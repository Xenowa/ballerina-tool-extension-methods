package org.wso2.ballerina;

import io.ballerina.compiler.api.SemanticModel;
import io.ballerina.compiler.syntax.tree.SyntaxTree;
import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;

public interface ScannerContext {
    Reporter getReporter();

    SyntaxTree getSyntaxTree();

    SemanticModel getSemanticModel();

    Document getCurrentDocument();

    Module getCurrentModule();

    Project getCurrentProject();
}
