public class Counter {
    int count;

    Counter(int count) {
        this.count = count;
    }

    void count() throws InterruptedException {
        int i = 1;//local variable is stored inside thread's local cache or stack
        for (; i <= 100; i++) {
            Thread.sleep(10);
            this.count = this.count+1;//update count is 3 step process
            System.out.println(Thread.currentThread().getName() + " i = " + " count = "+ count);
        }
    }
}
