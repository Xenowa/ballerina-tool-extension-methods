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
import org.wso2.ballerinalang.compiler.diagnostic.BLangDiagnosticLocation;
import org.wso2.ballerinalang.compiler.diagnostic.properties.BStringProperty;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CustomTask implements AnalysisTask<SyntaxNodeAnalysisContext> {
    @Override
    public void perform(SyntaxNodeAnalysisContext context) {
        // Get access to the current module
        Module module = context.currentPackage().module(context.moduleId());
        // Get access to the current document
        Document document = module.document(context.documentId());
        // Get access to the current project
        Project project = module.project();

        // Retrieve current document path
        Path documentPath = project.documentPath(document.documentId()).orElse(null);
        if (documentPath != null) {
            // Retrieve current module name
            String moduleName = module.moduleName().toString();
            // Retrieve current document name
            String documentName = document.name();

            // Retrieve the location of the issue
            Location issueLocation = new BLangDiagnosticLocation(moduleName + "/" + documentName,
                    0,
                    0,
                    0,
                    0);

            // Create Diagnostics information
            DiagnosticInfo issueInfo = new DiagnosticInfo(
                    "SCAN_TOOL_DIAGNOSTICS",
                    "Custom compiler plugin issue",
                    DiagnosticSeverity.INTERNAL);

            // Add additional diagnostics properties relevant to the issue
            List<DiagnosticProperty<?>> diagnosticProperties = new ArrayList<>();

            // Adding the file name as a diagnostic property
            DiagnosticProperty<String> filePath = new BStringProperty(documentPath.toString());
            diagnosticProperties.add(filePath);

            // Create a new diagnostic
            Diagnostic diagnosticIssue = DiagnosticFactory.createDiagnostic(issueInfo,
                    issueLocation,
                    diagnosticProperties, issueInfo);

            // Report the diagnostic
            context.reportDiagnostic(diagnosticIssue);
        }
    }
}
