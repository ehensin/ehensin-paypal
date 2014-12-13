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
package com.ehensin.paypal.account.mapper;

import org.apache.ibatis.exceptions.PersistenceException;

import com.ehensin.paypal.account.orm.CustomerAccountORMapping;

/**
 * mybatis mapper interface to operate account related table
 * 
 * */
public interface AccountMapper {
    public int getCount();
    /**
     * insert a row into o2o_customer_account table
     * */
    public int insert(CustomerAccountORMapping orm) throws PersistenceException;
    /**
     * get a row record from o2o_customer_account table according uuid
     * */
    public CustomerAccountORMapping getCustomerAccount(String uuid);
}
