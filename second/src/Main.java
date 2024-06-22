import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        List<MutablePoint> unsafe = new ArrayList<>();
//        List<MutablePoint> safe = Collections.synchronizedList(unsafe);
        CopyOnWriteArrayList<MutablePoint> safe = new CopyOnWriteArrayList<>();

        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                safe.add(new MutablePoint(i, i + 1));
            }
        }).start();
        Thread.sleep(2000);
        for (int i = 0; i < 100; i++) {
            System.out.println(safe.get(i).toString());
        }
        Thread.sleep(2000);
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                safe.set(i, new MutablePoint(i * 2, (i + 1) * 2));
            }
        }).start();
        new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                safe.set(i, new MutablePoint(i * 3, (i + 1) * 3));
            }
        }).start();
        Thread.sleep(2000);
        for (int i = 0; i < 100; i++) {
            System.out.println(safe.get(i).toString());
        }
    }
}

class ImmutablePoint {
    private final int x;
    private final int y;

    public ImmutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class MutablePoint {
    private int x;
    private int y;
    private final ReentrantLock lock = new ReentrantLock();

    public MutablePoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(int x, int y) {
        lock.lock();
        this.x = x;
        this.y = y;
        lock.unlock();
    }

    public int[] get() {
        lock.lock();
        try {
            return new int[]{x, y};
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String toString() {
        return x + " -> " + y;
    }
}
//first one, has final fields and also doesn't have getter and setter, so it's thread safe.
//both lists are thread safe, and it's because of the usage of lock and unlock that works like synchronized.