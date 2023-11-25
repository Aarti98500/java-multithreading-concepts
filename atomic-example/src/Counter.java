import java.util.concurrent.atomic.AtomicInteger;

public class Counter {
    AtomicInteger count;

    Counter(AtomicInteger count) {
        this.count = count;
    }

    void count() throws InterruptedException {
        int i = 1;//local variable is stored inside thread's local cache or stack
        for (; i <= 100; i++) {
            Thread.sleep(10);
            count.addAndGet(1) ;//update count is single step process
            System.out.println(Thread.currentThread().getName() + " i = " + " count = "+ count);
        }
    }
}
