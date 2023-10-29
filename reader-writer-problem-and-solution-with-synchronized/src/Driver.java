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
  * the message written by writer is overwritten by itself and hence proper reader-writer
  * functioning is missing

  * If reader thread starts first then it acquires lock and goes into WAITING state
  * and Writer goes into BLOCKED state.

  * Also, there could be a scenario where Writer thread writes messages multiple times before
  * even Reader thread gets a chance to read a msg. So overwriting problem still exits.
 */
class Message {
    AtomicReference<String> msg;
    AtomicBoolean isEmptyMsg;

    public Message(AtomicReference<String> msg, AtomicBoolean isEmptyMsg) {
        this.msg = msg;
        this.isEmptyMsg = isEmptyMsg;
    }

    synchronized String readMessage(){
        while (isEmptyMsg.get()){
            System.out.println("Reader thread is in while loop");
            //reader thread wait
        }

        this.isEmptyMsg.set(true);
        return this.msg.get();
    }

    synchronized void writeMessage(String msg){
        //if msg is not empty
        while (!isEmptyMsg.get());

        System.out.println("Message written is : " + msg);
        this.msg = new AtomicReference<>(msg);
        this.isEmptyMsg.set(false);
    }
}

class Reader implements Runnable {

    Message message;

    public Reader(Message message) {
        this.message = message;
    }

    @Override
    public void run() {
        for (String msg = this.message.readMessage();
             !"Finished Writing!!!".equals(msg);
             msg = this.message.readMessage()){
            System.out.println("Message read by Reader: "+ msg);
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
            this.message.writeMessage(msg);
        }
        this.message.writeMessage("Finished Writing!!!");
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
