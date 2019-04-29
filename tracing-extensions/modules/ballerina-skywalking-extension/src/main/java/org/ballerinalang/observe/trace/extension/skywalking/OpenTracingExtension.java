/*
 * Copyright (c) 2019, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.ballerinalang.observe.trace.extension.skywalking;

import io.opentracing.Tracer;
import org.apache.skywalking.apm.toolkit.opentracing.SkywalkingTracer;

import org.ballerinalang.annotation.JavaSPIService;
import org.ballerinalang.config.ConfigRegistry;
import org.ballerinalang.util.tracer.OpenTracer;
import org.ballerinalang.util.tracer.exception.InvalidConfigurationException;

import java.io.PrintStream;
import java.util.Objects;


import static org.ballerinalang.observe.trace.extension.skywalking.Constants.TRACER_NAME;

/**
 * This is the open tracing extension class for {@link OpenTracer}.
 */
@JavaSPIService("org.ballerinalang.util.tracer.OpenTracer")
public class OpenTracingExtension implements OpenTracer {

    private ConfigRegistry configRegistry;
    private static final PrintStream console = System.out;
    private static final PrintStream consoleError = System.err;

    @Override
    public void init() throws InvalidConfigurationException {
        configRegistry = ConfigRegistry.getInstance();

        try {
            console.println("Init The Ballerina Skywalking Impl ");

        } catch (IllegalArgumentException | ArithmeticException e) {
            throw new InvalidConfigurationException(e.getMessage());
        }
        //console.println("ballerina: started publishing tracers to Apache Skywalking on " + hostname + ":" + port);
    }

    @Override
    public Tracer getTracer(String tracerName, String serviceName) {

        if (Objects.isNull(configRegistry)) {
            throw new IllegalStateException("Tracer not initialized with configurations");
        }

        return new SkywalkingTracer();
    }

    @Override
    public String getName() {
        return TRACER_NAME;
    }

}
