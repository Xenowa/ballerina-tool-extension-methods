package org.arc.scanner;

import io.ballerina.projects.plugins.CompilerPlugin;
import io.ballerina.projects.plugins.CompilerPluginContext;

public class MyScannerPlugin extends CompilerPlugin {
    @Override
    public void init(CompilerPluginContext pluginContext) {
        pluginContext.addCodeAnalyzer(new CustomCodeAnalyzer());
    }
}
