package org.wso2.ballerina.plugin;

import io.ballerina.compiler.api.SemanticModel;
import io.ballerina.compiler.syntax.tree.SyntaxTree;
import io.ballerina.projects.Document;
import io.ballerina.projects.Module;
import io.ballerina.projects.ModuleCompilation;
import io.ballerina.projects.Project;
import io.ballerina.projects.plugins.AnalysisTask;
import io.ballerina.projects.plugins.CodeAnalysisContext;
import io.ballerina.projects.plugins.CodeAnalyzer;
import io.ballerina.projects.plugins.CompilationAnalysisContext;
import io.ballerina.projects.plugins.CompilerPluginContext;
import org.wso2.ballerina.SensorContext;
import org.wso2.ballerina.SensorContextHolder;

public class CustomCompilerPlugin extends SensorContextHolder {
    // Method triggered via Package compilation
    @Override
    public void init(CompilerPluginContext compilerPluginContext) {
        compilerPluginContext.addCodeAnalyzer(new CodeAnalyzer() {
            @Override
            public void init(CodeAnalysisContext codeAnalysisContext) {
                codeAnalysisContext.addCompilationAnalysisTask(new AnalysisTask<CompilationAnalysisContext>() {
                    @Override
                    public void perform(CompilationAnalysisContext context) {
                        context.currentPackage().moduleIds().forEach(moduleId -> {
                            // Get access to the project modules
                            Module module = context.currentPackage().module(moduleId);

                            // Iterate through each document of the module
                            module.documentIds().forEach(documentId -> {
                                // Get access to the module documents
                                Document document = module.document(documentId);

                                // Retrieve the syntax tree from the parsed ballerina document
                                SyntaxTree syntaxTree = document.syntaxTree();

                                // Retrieve the compilation of the module
                                ModuleCompilation compilation = module.getCompilation();

                                // Retrieve the semantic model from the ballerina document compilation
                                SemanticModel semanticModel = compilation.getSemanticModel();

                                // Retrieve the current document path
                                Project project = module.project();

                                // Get the serialized context from file
                                SensorContext externalContext = getContext();

                                // Simulating performing a custom analysis by reporting a custom issue for each document
                                externalContext.getReporter().reportExternalIssue(0,
                                        0,
                                        0,
                                        0,
                                        "Custom compiler plugin issue",
                                        document,
                                        module,
                                        project);

                                // Save the reported data to file
                                saveContext();
                            });
                        });
                    }
                });
            }
        });
    }
}
