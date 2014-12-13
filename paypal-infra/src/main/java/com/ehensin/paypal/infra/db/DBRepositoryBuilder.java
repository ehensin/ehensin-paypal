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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ehensin.paypal.infra.db.xml.DbRepositoriesConfig;
/**
 * db repository builder to build repository instance.
 * 
 * */
public class DBRepositoryBuilder {
	private static final Logger logger = LoggerFactory
			.getLogger(DBRepositoryBuilder.class);
	private String defaultConfigLocation = "/DBRepConfig.xml";
    public DBRepositoryBuilder(){
    	
    }
    public DBRepository build(String[] mappers){
    	InputStream in = DBRepositoryBuilder.class.getResourceAsStream(defaultConfigLocation);
		DbRepositoriesConfig config =  loadProfile(in, null);
		DBRepository repository = new DBRepository(config,mappers);
    	return repository;
    }
    
    public DBRepository build(String repositoryConfigXml, Map<String, String> initParam, String[] mappers){
		InputStream in;
		DBRepository repository = null;
		try {
			in = new FileInputStream(repositoryConfigXml);
			DbRepositoriesConfig config =  loadProfile(in, initParam);
			repository = new DBRepository(config, mappers);
		} catch (FileNotFoundException e) {
			logger.error("cannot load repository config file : {} ", repositoryConfigXml, e);
    		System.exit(1);
		}
    	
    	return repository;
    }
    
    public DBRepository build(InputStream in, Map<String, String> initParam, String[] mappers){
		DbRepositoriesConfig config =  loadProfile(in, initParam);
		DBRepository repository = new DBRepository(config, mappers);
    	return repository;
    }
    
    public DBRepository build(DbRepositoriesConfig config, Map<String, String> initParam, String[] mappers){
    	DBRepository repository = new DBRepository(config, mappers);
    	return repository;
    } 
    
    
    private DbRepositoriesConfig loadProfile(InputStream in, Map<String, String> initParam){
		try {
			String xml = inputStreamTOString(in);
			StringBuffer sb = new StringBuffer();
			if( initParam != null && initParam.size() > 0){
				Pattern p = Pattern.compile("\\$\\{.*?\\}");
				Matcher matcher = p.matcher(xml);
				while(matcher.find()){
					String param = matcher.group();
					String key = param.replace("${", "").replace("}", "");
					if(initParam.get(key) != null)
					   matcher.appendReplacement(sb, initParam.get(key));
				}
				matcher.appendTail(sb);
			}else{
				sb.append(xml);
			}
	        /*parser*/
			JAXBContext context;
			context = JAXBContext.newInstance(DbRepositoriesConfig.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (DbRepositoriesConfig)unmarshaller.unmarshal(new StringReader(sb.toString()));
		} catch (Exception e) {
			throw new IllegalArgumentException("cannot load db repository configuration, please check file", e);
		}
	}
    
    private String inputStreamTOString(InputStream in) throws Exception {

		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] data = new byte[4096];
		int count = -1;
		while ((count = in.read(data, 0, 4096)) != -1)
			outStream.write(data, 0, count);

		data = null;
		return new String(outStream.toByteArray(), "UTF-8");
	}
}
