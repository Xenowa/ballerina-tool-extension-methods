package org.arc.scanner;

import io.ballerina.compiler.syntax.tree.SyntaxKind;
import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.Project;
import io.ballerina.projects.plugins.CodeAnalysisContext;
import io.ballerina.projects.plugins.CodeAnalyzer;
import io.ballerina.projects.plugins.CompilerPluginContext;
import org.wso2.ballerina.ScannerCompilerPlugin;
import org.wso2.ballerina.ScannerContext;

import java.nio.file.Path;

public class MyScannerPlugin extends ScannerCompilerPlugin {
    @Override
    public void init(CompilerPluginContext compilerPluginContext) {
        compilerPluginContext.addCodeAnalyzer(new CodeAnalyzer() {
            @Override
            public void init(CodeAnalysisContext codeAnalysisContext) {
                codeAnalysisContext.addSyntaxNodeAnalysisTask(context -> {
                    ScannerContext scannerContext = getScannerContext(compilerPluginContext);
                    // Get access to the current module
                    Module module = context.currentPackage().module(context.moduleId());
                    // Get access to the current document
                    Document document = module.document(context.documentId());
                    // Get access to the current project
                    Project project = module.project();

                    // Retrieve current document path
                    Path documentPath = project.documentPath(document.documentId()).orElse(null);
                    if (documentPath != null) {

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
                    saveScannerContext(compilerPluginContext);
                }, SyntaxKind.FUNCTION_BODY_BLOCK);
            }
        });
    }
}