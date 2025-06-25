// Abstract class similar to Java's Runnable
abstract class Runnable {
  abstract run(): void;
}

// Class implementing Runnable
class MyTask extends Runnable {
  run(): void {
    console.log("MyTask is running...");
  }
}

// Using the class
// const task: Runnable = new MyTask();
// task.run();