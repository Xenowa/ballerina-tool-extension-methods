# CustomCompilerPlugin

## About

- CustomCompilerPlugin is a ballerina compiler plugin
- This plugin shares a context with the Ballerina Bridge Tool

## How it works

1. First a ballerina project should be opened

2. The compiler plugin should be provided as an import to the ballerina file

```bal
import tharana_wanigaratne/custom_compiler_plugin as _;

public function main(){
}
```

3. Run Ballerina bridge command

```cmd
bal bridge
```

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

## Features

- Pass a diagnostic object from a compiler plugin to a Ballerina tool