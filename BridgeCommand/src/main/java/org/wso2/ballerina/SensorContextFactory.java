package org.wso2.ballerina;

import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;
import io.ballerina.projects.plugins.CompilerPlugin;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;

public abstract class SensorContextFactory extends CompilerPlugin {
    private static SensorContext context = null;

    // Used by Ballerina tool
    public static void setContext(SensorContext context) {
        if (SensorContextFactory.context == null) {
            SensorContextFactory.context = context;
        }
    }

    // External methods (Synchronized, since multiple reporters can be using this from compiler plugins)
    public synchronized void reportIssue(int startLine,
                                         int startLineOffset,
                                         int endLine,
                                         int endLineOffset,
                                         String message,
                                         Document reportedDocument,
                                         Module reportedModule,
                                         Project reportedProject) {

        String moduleName = reportedModule.moduleName().toString();
        String documentName = reportedDocument.name();
        String fileName = moduleName + "/" + documentName;
        Path externalIssuesFilePath = reportedProject.documentPath(reportedDocument.documentId()).orElse(null);

        if (externalIssuesFilePath != null) {
            // As compiler plugins use the URLClassLoader, getting the context through CustomToolClassLoader and reflection
            ClassLoader customToolClassLoader = Thread.currentThread().getContextClassLoader();
            try {

                // SensorContext object retrieved through reflection
                Field classLoadedContextField = customToolClassLoader.loadClass("org.wso2.ballerina.SensorContextFactory")
                        .getDeclaredField("context");
                classLoadedContextField.setAccessible(true); // to access private fields
                Object classLoadedContext = classLoadedContextField.get(null); // Since static no object instance needed

                // Setting reporter field accessible
                Field classLoadedReporterField = classLoadedContext.getClass().getDeclaredField("reporter");
                classLoadedReporterField.setAccessible(true); // to access private fields

                // Reporter object retrieved through reflection
                Object classLoadedReporter = classLoadedReporterField.get(classLoadedContext);
                Method reportExternalIssue = classLoadedReporter.getClass().getMethod("reportExternalIssue",
                        int.class,
                        int.class,
                        int.class,
                        int.class,
                        String.class,
                        String.class,
                        String.class);

                // Report the issue through reflection
                reportExternalIssue.invoke(classLoadedReporter,
                        startLine,
                        startLineOffset,
                        endLine,
                        endLineOffset,
                        message,
                        fileName,
                        externalIssuesFilePath.toString());
            } catch (NoSuchFieldException |
                     ClassNotFoundException |
                     IllegalAccessException |
                     NoSuchMethodException |
                     InvocationTargetException e) {
                System.out.println("Please run 'bal bridge' to engage the plugin");
                throw new RuntimeException(e);
            }
        }
    }
}
