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

import java.io.Serializable;

abstract public class Entity implements Serializable{
    /**
	 * serialization uid;
	 */
	private static final long serialVersionUID = -5550470719790405083L;
	
	protected String entityId;
    public Entity(String entityId){
    	this.entityId = entityId;
    }
	public String getUuid() {
		return entityId;
	}
    
}
