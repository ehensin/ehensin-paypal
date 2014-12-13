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

/**
 * split algorithom for db .
 * 
 * */
public enum SplitAlg {
    Hash(1, "hash","split with simple hash algorithom accoding to object hash code"),
    Named(2,"named","split with db name to");
    int code;
    String name;
    String desc;
    private SplitAlg(int code, String name, String desc){
    	this.code = code;
    	this.desc = desc;
    	this.name = name;
    }
    
    public static SplitAlg getSplitAlg(String name){
    	if( name.equals( Hash.name ))
    		return Hash;
    	else if( name.equals( Named.name ) )
    		return Named;
    	else
    		return null;
    }
}
