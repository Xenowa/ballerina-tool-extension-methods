package org.wso2.ballerina;

import com.google.gson.Gson;
import io.ballerina.projects.plugins.CompilerPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class SensorContextFactory extends CompilerPlugin {
    private static final Gson gson = new Gson();
    private static String serializedContext = null;

    private SensorContext externalSensorContext = null;

    // Used internally
    static SensorContext getContext() {
        return gson.fromJson(serializedContext, SensorContext.class);
    }

    // Used by Ballerina tool
    static void setContext(SensorContext context) {
        if (SensorContextFactory.serializedContext == null) {
            SensorContextFactory.serializedContext = gson.toJson(context, SensorContext.class);
        }
    }

    private static void setSerializedContext(String serializedContext) {
        SensorContextFactory.serializedContext = serializedContext;
    }

    // Used externally
    public synchronized SensorContext getDeserializedContext() {
        if (externalSensorContext == null) {
            // As compiler plugins use the URLClassLoader, getting the context through CustomToolClassLoader and reflection
            ClassLoader customToolClassLoader = Thread.currentThread().getContextClassLoader();
            try {
                Field classLoadedContextField = customToolClassLoader.loadClass("org.wso2.ballerina.SensorContextFactory")
                        .getDeclaredField("serializedContext");
                classLoadedContextField.setAccessible(true); // to access private fields
                String classLoadedSerializedContext = (String) classLoadedContextField.get(null);
                externalSensorContext = gson.fromJson(classLoadedSerializedContext, SensorContext.class);

                // Return the CustomToolClassLoader reporter
                return externalSensorContext;
            } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException e) {
                System.out.println("Please run 'bal bridge' to engage the plugin");
                throw new RuntimeException(e);
            }
        }

        return externalSensorContext;
    }

    public synchronized void saveSerializedContext() {
        if (externalSensorContext != null) {
            ClassLoader customToolClassLoader = Thread.currentThread().getContextClassLoader();
            try {
                // Get existing reporter to get issues
                Field classLoadedContextField = customToolClassLoader.loadClass("org.wso2.ballerina.SensorContextFactory")
                        .getDeclaredField("serializedContext");
                classLoadedContextField.setAccessible(true); // to access private fields
                String classLoadedSerializedContext = (String) classLoadedContextField.get(null);
                SensorContext classLoadedContext = gson.fromJson(classLoadedSerializedContext, SensorContext.class);

                // set all current external issues to the class loaded context
                classLoadedContext.getReporter()
                        .setExternalIssues(externalSensorContext.getReporter().getExternalIssues());

                // Save the updated reporter
                Method setSerializedContext = customToolClassLoader
                        .loadClass("org.wso2.ballerina.SensorContextFactory")
                        .getDeclaredMethod("setSerializedContext", String.class);
                setSerializedContext.setAccessible(true); // to access private method
                setSerializedContext.invoke(null, gson.toJson(classLoadedContext, SensorContext.class));
            } catch (ClassNotFoundException | IllegalAccessException e) {
                System.out.println("Please run 'bal bridge' to engage the plugin");
                throw new RuntimeException(e);
            } catch (NoSuchMethodException | InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
