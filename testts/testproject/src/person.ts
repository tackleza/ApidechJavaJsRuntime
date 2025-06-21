// Export another class
export class Person {
  constructor(public name: string, public age: number) {}

  describe() {
    return `${this.name} is ${this.age} years old.`;
  }
}
