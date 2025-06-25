function hello(name) {
  console.log(`[JS] Hello, ${name}!`);
}

// Example usage
// console.log(hello("Tackle"));

// Exporting function for testing purposes (Node.js)
if (typeof module !== 'undefined' && module.exports) {
  module.exports = hello;
}
