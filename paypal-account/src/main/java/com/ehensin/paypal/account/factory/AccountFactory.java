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
package com.ehensin.paypal.account.factory;

import java.sql.Timestamp;

import com.ehensin.paypal.account.entity.CustomerAccountEntity;
import com.ehensin.paypal.account.infra.UUIDUtil;
import com.ehensin.paypal.account.vo.CustomerAccountVO;

public class AccountFactory {
	private final static AccountFactory factory = new AccountFactory();
	
	private AccountFactory(){
		
	}
	
	public static AccountFactory getFactory(){
		return factory;
	}
	
	public CustomerAccountEntity createCustomerAccount(CustomerAccountVO vo){
		String uuid = UUIDUtil.getUUID();
		CustomerAccountEntity entity = new CustomerAccountEntity(uuid,vo.getPhone(),
				vo.getLoginName(), vo.getMail(), vo.getPassword(),0,0,0,0,0,0,
				new Timestamp(System.currentTimeMillis()) );
		
		return entity;
	}
}
