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
 * split factor for db repository to decide which db session factory should be located.
 * 
 * */
public class SplitFactor {
	/*Algorithm*/
    private SplitAlg alg;
    /*value for algorithm*/
    private Object valueFoAlg;
	public SplitFactor(SplitAlg alg, Object valueFoAlg) {
		super();
		this.alg = alg;
		this.valueFoAlg = valueFoAlg;
	}
	public SplitAlg getAlg() {
		return alg;
	}
	public Object getValueForAlg() {
		return valueFoAlg;
	}
    
    
}
