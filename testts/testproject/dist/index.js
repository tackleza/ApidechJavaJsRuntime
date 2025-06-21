"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
// Import your classes
const greeter_1 = require("./greeter");
const person_1 = require("./person");
// Use them together
const alice = new person_1.Person("Alice", 30);
const greeter = new greeter_1.Greeter(alice.name);
console.log(alice.describe()); // “Alice is 30 years old.”
console.log(greeter.greet()); // “Hello, Alice!”
