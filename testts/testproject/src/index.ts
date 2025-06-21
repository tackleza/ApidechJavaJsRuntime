// Import your classes
import { Greeter } from "./greeter";
import { Person } from "./person";

// Use them together
const alice = new Person("Alice", 30);
const greeter = new Greeter(alice.name);

console.log(alice.describe());   // “Alice is 30 years old.”
console.log(greeter.greet());    // “Hello, Alice!”
