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
package salve.contract;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotates an numerical argument or return value that must be less then or
 * equal to zero.
 * <p>
 * Supported types are {@link Double}, {@link Float}, {@link Long},
 * {@link Integer}, {@link Short}, and {@link Byte}
 * </p>
 * 
 * @author ivaynberg
 */
@Retention(RetentionPolicy.RUNTIME) @Documented @Target( { ElementType.PARAMETER, ElementType.METHOD }) public @interface LE0 {

}
