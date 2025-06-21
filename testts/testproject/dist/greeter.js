"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Greeter = void 0;
// Export a simple class
class Greeter {
    constructor(greeting) {
        this.greeting = greeting;
    }
    greet() {
        return `Hello, ${this.greeting}!`;
    }
}
exports.Greeter = Greeter;
