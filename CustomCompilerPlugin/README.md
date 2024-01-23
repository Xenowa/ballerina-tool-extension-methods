# CustomCompilerPlugin

## About

- CustomCompilerPlugin is a ballerina compiler plugin
- This plugin shares a context with the Ballerina Bridge Tool

## How it works

1. First a ballerina project should be opened

2. Run Ballerina bridge command

```cmd
bal bridge
```

## Features

- Pass a context from to a Ballerina tool

## Usage (Local)

1. Run and build jar file

```cmd
gradlew clean build -p CustomCompilerPlugin
```

2. Navigate to the custom-compiler-plugin directory

```cmd
cd custom-compiler-plugin
```

3. Generate a bala file

```cmd
bal pack
```

4. Push the bala file to local repository

```cmd
bal push --repository=local
```

5. Move the custom_compiler_plugin to the central.ballerina.io, bala folder

```
📦<USER_HOME>/.ballerina/repositories/central.ballerina.io
 ┗ 📦bala
    ┗📦tharana_wanigaratne
      ┗**📦custom_compiler_plugin**
📦local
```

6. Try out the plugin