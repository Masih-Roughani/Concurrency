first{
1-Making a ConcurrentLinkedQueue to save the tasks.
2-Making a CountDownLatch to make the thread wait for all the tasks to be added.
3-Making a Semaphore for managing the number tasks that are managing in a cuoncurrent way.
4-Making a ExecutorService for having and managing a ThreadPool.
5-Using a runnable to add tasks to the queue and decreasing the latch.
6-Submiting tasks.
7-Using latch.wait() to make sure all the tasks have been added.
8-Making a runnable to execute tasks by using the semaphore to manage the number of tasks, executing.
9-Submiting tasks.
10-Using shutdown() to for ExecutorService.
}

second{
1-Making a list using Collections.synchronizedList.
2-Making a concurrentList list.
3-Checking both threads and classes to see if they're thread safe or not.
}

third{
1-Making two AtomicInteger for successful or unsuccessful requests.
2-Making a Runnable for making random successful or unsuccessful requests.
3-Using an ExecutorService to submit some of the tasks.
4-Shuting down ExecutorService.
5-Using awaitTermination() to make sure all the tasks are done.
}


