public class ThreadExampleUsingLambdaExpression {
    public static void main(String[] args) {
        Runnable lambdaTask = () -> {
            System.out.println("Say hello from " + Thread.currentThread().getName() + " and state is : "+ Thread.currentThread().getState());
        };
        /*
        () -> System.out.println("Say hello");
        this is run() method implementation in the form of lambda expression
        this constructs an object and returns it.
        The reference for the object is 'lambdaTask'
         */
        Thread thread = new Thread(lambdaTask);
        thread.start();
    }
}
