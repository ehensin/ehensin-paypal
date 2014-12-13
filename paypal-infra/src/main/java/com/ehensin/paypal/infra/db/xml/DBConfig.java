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
package com.ehensin.paypal.infra.db.xml;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "db")
public class DBConfig {
	@XmlElement(name="property")
	private List<DBProperty> dbProperties;
    @XmlTransient
	private Map<String, String> dbPropertiesMap;
    
	public List<DBProperty> getDbProperties() {
		return dbProperties;
	}

	public void setDbProperties(List<DBProperty> dbProperties) {
		this.dbProperties = dbProperties;
	}
	
	public Map<String, String> getDbPropertiesMap(){
		if( dbPropertiesMap == null ){
			dbPropertiesMap = new HashMap<String, String>();
			for( DBProperty dbp : dbProperties ){
				dbPropertiesMap.put(dbp.getName(), dbp.getValue());
			}
		}
		return dbPropertiesMap;
	}
	
	
}
