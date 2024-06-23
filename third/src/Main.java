import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        AtomicInteger success = new AtomicInteger(0);
        AtomicInteger unSuccess = new AtomicInteger(0);

        Runnable runnable = () -> {
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
            if (getRandomBoolean()){
                success.incrementAndGet();
            }else{
                unSuccess.incrementAndGet();
            }
        };

        /*
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(25);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                if (getRandomBoolean()){
                    success.incrementAndGet();
                }else{
                    unSuccess.incrementAndGet();
                }
            }
        };
        Can be replaced.
        */

        ExecutorService executorService = Executors.newFixedThreadPool(25);
        for (int i = 0; i < 100; i++) {
            executorService.submit(runnable);
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(110, TimeUnit.MILLISECONDS);
            System.out.println("Successful requests : " + success.get());
            System.out.print("Unsuccessful requests : " + unSuccess.get());
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }

    }
    public static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
}