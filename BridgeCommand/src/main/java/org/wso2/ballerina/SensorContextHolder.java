package org.wso2.ballerina;

import io.ballerina.projects.plugins.CompilerPlugin;

public abstract class SensorContextHolder extends CompilerPlugin {
    private static SensorContext externalSensorContext = null;

    public SensorContext getContext() {
        return externalSensorContext;
    }

    static void setContext(SensorContext context) {
        externalSensorContext = context;
    }
}
