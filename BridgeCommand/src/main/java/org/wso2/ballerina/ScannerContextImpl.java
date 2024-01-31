package org.wso2.ballerina;

import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;

import java.util.ArrayList;

public class ScannerContextImpl implements ScannerContext {
    private final Reporter reporter;
    private final Document currentDocument;
    private final Module currentModule;
    private final Project currentProject;

    // A sensor context is created for each document
    public ScannerContextImpl(ArrayList<Issue> issues,
                              Document currentDocument,
                              Module currentModule,
                              Project currentProject) {
        this.reporter = new Reporter(issues);
        this.currentDocument = currentDocument;
        this.currentModule = currentModule;
        this.currentProject = currentProject;
    }

    @Override
    public Reporter getReporter() {
        return reporter;
    }

    @Override
    public Document getCurrentDocument() {
        return currentDocument;
    }

    @Override
    public Module getCurrentModule() {
        return currentModule;
    }

    @Override
    public Project getCurrentProject() {
        return currentProject;
    }
}
