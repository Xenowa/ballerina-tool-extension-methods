package org.wso2.ballerina;

import java.util.ArrayList;

public class ScannerContext {
    private final Reporter reporter;

    // A sensor context is created for each document
    public ScannerContext(ArrayList<Issue> issues) {
        this.reporter = new Reporter(issues);
    }

    public Reporter getReporter() {
        return reporter;
    }
}
