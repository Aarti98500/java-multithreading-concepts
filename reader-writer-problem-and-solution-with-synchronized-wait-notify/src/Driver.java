import com.sun.jdi.IntegerType;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;


/**
 * A CPU can be dual or quad-core and each can run one thread.
 * Each core has its own cache and this cache is used by the thread that is running
 * in this core.
 * core1 --> Thread1 [core1 cache1]  <-- [Heap(Main Memory)]
 * core2 --> Thread2 [core2 cache2]  <-- [Heap(Main Memory)]
 * Each thread reads message object from heap and stores them into their cache.

 * To solve the problem of local thread cache update issue, we can use
 * volatile keyword with variables that will enforce read/write directly to the
 * main memory and hence all the threads will have same values of volatile variables.

 * volatile vairable can be accessed/updated by many threads at the same time.

 * atomic internally usage volatile but it make sure that operation is atomic and
 * one thread is accessing/updating the atomic at a time

 * Atomic -> volatile + thread safety for the variable.

 * Now we are using atomic instead of volatile but the problem still exist where
 * the message written by writer is over written by itself and hence proper reader-writer
 * functioning is missing

 * If reader thread starts first then it acquires lock and goes into WAITING state
 * and Writer goes into BLOCKED state.
 */
class Message {
    AtomicReference<String> msg;
    AtomicBoolean isEmptyMsg;

    public Message(AtomicReference<String> msg, AtomicBoolean isEmptyMsg) {
        this.msg = msg;
        this.isEmptyMsg = isEmptyMsg;
    }

    synchronized String readMessage() throws InterruptedException {
        while (isEmptyMsg.get()){
            //reader thread wait
            this.wait();
        }

        this.isEmptyMsg.set(true);
        this.notifyAll();
        return this.msg.get();
    }

    synchronized void writeMessage(String msg) throws InterruptedException {
        //if msg is not empty
        while (!isEmptyMsg.get()){
            this.wait();
        }

        System.out.println("Message written is : " + msg);
        this.msg = new AtomicReference<>(msg);
        this.isEmptyMsg.set(false);
        this.notify();
    }
}

class Reader implements Runnable {

    Message message;

    public Reader(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        try{
            for (String msg = this.message.readMessage();
                 !"Finished Writing!!!".equals(msg);
                 msg = this.message.readMessage()){
                System.out.println("Message read by Reader: "+ msg);
            }
        }catch (InterruptedException runtimeException){
            throw new RuntimeException();
        }
    }
}

class Writer implements Runnable {
    Message message;
    public Writer(Message message) {
        this.message = message;
    }
    @Override
    public void run() {
        for (String msg : new String[]{"Hello", "how", "are", "you"}){
            try {
                this.message.writeMessage(msg);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            this.message.writeMessage("Finished Writing!!!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}



public class Driver {
    public static void main(String[] params) {
        Message message = new Message(new AtomicReference<>(""), new AtomicBoolean(true));

        Thread readerThread = new Thread(new Reader(message));
        Thread writerThread = new Thread(new Writer(message));

        readerThread.start();
        writerThread.start();
    }
}
