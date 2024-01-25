package org.wso2.ballerina;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.ballerina.projects.plugins.CompilerPlugin;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public abstract class SensorContextFactory extends CompilerPlugin {
    private static final String serializeContextFile = "serialized-context-out.json";
    private static final Gson gson = new Gson();
    private SensorContext deserializedContext = null;


    // Used by Ballerina tool
    static void saveSerializedContext(SensorContext context) {
        try {
            Writer fileWriter = new FileWriter(serializeContextFile);
            JsonWriter writer = new JsonWriter(fileWriter);
            gson.toJson(context, SensorContext.class, writer);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static SensorContext getDeserializedContext() {
        try {
            Reader fileReader = new FileReader(serializeContextFile);
            JsonReader reader = new JsonReader(fileReader);
            SensorContext deserializedContext = gson.fromJson(reader, SensorContext.class);
            reader.close();

            // delete the file after getting the context
            Files.delete(Path.of(serializeContextFile));

            return deserializedContext;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Used by compiler plugins (Optimized methods for concurrent operations by multiple compiler plugins)
    public synchronized Reporter getDeserializedReporter() {
        // Read back the serialized context and return the reporter
        if (deserializedContext == null) {
            try {
                Reader fileReader = new FileReader(serializeContextFile);
                JsonReader reader = new JsonReader(fileReader);
                deserializedContext = gson.fromJson(reader, SensorContext.class);
                reader.close();
                return deserializedContext.getReporter();
            } catch (IOException ignored) {
                System.out.println("To engage compiler plugin please run 'bal bridge'");
            }
        }

        return deserializedContext.getReporter();
    }

    public synchronized void saveSerializedReporter() {
        if (deserializedContext != null) {
            try {
                // Read contents of the file
                Reader fileReader = new FileReader(serializeContextFile);
                JsonReader reader = new JsonReader(fileReader);
                SensorContext fileSensorContext = gson.fromJson(reader, SensorContext.class);
                reader.close();

                // Add all issues from the in memory sensor context reporter to the file sensor context reporter
                fileSensorContext.getReporter().addExternalIssues(deserializedContext.getReporter().getExternalIssues());

                // Save the in memory sensor context back to file
                Writer fileWriter = new FileWriter(serializeContextFile);
                JsonWriter writer = new JsonWriter(fileWriter);
                gson.toJson(fileSensorContext, SensorContext.class, writer);
                writer.close();
            } catch (IOException ignored) {
                System.out.println("To engage compiler plugin please run 'bal bridge'");
            }
        }
    }
}
