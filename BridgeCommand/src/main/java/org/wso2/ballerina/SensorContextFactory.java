package org.wso2.ballerina;

import io.ballerina.projects.plugins.CompilerPlugin;

import java.lang.reflect.Field;

public abstract class SensorContextFactory extends CompilerPlugin {
    private static SensorContext context = null;

    // Used by Ballerina tool
    public static void setContext(SensorContext context) {
        if (SensorContextFactory.context == null) {
            SensorContextFactory.context = context;
        }
    }

    // Used by compiler plugins
    public Reporter getReporter() {
        // As compiler plugins use the URLClassLoader, getting the context through CustomToolClassLoader and reflection
        ClassLoader customToolClassLoader = Thread.currentThread().getContextClassLoader();
        try {
            Field classLoadedContextField = customToolClassLoader.loadClass("org.wso2.ballerina.SensorContextFactory")
                    .getDeclaredField("context");
            classLoadedContextField.setAccessible(true); // to access private fields
            SensorContext classLoadedContext = (SensorContext) classLoadedContextField.get(null);

            // Return the CustomToolClassLoader reporter
            return classLoadedContext.getReporter();
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
            System.out.println("Please run 'bal bridge' to engage the plugin");
            throw new RuntimeException(e);
        }
    }
}
