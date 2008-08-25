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
package salve.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * Jvm agent that applies Salve instrumentors to classes as they are being
 * loaded.
 * <p>
 * To configure add the following jvm parameter to the command line:
 * <code>-javaagent:path-to-this-jar</code>
 * </p>
 * 
 * @author ivaynberg
 */
public class Agent {
	private static Instrumentation INSTRUMENTATION;

	private static ClassFileTransformer newTransformer() {
		return new Transformer();
	}

	public static void premain(String agentArgs, Instrumentation inst) {
		// ignore double agents
		if (INSTRUMENTATION == null) {
			INSTRUMENTATION = inst;
			inst.addTransformer(newTransformer());
		}
	}

}
