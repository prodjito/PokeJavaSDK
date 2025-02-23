# PokeJavaSDK

## Overview

The PokeJavaSDK is a Java-based SDK for retrieving Pokemon and Generation data from the PokeAPI, either one object at a time or all at once. It supports cursor-based pagination, retry logic for failed requests and concurrent fetching of full objects based on partial responses. Since this is a proof of concept, not all attributes of Pokemon and Generation schemas are implemented. JUnit is used for integration testing. Jackson is used for JSON parsing. Maven is used for dependency management. More on the design decisions below.

## Installation

Prerequisites:
* Java 11 or later. The SDK uses `HttpClient` which was introduced in Java SE 11.
* Maven for dependency management

Maven dependencies:
```
<dependency>
  <groupId>com.fasterxml.jackson.core</groupId>
  <artifactId>jackson-databind</artifactId>
  <version>2.16.0</version>
</dependency>
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-api</artifactId>
  <version>5.9.1</version>
</dependency>
<dependency>
  <groupId>org.junit.jupiter</groupId>
  <artifactId>junit-jupiter-engine</artifactId>
  <version>5.9.1</version>
</dependency>
```

The following command compiles the code, runs the integration tests, and builds the jars:

```
mvn clean package
```
The JAR will be created in `target/`.

## Usage

Please see [Main.java](src/main/java/Main.java).

## Package Structure

The project follows a structured package hierarchy to maintain modularity and clarity. Since the fundamental strategy for consuming the Pokemon and Generation endpoints are the same, that logic was modularized and can be found in [src.main.java.api](src/main/java/api) package.

## Design Decisions

### Handling Pagination
The SDK follows pagination using the `next` attribute from API responses. Retrieval stops when `next` is `null`.

### Concurrent Fetching

Uses `CompletableFuture` with a fixed thread pool to fetch full objects asynchronously. This speeds up performance compared to sequential fetching and is necessary because the PokeAPI only supports fetching one full object at a time.

### Retry Mechanism

Retries failed requests up to 3 times, with a 1-second delay between attempts.

## Dependencies & Tools Used
* Java 11+: The SDK uses `HttpClient` which was introduced in Java SE 11.
* Jackson: JSON serialization / deserialization.
* CompletableFuture: Asynchronous fetching of full objects.
* JUnit: Integration testing for `PokemonService.java` and `GenerationService.java`.
