# WIP

This project is still in WIP, the main purpose of this project to make javascript(.js) and typescript(.ts) able to run within Java.

This is still in develop and class design, when this project finish i'll put it into maven

This project is build on top of "GraalVM" that allow user to execute the .js with almost native performance of nodejs. which is base on oracle/graaljs on github

I just want to put this on github. since no-one develop easy .js runtime on this. and maybe you can use my docker image to run GraalVM. please check my repo "docker-graalvm"

## Requirements

- JDK 25+
- Maven 3.9+

## Running on JDK 25

GraalVM Truffle uses restricted native access on JDK 25. To silence the
runtime warnings (and to be forward-compatible with future JDKs that will
make these warnings errors), launch your app with:

```
java --enable-native-access=ALL-UNNAMED -jar your-app.jar
```

The test suite already sets this via the Surefire `argLine` so `mvn test`
runs cleanly.
