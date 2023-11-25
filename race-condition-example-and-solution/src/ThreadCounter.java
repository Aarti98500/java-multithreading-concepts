public class ThreadCounter extends Thread {
    Counter counter;

    ThreadCounter(Counter counter) {
        this.counter = counter;
    }

    @Override
    public void run() {
        try {
            this.counter.count();
        } catch (InterruptedException interruptedException){
            interruptedException.printStackTrace();
        }
    }
}
