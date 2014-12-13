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
package com.ehensin.paypal.account.test;

import java.sql.Timestamp;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.ehensin.paypal.account.mapper.AccountMapper;
import com.ehensin.paypal.account.orm.CustomerAccountORMapping;
import com.ehensin.paypal.infra.db.DBRepository;
import com.ehensin.paypal.infra.db.DBRepositoryBuilder;
import com.ehensin.paypal.infra.db.SplitAlg;
import com.ehensin.paypal.infra.db.SplitFactor;

public class CustomerAccountTest {
	@Test
	public void testInsert() throws Exception {
		DBRepositoryBuilder builder = new DBRepositoryBuilder();
		DBRepository repository = builder.build(new String[]{"com.ehensin.paypal.account.service"});
		SplitFactor factor = new SplitFactor(SplitAlg.Hash, 1);
		SqlSessionFactory factory = repository.getSessionFactory("account", factor, true);
		SqlSession session = factory.openSession();
		AccountMapper mapper = (AccountMapper) session.getMapper(AccountMapper.class);
		int count = mapper.getCount();
		System.out.println("hash customer count : " + count);
		CustomerAccountORMapping orm = new CustomerAccountORMapping();
		orm.setBalance1(0);
		orm.setBalance2(0);
		orm.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orm.setLoginName("hhb");
		orm.setMail("huanghaibo@newcosoft.com");
		orm.setPhone("13917310925");
		orm.setStatus(0);
		orm.setUuid("123");
		orm.setPassword("123456");
		mapper.insert(orm);
		count = mapper.getCount();
		System.out.println("hash customer count : " + count);
		session.commit(true);
		session.close();
	}
	@Test
	public void testSelect(){
		DBRepositoryBuilder builder = new DBRepositoryBuilder();
		DBRepository repository = builder.build(new String[]{"com.ehensin.paypal.account.service"});
		SplitFactor factor = new SplitFactor(SplitAlg.Hash, 1);
		SqlSessionFactory factory = repository.getSessionFactory("account", factor, true);
		SqlSession session = factory.openSession();
		AccountMapper mapper = (AccountMapper) session.getMapper(AccountMapper.class);
		int count = mapper.getCount();
		System.out.println("hash customer count : " + count);
		CustomerAccountORMapping orm = mapper.getCustomerAccount("123");
		/*new CustomerAccountORMapping();
		orm.setBalance1(0);
		orm.setBalance2(0);
		orm.setLastUpdateTime(new Timestamp(System.currentTimeMillis()));
		orm.setLoginName("hhb");
		orm.setMail("huanghaibo@newcosoft.com");
		orm.setPhone("13917310925");
		orm.setStatus(0);
		orm.setUuid("123");
		orm.setPassword("123456");
		mapper.insert(orm);
		count = mapper.getCount();*/
		System.out.println("login name : " + orm.getLoginName());
		session.close();
	}
}
