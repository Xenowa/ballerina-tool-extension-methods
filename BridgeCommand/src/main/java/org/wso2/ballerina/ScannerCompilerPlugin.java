package org.wso2.ballerina;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.ballerina.projects.plugins.CompilerPlugin;
import io.ballerina.projects.plugins.CompilerPluginContext;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class ScannerCompilerPlugin extends CompilerPlugin {
    private static final String serializeContextFile = "serialized-context-out.json";
    private static final Gson gson = new Gson();
    private final Map<CompilerPluginContext, ScannerContext> scannerContexts = new HashMap<>();

    // Retrieve the deserialized context from file
    static ScannerContext getDeserializedContext() {
        Path serializedContextFilePath = Path.of(serializeContextFile);
        if (Files.exists(serializedContextFilePath)) {
            try {
                Reader fileReader = new FileReader(serializeContextFile);
                JsonReader reader = new JsonReader(fileReader);
                ScannerContext deserializedContext = gson.fromJson(reader, ScannerContext.class);
                reader.close();

                // delete the file after getting the context
                Files.delete(serializedContextFilePath);

                return deserializedContext;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return null;
    }

    public ScannerContext getScannerContext(CompilerPluginContext compilerPluginContext) {
        if (scannerContexts.containsKey(compilerPluginContext)) {
            return scannerContexts.get(compilerPluginContext);
        }

        // Create new context when requested by compiler plugins
        ArrayList<Issue> externalIssues = new ArrayList<>();
        ScannerContext scannerContext = new ScannerContext(externalIssues);
        scannerContexts.put(compilerPluginContext, scannerContext);
        return scannerContext;
    }

    // Save the serialized context to file
    public synchronized void saveScannerContext(CompilerPluginContext compilerPluginContext) {
        ScannerContext scannerContext = getScannerContext(compilerPluginContext);

        try {
            Writer fileWriter = new FileWriter(serializeContextFile);
            JsonWriter writer = new JsonWriter(fileWriter);
            if (!Files.exists(Path.of(serializeContextFile))) {
                // Check if file already exists if not create one and save the serialized context directly
                gson.toJson(scannerContext, ScannerContext.class, writer);
            } else {
                // Read the context from file
                Reader fileReader = new FileReader(serializeContextFile);
                JsonReader reader = new JsonReader(fileReader);
                ScannerContext fileScannerContext = gson.fromJson(reader, ScannerContext.class);
                reader.close();

                if (fileScannerContext == null) {
                    gson.toJson(scannerContext, ScannerContext.class, writer);
                } else {
                    // Save the issues from in memory context to the file context
                    fileScannerContext.getReporter().addExternalIssues(scannerContext.getReporter().getExternalIssues());
                    // Save the file context
                    gson.toJson(fileScannerContext, ScannerContext.class, writer);
                }
            }
            writer.close();
        } catch (IOException ignored) {
            System.out.println("To engage compiler plugin please run 'bal bridge'");
        }
    }
}
