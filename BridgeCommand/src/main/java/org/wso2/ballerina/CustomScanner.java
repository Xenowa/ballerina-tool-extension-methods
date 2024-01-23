package org.wso2.ballerina;

import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;
import io.ballerina.projects.plugins.CompilationAnalysisContext;
import io.ballerina.projects.plugins.CompilerPlugin;
import io.ballerina.tools.diagnostics.Diagnostic;
import io.ballerina.tools.diagnostics.DiagnosticFactory;
import io.ballerina.tools.diagnostics.DiagnosticInfo;
import io.ballerina.tools.diagnostics.DiagnosticProperty;
import io.ballerina.tools.diagnostics.DiagnosticSeverity;
import io.ballerina.tools.diagnostics.Location;
import org.wso2.ballerinalang.compiler.diagnostic.BLangDiagnosticLocation;
import org.wso2.ballerinalang.compiler.diagnostic.properties.NonCatProperty;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public abstract class CustomScanner extends CompilerPlugin {
    public void reportExternalIssue(int startLine,
                                    int startLineOffset,
                                    int endLine,
                                    int endLineOffset,
                                    String message,
                                    Document reportedDocument,
                                    Module reportedModule,
                                    Project reportedProject,
                                    CompilationAnalysisContext context) {
        String moduleName = reportedModule.moduleName().toString();
        String documentName = reportedDocument.name();
        Path issuesFilePath = reportedProject.documentPath(reportedDocument.documentId()).orElse(null);

        if (issuesFilePath != null) {
            // Create a new issue
            Issue issue = new Issue(startLine,
                    startLineOffset,
                    endLine,
                    endLineOffset,
                    "S1001", // TODO: Generated internally
                    message,
                    "CUSTOM_CHECK_VIOLATION", // TODO: Labelled internally
                    moduleName + "/" + documentName,
                    issuesFilePath.toString());

            // Retrieve the location of the issue
            Location issueLocation = new BLangDiagnosticLocation(issue.getReportedFilePath(),
                    issue.getStartLine(),
                    issue.getEndLine(),
                    issue.getStartLineOffset(),
                    issue.getEndLineOffset());

            // Create Diagnostics information
            DiagnosticInfo issueInfo = new DiagnosticInfo(
                    "SCAN_TOOL_DIAGNOSTICS",
                    issue.getMessage(),
                    DiagnosticSeverity.INTERNAL);

            // Add additional diagnostics properties relevant to the issue
            List<DiagnosticProperty<?>> diagnosticProperties = new ArrayList<>();

            // Adding the issue Object to diagnostics
            NonCatProperty issueDiagnostics = new NonCatProperty(issue);
            diagnosticProperties.add(issueDiagnostics);

            // Create a new diagnostic
            Diagnostic diagnosticIssue = DiagnosticFactory.createDiagnostic(issueInfo,
                    issueLocation,
                    diagnosticProperties, issue);

            // Report the diagnostic
            context.reportDiagnostic(diagnosticIssue);
        }
    }
}