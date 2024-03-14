package org.arc.scanner;

import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;
import io.ballerina.projects.plugins.AnalysisTask;
import io.ballerina.projects.plugins.SyntaxNodeAnalysisContext;
import io.ballerina.tools.diagnostics.Diagnostic;
import io.ballerina.tools.diagnostics.DiagnosticFactory;
import io.ballerina.tools.diagnostics.DiagnosticInfo;
import io.ballerina.tools.diagnostics.DiagnosticProperty;
import io.ballerina.tools.diagnostics.DiagnosticSeverity;
import io.ballerina.tools.diagnostics.Location;
import org.wso2.ballerina.Issue;
import org.wso2.ballerina.ScannerContext;
import org.wso2.ballerina.ScannerContextImpl;
import org.wso2.ballerinalang.compiler.diagnostic.BLangDiagnosticLocation;
import org.wso2.ballerinalang.compiler.diagnostic.properties.BStringProperty;
import org.wso2.ballerinalang.compiler.diagnostic.properties.NonCatProperty;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CustomTask implements AnalysisTask<SyntaxNodeAnalysisContext> {
    @Override
    public void perform(SyntaxNodeAnalysisContext context) {
        // Simulating loading this class from compiler plugin side
        // Get the context from the build context
        ScannerContext bridgeContext = (ScannerContext) context.currentPackage().project().buildContext()
                .getData("bridgeContext");

        // Report issues through the context reporter
        Module module = context.currentPackage().module(context.moduleId());
        Document document = module.document(context.documentId());

        bridgeContext.getReporter().reportIssue(0,
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
