import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> tasks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            int finalI = i;
            tasks.add(() -> {
                Thread.sleep(1000);
                int num = (((finalI * 2) + 3) * 2 + 4) + 10;
                return (int) (Math.random() * num);
            });
        }

        List<Future<Integer>> doneTasks = pool.invokeAll(tasks);

        for (Future<Integer> doneTask : doneTasks) {
            if (doneTask.isDone() && !doneTask.isCancelled()) {
                System.out.println("Result : " + doneTask.get());
            } else {
                System.out.println("Task has been cancelled or has been done.");
            }
        }

        pool.shutdown();
    }
}