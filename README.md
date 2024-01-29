# Architecture: ClassLoader based Approach

```mermaid
sequenceDiagram
    participant BridgeTool
    participant SensorContextFactory 1
    note over BridgeTool, SensorContextFactory 1: Uses CustomToolClassLoader
    participant SensorContextFactory 2
    participant CustomCompilerPlugin
    note over SensorContextFactory 2, CustomCompilerPlugin: Uses URLClassLoader
    activate BridgeTool
    BridgeTool ->> SensorContextFactory 1: Send SensorContext Object
    activate SensorContextFactory 1
    SensorContextFactory 1 ->> SensorContextFactory 1: Initialize static sensorcontext attribute through serializing
    deactivate SensorContextFactory 1
    BridgeTool ->> CustomCompilerPlugin: Engage through package compilation
    activate CustomCompilerPlugin
    CustomCompilerPlugin ->> SensorContextFactory 2: Request context
    activate SensorContextFactory 2
    SensorContextFactory 2 ->> SensorContextFactory 1: Request SensorContext Object through CustomToolClassLoader
    activate SensorContextFactory 1
    SensorContextFactory 1 ->> SensorContextFactory 2: Send serialized static context
    deactivate SensorContextFactory 1
    SensorContextFactory 2 ->> CustomCompilerPlugin: Send De-serialized context
    deactivate SensorContextFactory 2
    CustomCompilerPlugin ->> CustomCompilerPlugin: Report issues through context
    CustomCompilerPlugin ->> SensorContextFactory 2: Call save method
    activate SensorContextFactory 2
    SensorContextFactory 2 ->> SensorContextFactory 2: Serialize context
    SensorContextFactory 2 ->> SensorContextFactory 1: Save serialized context
    deactivate SensorContextFactory 2
    activate SensorContextFactory 1
    BridgeTool ->> SensorContextFactory 1: Requests external issues
    SensorContextFactory 1 ->> BridgeTool: Get external issues through context
    BridgeTool ->> BridgeTool: Add external issues to issues array
    deactivate SensorContextFactory 1
    deactivate CustomCompilerPlugin
    deactivate BridgeTool
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