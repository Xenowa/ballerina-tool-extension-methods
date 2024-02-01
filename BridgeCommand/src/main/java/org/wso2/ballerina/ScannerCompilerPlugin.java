package org.wso2.ballerina;

import io.ballerina.projects.plugins.CompilerPlugin;
import io.ballerina.projects.plugins.CompilerPluginContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ScannerCompilerPlugin extends CompilerPlugin {
    // To prevent concurrent access issues
    private static final Map<CompilerPluginContext, ScannerContext> scannerContexts = new ConcurrentHashMap<>();

    static Collection<ScannerContext> getExternalScannerContext() {
        return scannerContexts.values();
    }

    public ScannerContext getScannerContext(CompilerPluginContext compilerPluginContext) {
        if (scannerContexts.containsKey(compilerPluginContext)) {
            return scannerContexts.get(compilerPluginContext);
        }

        ArrayList<Issue> externalIssues = new ArrayList<>();
        ScannerContext scannerContext = new ScannerContextImpl(externalIssues,
                null,
                null,
                null);
        scannerContexts.put(compilerPluginContext, scannerContext);
        return scannerContext;
    }
}
