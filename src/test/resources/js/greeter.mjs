export function greet(name) {
    return `Hi ${name}`;
}

export class Greeter {
    constructor(prefix) {
        this.prefix = prefix;
    }
    say(name) {
        return `${this.prefix}, ${name}`;
    }
}
