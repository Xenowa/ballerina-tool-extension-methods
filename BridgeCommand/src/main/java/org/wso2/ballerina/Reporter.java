package org.wso2.ballerina;

import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;

import java.nio.file.Path;
import java.util.ArrayList;

public class Reporter {
    // Internal attributes
    private final String filName;
    private final String issuesFilePath;
    private final ArrayList<Issue> issues;
    // External attributes
    private final ArrayList<Issue> externalIssues = new ArrayList<>();

    // Internal methods
    public Reporter(String filName, ArrayList<Issue> issues, String issuesFilePath) {
        this.filName = filName;
        this.issues = issues;
        this.issuesFilePath = issuesFilePath;
    }

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
                filName,
                issuesFilePath);

        issues.add(issue);
    }

    // External methods
    ArrayList<Issue> getExternalIssues() {
        return externalIssues;
    }

    void addExternalIssues(ArrayList<Issue> issues) {
        externalIssues.addAll(issues);
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
