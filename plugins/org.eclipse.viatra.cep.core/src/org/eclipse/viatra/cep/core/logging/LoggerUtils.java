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

package org.eclipse.viatra.cep.core.logging;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Utility class for logging functionality.
 * 
 * @author Istvan David
 * 
 */
public class LoggerUtils {
    private static final String LOGGER_NAME = "org.eclipse.viatra.cep";
    private static LoggerUtils instance;
    private Logger logger;

    public static LoggerUtils getInstance() {
        if (instance == null) {
            instance = new LoggerUtils();
            BasicConfigurator.configure();
        }
        return instance;
    }

    private LoggerUtils() {
        this.logger = Logger.getLogger(LOGGER_NAME);
    }

    /**
     * @return the {@link Logger} instance
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Set the {@link Level} of debugging.
     * 
     * @param level
     *            the {@link Level} of debugging
     */
    public void setLevel(Level level) {
        logger.setLevel(level);
    }
}
