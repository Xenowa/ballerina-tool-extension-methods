package org.arc.scanner;

import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.plugins.AnalysisTask;
import io.ballerina.projects.plugins.SyntaxNodeAnalysisContext;
import org.wso2.ballerina.Reporter;
import org.wso2.ballerina.ScannerContext;

public class CustomTask implements AnalysisTask<SyntaxNodeAnalysisContext> {
    private final Reporter reporter;

    public CustomTask(ScannerContext scannerContext) {
        reporter = scannerContext.getReporter();
    }

    @Override
    public void perform(SyntaxNodeAnalysisContext context) {
        // Report issues through the context reporter
        Module module = context.currentPackage().module(context.moduleId());
        Document document = module.document(context.documentId());

        reporter.reportIssue(0,
                0,
                0,
                0,
                "C1001",
                "External issue",
                "EXTERNAL_CHECK_VIOLATION",
                document,
                document.module(),
                document.module().project());
    }
}
