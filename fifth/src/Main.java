import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ReadWriteLockCache<String, String> cache = new ReadWriteLockCache<>();
        CountDownLatch latch = new CountDownLatch(100);
        CountDownLatch finalLatch1 = latch;
        new Thread(() -> {
            for (int i = 0 ; i < 100 ; i++){
                cache.put("key" + i, "value" + i);
                System.out.println(Thread.currentThread().getName() + " wrote key" + i);
                finalLatch1.countDown();
            }
        }).start();

        latch.await();
        latch = new CountDownLatch(150);

        CountDownLatch finalLatch2 = latch;
        new Thread(() -> {
            for (int i = 50 ; i < 150 ; i++){
                cache.put("key" + i, "value" + i);
                System.out.println(Thread.currentThread().getName() + " wrote key" + i);
                finalLatch2.countDown();
            }
        }).start();

        latch.await();

        new Thread(() -> {
            for (int i = 0 ; i < 100 ; i++){
                String value = cache.get("key" + i);
                System.out.println(Thread.currentThread().getName() + " read key" + i + ": " + value);
            }
        }).start();

        new Thread(() -> {
            for (int i = 50 ; i < 150 ; i++){
                String value = cache.get("key" + i);
                System.out.println(Thread.currentThread().getName() + " read key" + i + ": " + value);
            }
        }).start();
    }
}

class ReadWriteLockCache<K, V> {
    private final Map<K, V> cache = new HashMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public V get(K key) {
        lock.readLock().lock();
        try {
            return cache.get(key);
        }finally {
            lock.readLock().unlock();
        }
    }

    public void put(K key, V value) {
        lock.writeLock().lock();
        cache.put(key, value);
        lock.writeLock().unlock();
    }
}