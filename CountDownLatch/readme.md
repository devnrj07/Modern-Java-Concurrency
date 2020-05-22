# CountDownLatch

CoundDownLatch enables you to make a thread wait till all other threads are done with their execution.

Pseudo code can be:
>// Main thread starts
// Create CountDownLatch for N threads
// Create and start N threads
// Main thread awaits on latch
// N threads completes there tasks are returns
// Main thread resume execution

Here, **await()** method waits for countdownlatch flag to become 0, and **countDown()** method decrements countdownlatch flag by 1.

**Real Life usecases**
Use CountDownLatch when one thread (like the main thread) requires to wait for one or more threads to complete, before it can continue processing.

A classical example of using CountDownLatch in Java is a server side core Java application which uses services architecture, where multiple services are provided by multiple threads and the application cannot start processing until all services have started successfully.