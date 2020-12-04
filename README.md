# Quarkus Github Regression Issue

Service to manage Quarkus Regression Issue [^1] 

This service will be registered as a Horreum [^2] webhook endpoint within a firewalled performance lab, and updates the Quarkus Github issue with results of regression analysis.   

## Required Configuration


## Running in OCP cluster



## Running in standalone container

## Development

### Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

### Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `github-regression-issue-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```

The application is now runnable using `java -jar target/github-regression-issue-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Pnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Pnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/github-regression-issue-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.html.



[^1]: https://github.com/quarkusio/quarkus/issues/-1
[^2]: https://horreum.hyperfoil.io/
