package org.wso2.ballerina;

import java.util.ArrayList;

public class Reporter {
    // Internal Attributes
    private final String filName;
    private final String issuesFilePath;

    // Common attribute shared between internal and external reporter
    private final ArrayList<Issue> issues;

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

    // External method used by reporter
    public void reportExternalIssue(int startLine,
                                    int startLineOffset,
                                    int endLine,
                                    int endLineOffset,
                                    String message,
                                    String fileName,
                                    String externalIssuesFilePath) {
        Issue issue = new Issue(startLine,
                startLineOffset,
                endLine,
                endLineOffset,
                "S1001", // TODO: Generated internally
                message,
                "CUSTOM_CHECK_VIOLATION", // TODO: Labelled internally
                fileName,
                externalIssuesFilePath);

        issues.add(issue);
    }
}
