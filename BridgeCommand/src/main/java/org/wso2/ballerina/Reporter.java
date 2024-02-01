package org.wso2.ballerina;

import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;

import java.nio.file.Path;
import java.util.ArrayList;

public class Reporter {
    private final ArrayList<Issue> issues;
    private final ArrayList<Issue> externalIssues = new ArrayList<>();

    // Internal methods
    public Reporter(ArrayList<Issue> issues) {
        this.issues = issues;
    }

    ArrayList<Issue> getExternalIssues() {
        return externalIssues;
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
                    moduleName + "/" + documentName,
                    issuesFilePath.toString());

            issues.add(issue);
        }
    }

    // External methods (Synchronized, since multiple compiler plugins can be using this)
    public synchronized void reportExternalIssue(int startLine,
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

        if (externalIssuesFilePath != null) {
            Issue issue = new Issue(startLine,
                    startLineOffset,
                    endLine,
                    endLineOffset,
                    "S1001", // TODO: Generated internally
                    message,
                    "CUSTOM_CHECK_VIOLATION", // TODO: Labelled internally
                    moduleName + "/" + documentName,
                    externalIssuesFilePath.toString());

            externalIssues.add(issue);
        }
    }
}
