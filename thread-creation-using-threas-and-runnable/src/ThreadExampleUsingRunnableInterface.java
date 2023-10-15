public class ThreadExampleUsingRunnableInterface {
    public static void main(String[] args) {
        System.out.println("main method start: "+ Thread.currentThread());
        NumberPrinter numberPrinter = new NumberPrinter();

        Runnable task1 = new NumberPrinterRunnableImpl(numberPrinter);
        Thread thread1 = new Thread(task1);
        thread1.setName("Thread-1");

        Runnable task2 = new NumberPrinterRunnableImpl(numberPrinter);
        Thread thread2 = new Thread(task2);
        thread1.setName("Thread-2");

        Runnable task3 = new NumberPrinterRunnableImpl(numberPrinter);
        Thread thread3 = new Thread(task3);
        thread1.setName("Thread-3");

        try{
            thread1.start();//thread1 is calling run method
            thread2.start();//thread2 is calling run method
            thread3.start();//thread3 is calling run method
        }catch (RuntimeException e){
            System.out.println("RuntimeTime exception occurred.");
            e.printStackTrace();
        }
        System.out.println("main method end : "+ Thread.currentThread());
    }
}
