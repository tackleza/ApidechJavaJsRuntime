"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Person = void 0;
// Export another class
class Person {
    constructor(name, age) {
        this.name = name;
        this.age = age;
    }
    describe() {
        return `${this.name} is ${this.age} years old.`;
    }
}
exports.Person = Person;
