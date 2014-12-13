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

import javax.sql.DataSource;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;

public class DataSourceFactory {
	public static DataSource getDataSource(DataSourceConfig dbConfig) {
		try {
			Class.forName(dbConfig.getDriver());
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(dbConfig.getDbUrl());
			config.setUsername(dbConfig.getUserName());
			config.setPassword(dbConfig.getPassword());
			config.setMinConnectionsPerPartition(dbConfig.getMinConnections());
			config.setMaxConnectionsPerPartition(dbConfig.getMaxConnections());
			config.setPartitionCount(dbConfig.getPartition());
			BoneCPDataSource dataSource = new BoneCPDataSource(config);
			return dataSource;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("cannot init db driver : " + dbConfig.getDriver(),e);
		}
		
	}

	private DataSourceFactory() {
	}

	
	
}
