import java.io.FileOutputStream;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        ConcurrentLinkedQueue<String> taskQueue = new ConcurrentLinkedQueue<>();
        int num = 10;
//      waits for all tasks to be include
        CountDownLatch latch = new CountDownLatch(num);
//      controlling concurrent tasks.
        Semaphore semaphore = new Semaphore(2);
//      for managing threads and having a pool of threads.
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (int i = 0; i < num; i++) {
            final int finalI = i;
//          uses its threads to do the tasks that have handed to it.
            executor.submit(() -> {
                String task = Thread.currentThread().getName() + " - Task " + finalI;
                taskQueue.add("Task -> "+ finalI);
                System.out.println("Added : " + task);
//              counts the number of tasks that have left.
                latch.countDown();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
//            can be used for threads to add tasks, respectively.
            });
        }

        System.out.println(latch.getCount());
        Thread.sleep(1000);
//      waits to make sure that the executor have added all the tasks.
        latch.await();
        System.out.println(latch.getCount());
        System.out.println();
        Thread.sleep(2000);

        Runnable taskProcessor = () -> {
            try {
                while (!taskQueue.isEmpty()) {
//                  asks for permission to do the rest.
                    semaphore.acquire();
                    String task = taskQueue.poll();
                    if (task != null) {
                        System.out.println(Thread.currentThread().getName() + " is processing " + task);
                        Thread.sleep(1000);
//                      tells that the mission is does and other threads are able to do the rest.
                        semaphore.release();
                    }
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        };


        for (int i = 0; i < 5; i++) {
//          executes the tasks by the given number when it was defined.
            executor.submit(taskProcessor);
        }

        executor.shutdown();
    }
}
