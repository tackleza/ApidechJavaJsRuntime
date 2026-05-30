var greeting = "hello";

function add(a, b) {
    return a + b;
}

function greet(name) {
    return "Hello, " + name + "!";
}

class Calculator {
    constructor(initial) {
        this.value = initial;
    }
    add(n) {
        this.value += n;
        return this.value;
    }
    multiply(n) {
        this.value *= n;
        return this.value;
    }
    getValue() {
        return this.value;
    }
}
