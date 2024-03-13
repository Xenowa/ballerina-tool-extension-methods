package org.wso2.ballerina;

import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;

public interface ScannerContext {
    Reporter getReporter();

    Document getCurrentDocument();

    Module getCurrentModule();

    Project getCurrentProject();
}
