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
package com.ehensin.paypal.account.service;

import com.ehensin.paypal.account.entity.CustomerAccountEntity;
import com.ehensin.paypal.account.factory.AccountFactory;
import com.ehensin.paypal.account.repository.AccountRepository;
import com.ehensin.paypal.account.vo.CustomerAccountVO;
import com.ehensin.tunnel.server.protocol.service.annotation.Service;

@Service(name="account-service")
public class AccountService implements IAccountService {
    public AccountService(){

    }
	@Override
	public Boolean registerCustomer(CustomerAccountVO vo) throws Exception {
		CustomerAccountEntity entity = AccountFactory.getFactory().createCustomerAccount(vo);
		AccountRepository.getRepository().addCustomerAccount(entity);
		return true;
	}

	@Override
	public CustomerAccountVO getCustomerAccount(String uuid) throws Exception {
		CustomerAccountEntity entity = AccountRepository.getRepository().getCustomerAccount(uuid);
		CustomerAccountVO vo = new CustomerAccountVO(entity.getUuid(), entity.getPhone(), entity.getLoginName(),
				entity.getMail(),entity.getPassword(),entity.getStatus(), entity.getBalance1(), entity.getBalance2());
		return vo;
	}

}
