package org.arc.scanner;

import io.ballerina.projects.plugins.CompilerPluginContext;
import org.wso2.ballerina.ScannerCompilerPlugin;
import org.wso2.ballerina.ScannerContext;

public class MyScannerPlugin extends ScannerCompilerPlugin {

    @Override
    public void init(CompilerPluginContext pluginContext) {
        ScannerContext scannerContext = getScannerContext(pluginContext);
        pluginContext.addCodeAnalyzer(new CustomCodeAnalyzer(scannerContext));
    }
}
