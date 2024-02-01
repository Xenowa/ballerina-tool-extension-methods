package org.arc.scanner;

import io.ballerina.compiler.syntax.tree.SyntaxKind;
import io.ballerina.projects.plugins.CodeAnalysisContext;
import io.ballerina.projects.plugins.CodeAnalyzer;
import org.wso2.ballerina.ScannerContext;

public class CustomCodeAnalyzer extends CodeAnalyzer {
    private final ScannerContext scannerContext;

    public CustomCodeAnalyzer(ScannerContext scannerContext) {
        this.scannerContext = scannerContext;
    }

    @Override
    public void init(CodeAnalysisContext codeAnalysisContext) {
        codeAnalysisContext.addSyntaxNodeAnalysisTask(new CustomTask(scannerContext), SyntaxKind.FUNCTION_BODY_BLOCK);
    }
}
