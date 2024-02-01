# Architecture: Ballerina Distribution Bundling based Approach

```mermaid
sequenceDiagram
    participant Bridge Tool (in Distribution)
    participant MyScannerPlugin extends ScannerCompilerPlugin
    activate Bridge Tool (in Distribution)
    Bridge Tool (in Distribution) ->> Bridge Tool (in Distribution): Perform core scan
    Bridge Tool (in Distribution) ->> MyScannerPlugin extends ScannerCompilerPlugin: Engage through package compilation
    activate MyScannerPlugin extends ScannerCompilerPlugin
    MyScannerPlugin extends ScannerCompilerPlugin ->> MyScannerPlugin extends ScannerCompilerPlugin: Create static ScannerContext, <br> perform scans and report issues <br> via the Reporter of it
    deactivate MyScannerPlugin extends ScannerCompilerPlugin
    Bridge Tool (in Distribution) ->> MyScannerPlugin extends ScannerCompilerPlugin: Request ScannerContext
    activate MyScannerPlugin extends ScannerCompilerPlugin
    MyScannerPlugin extends ScannerCompilerPlugin ->> Bridge Tool (in Distribution): Send static ScannerContext
    deactivate MyScannerPlugin extends ScannerCompilerPlugin
    Bridge Tool (in Distribution) ->> Bridge Tool (in Distribution): Add external issues from reporter of <br> ScannerContext to issues array
    deactivate Bridge Tool (in Distribution)
```

# Ballerina Tool extension methods

Ballerina language has two main extension points:

- Ballerina Tools
- Ballerina Compiler Plugins

This project aims to extend the functionalities of a Ballerina tool via compiler plugins and simulate a
simplified version of the Ballerina scan tool

## Ballerina Tools

Allow developers to create custom Ballerina commands like:

```cmd
bal bridge
```

## Ballerina Compiler Plugins

Allow developers to extend features of the Ballerina compiler

```bal
import org/custom_compiler_plugin as _;

public function main(){
}
```

## Prerequisites

The following software should be installed locally

- [Java version: 17](https://adoptium.net/temurin/releases/?version=17)
- [Ballerina version: 2201.8.4](https://ballerina.io/downloads/archived/#swan-lake-archived-versions)

## Getting started

1. Create the custom compiler plugin by following the
   instructions [here](https://github.com/Xenowa/ballerina-tool-extension-methods/tree/tool-plugin-based-approach/CustomCompilerPlugin)

2. Create the bal bridge tool by following the
   instructions [here](https://github.com/Xenowa/ballerina-tool-extension-methods/tree/tool-plugin-based-approach/BridgeCommand)

3. Navigate to the test-bridge-command directory

4. Run "bal bridge" command