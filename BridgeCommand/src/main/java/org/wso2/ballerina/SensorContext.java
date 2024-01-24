package org.wso2.ballerina;

import java.util.ArrayList;

public class SensorContext {
    private final Reporter reporter;

    // A sensor context is created for each document
    public SensorContext(ArrayList<Issue> issues,
                         String issuesFilePath,
                         String moduleName,
                         String documentName) {

        String fileName = moduleName + "/" + documentName;
        this.reporter = new Reporter(fileName, issues, issuesFilePath);
    }

    public Reporter getReporter() {
        return reporter;
    }
}
