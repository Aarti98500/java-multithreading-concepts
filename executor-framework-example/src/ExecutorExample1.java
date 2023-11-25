import java.util.concurrent.*;

public class ExecutorExample1 {
    public static void main(String[] args) {

        Runnable runnableTask1 = () -> {
            try {
                System.out.println(Thread.currentThread().getName()+ " Run1 method called.");
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable runnableTask2 = () -> {
            try {
                System.out.println(Thread.currentThread().getName()+ " Run2 method called.");
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Runnable runnableTask3 = () -> {
            try {
                System.out.println(Thread.currentThread().getName()+ " Run3 method called.");
                TimeUnit.MILLISECONDS.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };


//        Executor executor = new ThreadPoolExecutor(
//                0,
//                10,
//                0L,
//                TimeUnit.MILLISECONDS,
//                new ArrayBlockingQueue<>(10)
//        );


        Executor executor = Executors.newScheduledThreadPool(0);

        executor.execute(runnableTask1);
        executor.execute(runnableTask2);
        executor.execute(runnableTask3);


        System.out.println(Thread.currentThread().getName());


    }
}
