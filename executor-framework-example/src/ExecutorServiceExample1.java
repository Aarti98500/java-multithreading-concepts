import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ExecutorServiceExample1 {
    public static void main(String[] args)
            throws InterruptedException, ExecutionException {


        //Create executorService using ThreadPoolExecutor
        // implementation of ExecutorService.

        ExecutorService executorService = new ThreadPoolExecutor(
                1,
                5,
                5000L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        //Create callable tasks using lambda implementation
        // of call method of Callable Interface

        Callable<String> callableTask = () -> {
            System.out.println(Thread.currentThread().getName() + " Call method called.");
            TimeUnit.MILLISECONDS.sleep(5000);
            return "Task execution in call method completed";
        };

        //submit single callable task to executorService, that is returning a Future
        Future<String> future = executorService.submit(callableTask);
        System.out.println(Thread.currentThread().getName() +" "+ future.get());

        //Create list of callable tasks
        List<Callable<String>> callableTaskList = new ArrayList<>();
        callableTaskList.add(callableTask);
        callableTaskList.add(callableTask);
        callableTaskList.add(callableTask);
        callableTaskList.add(callableTask);
        callableTaskList.add(callableTask);
        callableTaskList.add(callableTask);
        callableTaskList.add(callableTask);
        callableTaskList.add(callableTask);
        callableTaskList.add(callableTask);

        //submit the list of callable tasks to invokeAny method that returns a result.
        String result = executorService.invokeAny(callableTaskList);
        System.out.println(result);

        //submit the list of callable tasks to invokeAll method that returns a list
        // of futures representing results of asynchronous tasks.
        List<Future<String>> futures = executorService.invokeAll(callableTaskList);


        futures.forEach((stringFuture) -> {
            try {
                System.out.println(stringFuture.get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });

        //shutdown the executorService after completing all tasks to reclaim memory.
        executorService.shutdown();

        System.out.println("Main thread is waiting for 5s");
        TimeUnit.MILLISECONDS.sleep(5000);
        System.out.println(Thread.currentThread().getName() + " done execution");
    }
}
