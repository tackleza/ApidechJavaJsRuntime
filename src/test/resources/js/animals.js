class Animal {
    constructor(name) { this.name = name; }
    speak() { return this.name + " makes a sound"; }
}

class Cat extends Animal {
    constructor(name) { super(name); }
    speak() { return this.name + " meows"; }
}

class Rock {
    constructor() {}
}
