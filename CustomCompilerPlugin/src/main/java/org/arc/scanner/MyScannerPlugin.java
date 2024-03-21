package org.arc.scanner;

import io.ballerina.projects.plugins.CompilerPluginContext;
import org.wso2.ballerina.Rule;
import org.wso2.ballerina.ScannerCompilerPlugin;
import org.wso2.ballerina.ScannerContext;

import java.util.ArrayList;
import java.util.List;

public class MyScannerPlugin extends ScannerCompilerPlugin {
    @Override
    public List<Rule> rules() {
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule(1, "rule 1 description"));
        rules.add(new Rule(2, "rule 2 description"));
        rules.add(new Rule(3, "rule 3 description"));

        return rules;
    }

    @Override
    public void init(CompilerPluginContext pluginContext) {
        ScannerContext scannerContext = getScannerContext(pluginContext);
        pluginContext.addCodeAnalyzer(new CustomCodeAnalyzer(scannerContext));
    }
}
