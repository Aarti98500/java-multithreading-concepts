import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


public class Main {
    public static void main(String[] args) {

        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(1);

        Random random = new Random();
        Runnable producerTask = new Runnable() {
            @Override
            public void run() {
                while (true){
                    int i = random.nextInt(100);
                    try {
                        blockingQueue.put(i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(Thread.currentThread().getName()+" produced : "+i);
                    try {
                        Thread.sleep(random.nextInt(2000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };

        Runnable consumerTask = new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Integer i = blockingQueue.take();
                        System.out.println(Thread.currentThread().getName()+" consumed : "+i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        Thread.sleep(random.nextInt(2000));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };


        Thread producerThread = new Thread(producerTask);
        producerThread.setName("ProducerThread");
        producerThread.start();

        Thread consumerThread = new Thread(consumerTask);
        consumerThread.setName("ConsumerThread");
        consumerThread.start();

    }
}
