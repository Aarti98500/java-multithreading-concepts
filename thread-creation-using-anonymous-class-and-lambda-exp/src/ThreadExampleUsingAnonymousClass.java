public class ThreadExampleUsingAnonymousClass {
    public static void main(String[] args) {
        Runnable lambdaTask = new Runnable() {
            @Override
            public void run() {
                System.out.println("Say hello from " + Thread.currentThread().getName() + " and state is : "+ Thread.currentThread().getState());
            }
        };
        Thread thread = new Thread(lambdaTask);
        thread.start();
    }
}
