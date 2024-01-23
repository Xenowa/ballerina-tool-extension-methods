package org.wso2.ballerina;

import java.util.ArrayList;

public class SensorContext {
    private final ArrayList<Issue> issues;
    private final String issuesFilePath;
    private final String moduleName;
    private final String documentName;

    // A sensor context is created for each document
    public SensorContext(ArrayList<Issue> issues,
                         String issuesFilePath,
                         String moduleName,
                         String documentName) {
        this.issues = issues;
        this.issuesFilePath = issuesFilePath;
        this.documentName = documentName;
        this.moduleName = moduleName;
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
}
