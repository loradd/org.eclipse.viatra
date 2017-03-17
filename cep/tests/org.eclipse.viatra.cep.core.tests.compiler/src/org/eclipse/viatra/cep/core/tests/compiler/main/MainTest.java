/*******************************************************************************
 * Copyright (c) 2004-2014, Istvan David, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Istvan David - initial API and implementation
 *******************************************************************************/
package org.eclipse.viatra.cep.core.tests.compiler.main;

import org.apache.log4j.Level;
import org.eclipse.viatra.cep.core.api.engine.CEPEngine;
import org.eclipse.viatra.cep.core.compiler.testdata.patterns.CepFactory;
import org.eclipse.viatra.cep.core.metamodels.automaton.EventContext;
import org.eclipse.viatra.cep.core.streams.EventStream;
import org.eclipse.viatra.cep.core.tests.compiler.internal.DefaultRealm;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MainTest {

    private DefaultRealm defaultRealm;
    private CEPEngine engine;
    private EventStream eventStream;

    @Before
    public void setUp() {
        defaultRealm = new DefaultRealm();
        engine = CEPEngine.newEngine().eventContext(EventContext.CHRONICLE).rule(CepFactory.getInstance().rule_R())
                .prepare();
        eventStream = engine.getStreamManager().newEventStream();
    }

    @After
    public void tearDown() {
        eventStream = null;
        engine = null;
        defaultRealm.dispose();
    }

    @Test
    public void test() {
        engine.setCepEngineDebugLevel(Level.DEBUG);

        engine.getLogger().info("staring");
        eventStream.push(CepFactory.getInstance().createA_Event());
        eventStream.push(CepFactory.getInstance().createB_Event());

        eventStream.push(CepFactory.getInstance().createA_Event());
        engine.reset();
        eventStream.push(CepFactory.getInstance().createB_Event());

        // Some debug information about the end of the process.
        engine.getLogger().info("ending");
    }
}
