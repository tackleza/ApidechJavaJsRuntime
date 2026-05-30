# WIP

This project is still in WIP, the main purpose of this project to make javascript(.js) and typescript(.ts) able to run within Java.

This is still in develop and class design, when this project finish i'll put it into maven

This project is build on top of "GraalVM" that allow user to execute the .js with almost native performance of nodejs. which is base on oracle/graaljs on github

I just want to put this on github. since no-one develop easy .js runtime on this. and maybe you can use my docker image to run GraalVM. please check my repo "docker-graalvm"

## Requirements

- JDK 25+
- Maven 3.9+

## Build & test

```
mvn clean test
```

The suite ships 22 JUnit 5 tests under `src/test/java` covering `.js`/`.mjs`
loading, top-level function invocation, class instance methods, inheritance
checks, single-eval, and `tsconfig.json` parsing.

Fixture `.js` / `.mjs` files used by the tests live in `src/test/resources/js/`.

## Quick usage

### Load a `.js` file and call a top-level function

```java
ApidechJavaJsRuntime runtime = new ApidechJavaJsRuntime();
JsWorkingSpace ws = runtime.createWorkingSpace(new File("scripts"));

JsFile file = ws.compile(JsSource.create(new File("scripts/calculator.js")));

int sum = file.getFunction("add").execute(2, 3).getResult().asInt();
// sum == 5

ws.close();
runtime.shutdown();
```

### Call an instance method on a JS class

```java
JsClass calcClass = file.getClass("Calculator");
JsClassInstance calc = calcClass.newInstance(10);

calc.getMethod("add").execute(5);        // 15
calc.getMethod("multiply").execute(2);   // 30
int value = calc.getMethod("getValue").execute().asInt();
// value == 30
```

### Load an ES module (`.mjs`)

```java
JsFile module = ws.compile(JsSource.create(new File("scripts/greeter.mjs")));
// module.getResult() exposes the module's exports
```

Module vs script is inferred from the extension: `.mjs` → ES module,
everything else → classic script (so top-level `function` / `class`
declarations become global bindings).

### One-shot eval

```java
runtime.singleEval(result -> {
    if (result.isSuccess()) {
        System.out.println(result.getResult().asInt());
    } else {
        result.getException().printStackTrace();
    }
}, "1 + 2");
```

### Compile a TypeScript project

Requires `tsc` on `PATH` (`npm install -g typescript`).

```java
TypeScriptCompileResult result = ApidechJavaJsRuntime.compileTypeScript(
    new File("path/to/ts-project"));
```

## Running on JDK 25

GraalVM Truffle uses restricted native access on JDK 25. To silence the
runtime warnings (and to be forward-compatible with future JDKs that will
make these warnings errors), launch your app with:

```
java --enable-native-access=ALL-UNNAMED -jar your-app.jar
```

The test suite already sets this via the Surefire `argLine` so `mvn test`
runs cleanly.

For native-speed JS execution (no JVMCI fallback warning), run on a
GraalVM JDK distribution.

## What's working today

- Load `.js` files (top-level functions, classes, vars become global bindings)
- Load `.mjs` ES modules (returns the module's exports)
- CommonJS `require()` inside the working space (when a code directory is provided)
- Discover and invoke top-level functions with arg-count / arg-name introspection
- Instantiate JS classes from Java, invoke instance methods, list method names
- Inheritance checks (`isInstanceOf`, `getExtendedClassName`)
- Error reporting via `JsResult.isSuccess()` / `getException()`
- TypeScript project compilation via external `tsc`
