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
package com.ehensin.paypal.account.orm;

import java.sql.Timestamp;
/**
 * map object to table o2o_customer_account
 * */
public class CustomerAccountORMapping {
	private Integer id;
    private String uuid;
    private String phone;
    private String loginName;
    private String mail;
    private String password;
    private Integer status;
    private Integer balance1;
    private Integer balance2;
    private Integer balance3;
    private Integer balance4;
    private Integer balance5;
    private Timestamp lastUpdateTime;
    public CustomerAccountORMapping(){
    	
    }
	public CustomerAccountORMapping(String uuid, String phone,
			String loginName, String mail, String password, Integer status,
			Integer balance1, Integer balance2, Integer balance3,
			Integer balance4, Integer balance5, Timestamp lastUpdateTime) {
		super();
		this.uuid = uuid;
		this.phone = phone;
		this.loginName = loginName;
		this.mail = mail;
		this.password = password;
		this.status = status;
		this.balance1 = balance1;
		this.balance2 = balance2;
		this.balance3 = balance3;
		this.balance4 = balance4;
		this.balance5 = balance5;
		this.lastUpdateTime = lastUpdateTime;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getBalance1() {
		return balance1;
	}
	public void setBalance1(Integer balance1) {
		this.balance1 = balance1;
	}
	public Integer getBalance2() {
		return balance2;
	}
	public void setBalance2(Integer balance2) {
		this.balance2 = balance2;
	}
	public Integer getBalance3() {
		return balance3;
	}
	public void setBalance3(Integer balance3) {
		this.balance3 = balance3;
	}
	public Integer getBalance4() {
		return balance4;
	}
	public void setBalance4(Integer balance4) {
		this.balance4 = balance4;
	}
	public Integer getBalance5() {
		return balance5;
	}
	public void setBalance5(Integer balance5) {
		this.balance5 = balance5;
	}
	public Timestamp getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(Timestamp lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
}
