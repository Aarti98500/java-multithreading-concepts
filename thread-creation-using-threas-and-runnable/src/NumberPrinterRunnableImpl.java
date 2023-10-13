//NumberPrinterThread will print numbers
//There can be one or more number printer threads printing numbers in sequence or parallel.
class NumberPrinterRunnableImpl implements Runnable {
    NumberPrinter numberPrinter;

    NumberPrinterRunnableImpl(NumberPrinter numberPrinter){
        this.numberPrinter = numberPrinter;
    }

    @Override
    public void run() {
        System.out.println("Inside run method " + Thread.currentThread().getName() + " called run() method!!!"+ "" +
                "Thread state is : "+ Thread.currentThread().getState());
        try {
            Thread.sleep(2000);//Thread goes to Timed_Waiting, thread is waiting for finite amount of time
            this.numberPrinter.printNumber();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Inside run method " + Thread.currentThread().getName() + " called run() method!!!"+ "" +
                "Thread state is : "+ Thread.currentThread().getState() + " completed!!!");
    }
}