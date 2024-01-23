# BridgeCommand

## About

- BridgeCommand is a ballerina tool
- This tool adds "bal bridge" command support for ballerina
- The goal of the tool is to share a context between a Ballerina compiler plugin

## How it works

1. First a ballerina project should be opened

2. Next run the following command in the console:

```cmd
bal bridge
```

3. If successful, then will produce a console output

## Features

- Pass a context from the tool to a compiler plugin

## Usage (Local)
1. Replace the `jarURL` [here](https://github.com/Xenowa/ballerina-tool-plugin-bridge/blob/thin-jar-service-loading/BridgeCommand/src/main/java/org/wso2/ballerina/BridgeCommand.java#L128)
   with the absolute path to the compiler plugin

2. Run and build jar file

```cmd
gradlew clean build -p BridgeCommand
```

3. Navigate to the tool-bridge directory

```cmd
cd tool-bridge
```

4. Generate a bala file

```cmd
bal pack
```

5. Push the bala file to local repository

```cmd
bal push --repository=local
```

6. Move the tool_bridge to the central.ballerina.io, bala folder

```
📦<USER_HOME>/.ballerina/repositories/central.ballerina.io
 ┗ 📦bala
    ┗📦tharana_wanigaratne
      ┗**📦tool_bridge**
📦local
```

7. modify the .config folders following files

```
📦<USER_HOME>/.ballerina
 ┗ 📦.config
    ┗**📜bal-tools.toml**
    ┗**📜dist-2201.8.2.toml**
```

8. Include the tool details in them as follows

```
# (bal-tools.toml)
[[tool]]
id = "bridge"
org = "tharana_wanigaratne"
name = "tool_bridge"
```

```
# (dist-2201.8.2.toml)
[[tool]]
id = "bridge"
org = "tharana_wanigaratne"
name = "tool_bridge"
version = "0.1.0"
```

9. Check if the tool is added using the cmd

```cmd
bal tool list
```

10. Try out the tool