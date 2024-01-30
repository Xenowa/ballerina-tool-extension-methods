package org.wso2.ballerina;

public interface CustomScannerPlugin {
    void init(ScannerContext context);

    void perform();
}