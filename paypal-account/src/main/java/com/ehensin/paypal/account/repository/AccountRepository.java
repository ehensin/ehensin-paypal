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
package com.ehensin.paypal.account.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ehensin.paypal.account.entity.CustomerAccountEntity;
import com.ehensin.paypal.account.mapper.AccountMapper;
import com.ehensin.paypal.account.orm.CustomerAccountORMapping;
import com.ehensin.paypal.infra.db.DBRepository;
import com.ehensin.paypal.infra.db.DBRepositoryBuilder;
import com.ehensin.paypal.infra.db.SplitAlg;
import com.ehensin.paypal.infra.db.SplitFactor;
/**
 * account repository is due to find account‘s entity from db or cache for client.
 * all entity in repository will be persistent to db or other media and keep consistent with db.
 * */
public class AccountRepository {
	private static final Logger logger = LoggerFactory
			.getLogger(AccountRepository.class);
	private final static AccountRepository repository = new AccountRepository();
	private final String DB_CONFIG = "/ehensin/data/pay-pal/conf/DBRepConfig.xml";
	private DBRepository dbRepository ;
	private Map<Class<?>, String> entityDBRepNameMap;
	private AccountRepository(){
		DBRepositoryBuilder builder = new DBRepositoryBuilder();
		dbRepository = builder.build(DB_CONFIG, null, new String[]{"com.ehensin.paypal.account.mapper"});
		entityDBRepNameMap = new HashMap<Class<?>, String>();
		entityDBRepNameMap.put(CustomerAccountEntity.class, "account");
	}
	public static AccountRepository getRepository() {	
		return repository;
	}

	public CustomerAccountEntity getCustomerAccount(String entityId)throws Exception {
		String dbRepName = entityDBRepNameMap.get(CustomerAccountEntity.class);
		SplitFactor factor = new SplitFactor(SplitAlg.Hash, entityId);
		SqlSessionFactory factory = dbRepository.getSessionFactory(dbRepName, factor, false);
		SqlSession session = factory.openSession();
		try{
		    AccountMapper mapper = (AccountMapper) session.getMapper(AccountMapper.class);
		    CustomerAccountORMapping orm = mapper.getCustomerAccount(entityId);
		    if( orm == null )
		    	throw new Exception("cannot find entity for uuid :" + entityId);
		    CustomerAccountEntity entity = new CustomerAccountEntity(orm.getUuid(),orm.getPhone(),
		    		orm.getLoginName(), orm.getMail(), orm.getPassword(),orm.getStatus(),
		    		orm.getBalance1(),orm.getBalance2(),orm.getBalance3(),orm.getBalance4(),
		    		orm.getBalance5(),orm.getLastUpdateTime());
		    return entity;
		    
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
			session.close();
		}
	}
	
	public void addCustomerAccount(CustomerAccountEntity entity)throws Exception {
		String dbRepName = entityDBRepNameMap.get(CustomerAccountEntity.class);
		SplitFactor factor = new SplitFactor(SplitAlg.Hash, entity.getUuid());
		SqlSessionFactory factory = dbRepository.getSessionFactory(dbRepName, factor, true);
		SqlSession session = factory.openSession();
		try{
		    AccountMapper mapper = (AccountMapper) session.getMapper(AccountMapper.class);
		    CustomerAccountORMapping orm = new CustomerAccountORMapping(entity.getUuid(),entity.getPhone(),
		    		entity.getLoginName(), entity.getMail(), entity.getPassword(),entity.getStatus(),
		    		entity.getBalance1(),entity.getBalance2(),entity.getBalance3(),entity.getBalance4(),
		    		entity.getBalance5(),entity.getLastUpdateTime());
		    mapper.insert(orm);
		    session.commit(true);
		}catch(PersistenceException e){
			logger.error(e.getMessage(), e);
			throw e;
		}finally{
			session.close();
		}
	}
}
