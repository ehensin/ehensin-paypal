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
package com.ehensin.paypal.account.entity;

import java.sql.Timestamp;

public class CustomerAccountEntity extends AccountEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5796849016921732832L;

	public CustomerAccountEntity(String entityId, String phone,
			String loginName, String mail, String password, Integer status,
			Integer balance1, Integer balance2, Integer balance3,
			Integer balance4, Integer balance5, Timestamp lastUpdateTime) {
		super(entityId, phone, loginName, mail, password, status, balance1, balance2,
				balance3, balance4, balance5, lastUpdateTime);
		// TODO Auto-generated constructor stub
	}


}
