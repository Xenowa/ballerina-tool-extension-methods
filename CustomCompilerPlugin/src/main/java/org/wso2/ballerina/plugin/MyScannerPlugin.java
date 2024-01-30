package org.wso2.ballerina.plugin;

import io.ballerina.compiler.api.SemanticModel;
import io.ballerina.compiler.syntax.tree.SyntaxTree;
import org.wso2.ballerina.CustomScannerPlugin;
import org.wso2.ballerina.ScannerContext;

public class MyScannerPlugin implements CustomScannerPlugin {
    private ScannerContext context;

    @Override
    public void init(ScannerContext context) {
        this.context = context;
    }

    // Engaged and used via Bridge Tool
    @Override
    public void perform() {
        // Retrieve the syntax tree from the sensor context
        SyntaxTree syntaxTree = context.getSyntaxTree();

        // Retrieve the semantic model from the sensor context
        SemanticModel semanticModel = context.getSemanticModel();

        // Simulating performing a custom analysis by reporting a custom issue for each document
        context.getReporter().reportExternalIssue(0,
                0,
                0,
                0,
                "Custom compiler plugin issue",
                context.getCurrentDocument(),
                context.getCurrentModule(),
                context.getCurrentProject());
    }
}
