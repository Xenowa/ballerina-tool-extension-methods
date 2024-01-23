package org.wso2.ballerina.plugin;

import io.ballerina.compiler.api.SemanticModel;
import io.ballerina.compiler.syntax.tree.SyntaxTree;
import io.ballerina.projects.plugins.CompilerPlugin;
import io.ballerina.projects.plugins.CompilerPluginContext;
import org.wso2.ballerina.CustomScanner;
import org.wso2.ballerina.SensorContext;

public class CustomCompilerPlugin extends CompilerPlugin implements CustomScanner {
    // Method not used
    @Override
    public void init(CompilerPluginContext compilerPluginContext) {
        System.out.println("To engage, please run 'bal bridge'");
    }

    // Engaged and used via Bridge Tool
    @Override
    public void performScan(SensorContext context) {
        // Retrieve the syntax tree from the sensor context
        SyntaxTree syntaxTree = context.getSyntaxTree();

        // Retrieve the semantic model from the sensor context
        SemanticModel semanticModel = context.getSemanticModel();

        // Simulating performing a custom analysis by reporting a custom issue for each document
        context.reportExternalIssue(0,
                0,
                0,
                0,
                "Custom compiler plugin issue");
    }
}
