# BridgeCommand

## About

- BridgeCommand is a ballerina tool
- This tool adds "bal bridge" command support for ballerina
- The goal of the tool is to share a context between a Ballerina compiler plugin

## How it works

1. First a ballerina project should be opened

2. The compiler plugin should be provided as an import to the ballerina file

```bal
import tharana_wanigaratne/custom_compiler_plugin as _;

public function main(){
}
```

3. Next run the following command in the console:

```cmd
bal bridge
```

4. If successful, then will produce a console output

## Building from the source
Execute the commands below to build from the source.

1. Export Github Personal access token with read package permissions as follows,
    ```bash
    export packageUser=<Username>
    export packagePAT=<Personal access token>
    ```
2. To build the packages:
    ```bash
    ./gradlew clean build
    ```
   > **Note**: The bridge tool configurations will be appended to the contents of the `.ballerina/.config/bal-tools.toml` file during the build process.

## Features

- Pass a context from the Compiler plugin to a Ballerina tool

## Usage
1. Navigate to test-bridge-command Ballerina project
    ```bash
    cd test-bridge-command
    ```
2. Check if the `bridge` command is added by running
    ```bash
    bal tool list
    ```

3. Check if bridge command executes and returns results
    ```bash
    bal bridge
    ```