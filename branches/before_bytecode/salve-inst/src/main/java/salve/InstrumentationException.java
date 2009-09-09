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
package salve;

/**
 * Exception that represents a problem during the instrumentation process
 * 
 * @author ivaynberg
 * 
 */
public class InstrumentationException extends RuntimeException implements CodeMarkerAware {
	private static final long serialVersionUID = 1L;
	private CodeMarker marker;

	/**
	 * {@inheritDoc}
	 */
	public InstrumentationException(Exception e) {
		super(e);
		copyMarker(e);
	}

	/**
	 * {@inheritDoc}
	 */
	public InstrumentationException(Exception e, CodeMarker marker) {
		super(e);
		this.marker = marker;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public InstrumentationException(String message) {
		super(message);
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	public InstrumentationException(String message, CodeMarker marker) {
		super(message);
		this.marker = marker;
	}

	/**
	 * {@inheritDoc}
	 */
	public InstrumentationException(String string, Exception e) {
		super(string, e);
		copyMarker(e);
	}

	/**
	 * {@inheritDoc}
	 */
	public InstrumentationException(String string, Exception e, CodeMarker marker) {
		super(string, e);
		this.marker = marker;
	}

	private void copyMarker(Throwable e) {
		if (e instanceof CodeMarkerAware) {
			marker = ((CodeMarkerAware) e).getCodeMarker();
		}
	}

	/** {@inheritDoc} */
	public CodeMarker getCodeMarker() {
		return marker;
	}

}
