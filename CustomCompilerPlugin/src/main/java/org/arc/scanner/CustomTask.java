package org.arc.scanner;

import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;
import io.ballerina.projects.plugins.AnalysisTask;
import io.ballerina.projects.plugins.SyntaxNodeAnalysisContext;
import org.wso2.ballerina.ScannerContext;

public class CustomTask implements AnalysisTask<SyntaxNodeAnalysisContext> {
    private final ScannerContext scannerContext;

    public CustomTask(ScannerContext scannerContext) {
        this.scannerContext = scannerContext;
    }

    @Override
    public void perform(SyntaxNodeAnalysisContext context) {
        // Get access to the current module
        Module module = context.currentPackage().module(context.moduleId());
        // Get access to the current document
        Document document = module.document(context.documentId());
        // Get access to the current project
        Project project = module.project();

        // Simulating performing a custom analysis by reporting a custom issue for each document
        scannerContext.getReporter().reportExternalIssue(0,
                0,
                0,
                0,
                "Custom compiler plugin issue",
                document,
                module,
                project);
    }
}
