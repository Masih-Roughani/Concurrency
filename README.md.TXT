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
