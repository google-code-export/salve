/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package salve.depend.spring.txn;

import org.springframework.transaction.annotation.Transactional;

public class MethodBean {
	public static final long CLINIT_FORCER = System.currentTimeMillis();

	public MethodBean() {

	}

	@Transactional
	public void args1(int a) {

	}

	@Transactional
	public Object args2(int mode, Object[] a, double[] b) {
		switch (mode) {
		case 1:
			return a;
		case 2:
			return b;
		default:
			return null;
		}
	}

	@Transactional
	public Object exception(int mode, Object p) {
		switch (mode) {
		case 1:
			throw new ArrayIndexOutOfBoundsException();
		case 2:
			throw new IllegalStateException();
		default:
			return p;
		}
	}

	@Transactional
	public Object ret(Object p) {
		return p;
	}

	@Transactional
	public void simple() {

	}

	@Transactional(rollbackFor = RollbackException.class)
	public void exceptionOk() throws RollbackException, NoRollbackException {
	}

	@Transactional(rollbackFor = RollbackException.class)
	public void exceptionOkWithException() throws RollbackException,
			NoRollbackException {
		throw new NoRollbackException();
	}

	@Transactional(rollbackFor = RollbackException.class)
	public void exception() throws RollbackException, NoRollbackException {
		throw new RollbackException();
	}

	@Transactional
	public void exceptionFromWithin() {
		throwRte();
	}

	private void throwRte() {
		throw new RuntimeException();
	}

}