/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package salve.loader;

import java.util.ArrayList;
import java.util.List;

import salve.Bytecode;
import salve.BytecodeLoader;

/**
 * A compound implementation of {@link BytecodeLoader}
 * 
 * @author ivaynberg
 * 
 */
public class CompoundLoader implements BytecodeLoader {
	private final List<BytecodeLoader> delegates = new ArrayList<BytecodeLoader>();

	/**
	 * Adds a {@link BytecodeLoader}
	 * 
	 * @param loader
	 *            bytecode loader
	 * @return this for chaining
	 */
	public CompoundLoader addLoader(BytecodeLoader loader) {
		if (loader == null) {
			throw new IllegalArgumentException("Argument `loader` cannot be null");
		}
		delegates.add(loader);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	public Bytecode loadBytecode(String className) {
		for (BytecodeLoader loader : delegates) {
			Bytecode bytecode = loader.loadBytecode(className);
			if (bytecode != null) {
				return bytecode;
			}
		}
		return null;
	}
}
