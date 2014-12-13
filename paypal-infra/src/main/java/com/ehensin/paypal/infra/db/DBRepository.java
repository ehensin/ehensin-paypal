/*
 * Copyright 2013 The Ehensin Project
 *
 * The Ehensin Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.ehensin.paypal.infra.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ehensin.paypal.infra.db.xml.ClusterNode;
import com.ehensin.paypal.infra.db.xml.DBConfig;
import com.ehensin.paypal.infra.db.xml.DbRepositoriesConfig;
import com.ehensin.paypal.infra.db.xml.DbRepositoryConfig;
import com.ehensin.paypal.infra.db.xml.NodeMaster;

/**
 * this repository is to manage all Database session factory for client.
 * when a client need to access database, it needs to get a db session factory.
 * for supporting high concurrency access, normally, data should be divided into multiple
 * sub-homogeneous database cluster to decentralized access pressure.
 * 
 * Data split way:
 * 1. Simple hash
 * 2. Consistency hash
 * 3. Named : EX, splitting according to area
 * 
 * every database cluster have :
 * one master database and some slaver databases;
 * master for updating and salver for query
 * */
public class DBRepository {
	private static final Logger logger = LoggerFactory
			.getLogger(DBRepository.class);
	
	private DbRepositoriesConfig config;
	private String[] mappers;
	/*map repository name to db cluster of this repository*/
	private Map<String, DBCluster> repMap;
	DBRepository(DbRepositoriesConfig config, String[] mappersPackagName){
		this.config = config;
		repMap = new java.util.concurrent.ConcurrentHashMap<String, DBRepository.DBCluster>();
		this.mappers = mappersPackagName;
		init();
	}
	private void init(){
		if( logger.isDebugEnabled() )
			logger.debug("intilizing database repository.......");
		List<DbRepositoryConfig> repositories = this.config.getDbRepositories();
		for(DbRepositoryConfig config : repositories){
			
			List<DBClusterNode> clusterNodes = new ArrayList<DBClusterNode>();
			List<ClusterNode> nodes = config.getNodes();
			for(ClusterNode node : nodes){
				
				NodeMaster master = node.getMaster();
				SqlSessionFactory masterSf = this.getSqlSessionFactory(master.getDbConfig().getDbPropertiesMap());
				List<SqlSessionFactory> slaversSf = new ArrayList<SqlSessionFactory>();
				for( DBConfig dbConfig : node.getSlavers().getDbConfigs() ){
					slaversSf.add(this.getSqlSessionFactory(dbConfig.getDbPropertiesMap()));
				}
				
				DBClusterNode cnode = new DBClusterNode(node.getName(), masterSf, slaversSf);
				
				clusterNodes.add(cnode);
			}
			String algName = config.getAlg();
			SplitAlg alg = SplitAlg.getSplitAlg(algName);
			if( alg == null ){
				throw new IllegalArgumentException("cannot recognize alg name : " + algName);
			}
			DBCluster cluster = new DBCluster(alg, clusterNodes);
			repMap.put(config.getName(), cluster);
		}
		
		if( logger.isDebugEnabled() )
			logger.debug("database repository init completely.......");
	}
	
	private SqlSessionFactory getSqlSessionFactory(Map<String, String> properties ){
		DataSourceConfig config = new DataSourceConfig();
		config.setDbUrl(properties.get("url"));
		config.setDriver(properties.get("driver"));
		config.setPartition(Integer.valueOf(properties.get("partions")));
		config.setUserName(properties.get("username"));
		config.setPassword(properties.get("password"));
		config.setMinConnections(Integer.valueOf(properties.get("minconnection")));
		config.setMaxConnections(Integer.valueOf(properties.get("maxconnection")));
		
		DataSource dataSource = DataSourceFactory.getDataSource(config);
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		Environment environment = new Environment("account-service", transactionFactory, dataSource);
		Configuration configuration = new Configuration(environment);
		for(String mapper : mappers){
		     configuration.addMappers(mapper);
		}
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
		return sqlSessionFactory;
	}
	/**
	 * Getting session factory according {@link SplitFactor}
	 * @param repName repository name
	 * @param factor {@link SplitFactor}
	 * @param isMaster: true return master db session factory, false return one of slaver db session factory
	 * @return SqlSessionFactory instance if find one , or null if no factory found
	 * */
    public SqlSessionFactory getSessionFactory(String repName, SplitFactor factor, boolean isMaster){
    	DBCluster cluster = repMap.get(repName);
    	if( factor.getAlg().code == SplitAlg.Hash.code ){
    		int hashCode = factor.getValueForAlg().hashCode();
    		int size = cluster.getNodes().size();
    		int index = hashCode % size;
    		DBClusterNode node = cluster.getNodes().get(index);
    		if( isMaster )
    			return node.getMaster();
    		else{
    			return node.getSlaver();
    		}
    	}else if (factor.getAlg().code == SplitAlg.Named.code ){
    		DBClusterNode node = cluster.getNode((String)factor.getValueForAlg());
    		if( isMaster )
    			return node.getMaster();
    		else{
    			return node.getSlaver();
    		}
    	}
    	throw new IllegalArgumentException("cannot recognize alg name : " + factor.getAlg().name);
    }
    
    private class DBCluster{
    	private SplitAlg alg;
    	private List<DBClusterNode> nodes;
    	private Map<String, DBClusterNode> nodeMap;
		public DBCluster(SplitAlg alg, List<DBClusterNode> nodes) {
			super();
			this.alg = alg;
			this.nodes = nodes;
			nodeMap = new HashMap<String, DBClusterNode>();
			for( DBClusterNode node : nodes ){
				nodeMap.put(node.getName(), node);
			}
		}
		public List<DBClusterNode> getNodes() {
			return nodes;
		}
		
		public DBClusterNode getNode(String nodeName){
			return nodeMap.get(nodeName);
		}

    }
    
    private class DBClusterNode{
    	private String name;
    	private SqlSessionFactory master;
    	private List<SqlSessionFactory> slavers;
    	private int slaverIndex = 0;
		public DBClusterNode(String name, SqlSessionFactory master,
				List<SqlSessionFactory> slavers) {
			super();
			this.master = master;
			this.slavers = slavers;
			this.name = name;
		}
		
		public String getName() {
			return name;
		}

		public SqlSessionFactory getMaster() {
			return master;
		}

		public SqlSessionFactory getSlaver(){
			int i = slaverIndex;
			if( i >= slavers.size() )
				slaverIndex = i = 0;
			slaverIndex++;
			return slavers.get(i);
		}

    }
}
