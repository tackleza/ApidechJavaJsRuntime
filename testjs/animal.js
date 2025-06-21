// animal.js

/**
 * Abstract base class Animal with two abstract methods: walk() and talk().
 */
class Animal {
  constructor() {
    if (new.target === Animal) {
      throw new Error("Cannot instantiate abstract class Animal directly");
    }
  }

  /**
   * Abstract method: subclasses must implement.
   */
  walk() {
    throw new Error("Abstract method 'walk' must be implemented by subclass");
  }

  /**
   * Abstract method: subclasses must implement.
   */
  talk() {
    throw new Error("Abstract method 'talk' must be implemented by subclass");
  }
}

/**
 * Concrete class Cat extends Animal and overrides walk() and talk().
 */
class Cat extends Animal {
  constructor(name) {
    super();
    this.name = name;
  }

  walk() {
    console.log(`${this.name} is walking gracefully.`);
  }

  talk() {
    console.log(`${this.name} says: Meow!`);
  }
}

// Export for Node.js
if (typeof module !== 'undefined' && module.exports) {
  module.exports = { Animal, Cat };
}
