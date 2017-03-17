/*******************************************************************************
 * Copyright (c) 2004-2014, Istvan David, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Istvan David - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.cep.vepl.jvmmodel;

public enum FactoryMethodParameter {
	EMPTY(""), NULL("null"), EVENTSOURCE("eventSource");

	private String parameterName;

	FactoryMethodParameter(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getLiteral() {
		return parameterName;
	}
}
