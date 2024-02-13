package org.wso2.ballerina;

import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;

import java.util.ArrayList;

public class ScannerContext {
    private final Reporter reporter;
    private Document currentDocument;
    private Module currentModule;
    private Project currentProject;

    // A sensor context is created for each document
    public ScannerContext(ArrayList<Issue> issues,
                          Document currentDocument,
                          Module currentModule,
                          Project currentProject) {
        this.reporter = new Reporter(issues);
        this.currentDocument = currentDocument;
        this.currentModule = currentModule;
        this.currentProject = currentProject;
    }

    public ScannerContext(ArrayList<Issue> issues) {
        this.reporter = new Reporter(issues);
    }

    public Reporter getReporter() {
        return reporter;
    }

    Document getCurrentDocument() {
        return currentDocument;
    }

    Module getCurrentModule() {
        return currentModule;
    }

    Project getCurrentProject() {
        return currentProject;
    }
}
