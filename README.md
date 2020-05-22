# Modern day Java Concurrency

Collection of modern concurreny Apis and best practices to follow for buidling a secure and robust concurrent application in Java.


<h2>JDK 1.5</h2>
With jdk 1.5 came the modern package `java.util.concurrent` which contains modern tools for dealing with threads. Its a huge package with lots of Classes and Interfaces which we will explore over the time but now let's start with most frequently used.

> The most important idea is the separation of tasks and the policy for their execution. 

* A task is something that you want thread to execute(like a job) and the most common Interfaces to create a task are :
  * **java.lang.Runnable** - a task which doesn't return a value (like a void method).
  * **Callable** - a task which can return a value (this is the more flexible form) (prefer this!)

* For executing this task we need some executing Interface. The 3 important interfaces, which are linked in an inheritance chain. Starting at the top level, they are: 
  * **Executor** - a single execute method
  * **ExecutorService** - common workhorse; submit and invoke methods; includes lifecycle methods
  * **ScheduledExecutorService** - executes tasks after a specific time interval, or periodically 

*   To catch the result of certain async task after completion we can use the following interfaces (just like promises in Javascript) :
    * **Future** - see the result of an asynchronous task, or cancel it.
    * **CompletionService** - accepts tasks, and returns their Futures. A service that decouples the production of new asynchronous tasks from the consumption of the results of completed tasks.
    * **ExecutorCompletionService** - an implementation of CompletionService interface.  This class arranges that submitted tasks are, upon completion, placed on a queue accessible using take. The class is lightweight enough to be suitable for transient use when processing groups of tasks. 


* JDK 1.9 included a `publish-subscribe` framework for reactive streams. More details [here]()

* For Synchronization between the threads we should use either of the aid classes :
  * CountDownLatch : A synchronization aid that allows one or more threads to wait until a set of operations being performed in other threads completes.
  * CyclicBarrier : A synchronization aid that allows a set of threads to all wait for each other to reach a common barrier point. CyclicBarriers are useful in programs involving a fixed sized party of threads that must occasionally wait for each other. The barrier is called cyclic because it can be re-used after the waiting threads are released. 
  * FutureTask : A cancellable asynchronous computation. This class provides a base implementation of Future, with methods to start and cancel a computation, query to see if the computation is complete, and retrieve the result of the computation.

 * **Lifecycle** of a submitted task to an executor
    1. *created* - the task object has been created, but not yet submitted to an Executor.
    2. *submitted* - the task has been submitted to an Executor, but hasn't been started yet. In this state, the task can always be cancelled.
    3. *started* - the task has begun. The task may be cancelled, if the task is responsive to interruption.
    4. *completed* - the task has finished. Cancelling a completed task has no effect. 

# Examples

| Name                                                                 | Summary                               |
| -------------------------------------------------------------------- | ------------------------------------- |
| [Pinging N websites in parallel](./ping_n_websites/)                 | Callable, Executor, CompletionService |
| [fetching the list of available printers](./fetch_list_of_printers/) | Runnable, Executor, CountDownLatch |


 # Definitions
    
   * **user thread**, **daemon thread** - in Java, the JRE terminates when all user threads terminate; a single user thread will prevent the JRE from terminating. A daemon thread, on the other hand, will not prevent the JRE from terminating.
   * **blocking** - if a thread pauses execution until a certain condition is met, then it's said to be blocking.
   * **safety** - when nothing unpleasant happens in a multithreaded environment (this is admittedly vague).
    * **liveness** - execution continues, eventually; there may be significant pauses in a thread, but the application never completely hangs.
    * **deadlock** - execution hangs, since a pair of threads each holds (and keeps) a lock that the other thread needs in order to continue.
    * **race condition** - if you are unlucky with the timing of how threads interleave their execution, then bad things will happen.
   * **re-entrant** - if a thread already holds a   re-entrant lock, and if it needs to re-acquire the lock, then the re-acquisition always succeeds without blocking. Most locks are re-entrant, including Java's intrinsic locks.
    * **intrinsic lock** - the built-in locks of the Java language. These correspond to uses of the synchronized keyword.
    * **mutex lock** - mutually exclusive locks are held by at most one thread at a time. For example, intrinsic locks are mutexes. Read-write locks are not mutexes, since they allow N readers to access the same data at once.
    * **thread confinement** - data that is only accessed by a single thread is always safe, since it's confined to a single thread.
    * **semaphore** - object pools containing a limited number of resources use a semaphore to keep track of how many of the resources are currently in use. 

