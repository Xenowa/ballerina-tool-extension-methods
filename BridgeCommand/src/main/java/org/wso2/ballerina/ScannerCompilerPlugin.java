package org.wso2.ballerina;

import io.ballerina.projects.plugins.CompilerPlugin;
import io.ballerina.projects.plugins.CompilerPluginContext;

import java.util.List;

public abstract class ScannerCompilerPlugin extends CompilerPlugin {
    private ScannerContext scannerContext = null;

    public ScannerContext getScannerContext(CompilerPluginContext pluginContext) {
        if (scannerContext != null) {
            return scannerContext;
        }

        // Retrieve scanner context set by tool side (Even though this works class casting issue creates problems)
        scannerContext = (ScannerContext) pluginContext.userData().get("ScannerContext");
        return scannerContext;
    }

    public abstract List<Rule> rules();
}
