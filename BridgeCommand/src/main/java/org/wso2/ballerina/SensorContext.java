package org.wso2.ballerina;

import io.ballerina.compiler.api.SemanticModel;
import io.ballerina.compiler.syntax.tree.SyntaxTree;

import java.util.ArrayList;

public class SensorContext {
    private final ArrayList<Issue> issues;
    private final String issuesFilePath;
    private final String moduleName;
    private final String documentName;
    private final SyntaxTree syntaxTree;
    private final SemanticModel semanticModel;

    // A sensor context is created for each document
    public SensorContext(ArrayList<Issue> issues,
                         String issuesFilePath,
                         String moduleName,
                         String documentName,
                         SyntaxTree syntaxTree,
                         SemanticModel semanticModel) {
        this.issues = issues;
        this.issuesFilePath = issuesFilePath;
        this.documentName = documentName;
        this.moduleName = moduleName;
        this.syntaxTree = syntaxTree;
        this.semanticModel = semanticModel;
    }

    // Internal methods
    void reportIssue(int startLine,
                     int startLineOffset,
                     int endLine,
                     int endLineOffset,
                     String ruleID,
                     String message,
                     String issueType) {
        Issue issue = new Issue(startLine,
                startLineOffset,
                endLine,
                endLineOffset,
                ruleID,
                message,
                issueType,
                moduleName + "/" + documentName,
                issuesFilePath);

        issues.add(issue);
    }

    // External methods
    public void reportExternalIssue(int startLine,
                                    int startLineOffset,
                                    int endLine,
                                    int endLineOffset,
                                    String message) {
        Issue issue = new Issue(startLine,
                startLineOffset,
                endLine,
                endLineOffset,
                "S1001", // TODO: Generated internally
                message,
                "CUSTOM_CHECK_VIOLATION", // TODO: Labelled internally
                moduleName + "/" + documentName,
                issuesFilePath);

        issues.add(issue);
    }

    public SyntaxTree getSyntaxTree() {
        return syntaxTree;
    }

    public SemanticModel getSemanticModel() {
        return semanticModel;
    }
}
