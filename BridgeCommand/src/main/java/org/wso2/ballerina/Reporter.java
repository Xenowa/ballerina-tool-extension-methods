package org.wso2.ballerina;

import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;

import java.nio.file.Path;
import java.util.ArrayList;

public class Reporter {
    // Internal attributes
    private final ArrayList<Issue> issues;

    // Internal methods
    public Reporter(ArrayList<Issue> issues) {
        this.issues = issues;
    }

    ArrayList<Issue> getIssues() {
        return issues;
    }

    // TODO: to be removed once serializing only list of issues is implemented
    void addIssues(ArrayList<Issue> issues) {
        issues.addAll(issues);
    }

    void reportIssue(int startLine,
                     int startLineOffset,
                     int endLine,
                     int endLineOffset,
                     String ruleID,
                     String message,
                     String issueType,
                     Document reportedDocument,
                     Module reportedModule,
                     Project reportedProject) {

        String moduleName = reportedModule.moduleName().toString();
        String documentName = reportedDocument.name();
        Path issuesFilePath = reportedProject.documentPath(reportedDocument.documentId()).orElse(null);

        if (issuesFilePath != null) {
            Issue issue = new Issue(startLine,
                    startLineOffset,
                    endLine,
                    endLineOffset,
                    ruleID,
                    message,
                    issueType,
                    moduleName + "/" + documentName, // TODO: Attribute should be enabled for future scans
                    issuesFilePath.toString());

            issues.add(issue);
        }
    }

    public void reportExternalIssue(int startLine,
                                    int startLineOffset,
                                    int endLine,
                                    int endLineOffset,
                                    String message,
                                    Document reportedDocument,
                                    Module reportedModule,
                                    Project reportedProject) {
        String moduleName = reportedModule.moduleName().toString();
        String documentName = reportedDocument.name();
        Path externalIssuesFilePath = reportedProject.documentPath(reportedDocument.documentId()).orElse(null);

        if (externalIssuesFilePath != null && message != null) {
            Issue issue = new Issue(startLine,
                    startLineOffset,
                    endLine,
                    endLineOffset,
                    "S1001",
                    message,
                    "CUSTOM_CHECK_VIOLATION", // TODO: Labelled internally
                    moduleName + "/" + documentName, // TODO: Attribute should be enabled for future scans
                    externalIssuesFilePath.toString());

            issues.add(issue);
        }
    }
}
