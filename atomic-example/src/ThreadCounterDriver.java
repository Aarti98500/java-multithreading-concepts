import java.util.concurrent.atomic.AtomicInteger;

public class ThreadCounterDriver {
    public static void main(String[] args) {
        Counter counter = new Counter(new AtomicInteger(100));//object got created on heap(MM)

        Thread threadCounter1 = new ThreadCounter(counter);
        threadCounter1.setName("Counter-1");
        Thread threadCounter2 = new ThreadCounter(counter);
        threadCounter2.setName("Counter-2");

        threadCounter1.start();//run method called
        threadCounter2.start();//run method called
    }
}
