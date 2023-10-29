/**
 * A CPU can be dual or quad-core and each can run one thread.
 * Each core has its own cache and this cache is used by the thread that is running
 * in this core.
 * core1 --> Thread1 [core1 cache1]  <-- [Heap(Main Memory)]
 * core2 --> Thread2 [core2 cache2]  <-- [Heap(Main Memory)]
 * Each thread reads message object from heap and stores them into their cache.


 * Problem is local cache, and each thread reads value from cache.
 * One scenario could be, Reader thread reads isEmptyMsg as true initially
 * from the heap and then stores it in local cache and goes into infinite loop,
 * then Writer thread reads the value from the heap as true and stores the value
 * as true in local cache then it writes a new msg and makes isEmptyMsg as false
 * in its local cache and hence goes into infinite loop.
 */
class Message {
    String msg;
    boolean isEmptyMsg;

    public Message(String msg, boolean isEmptyMsg) {
        this.msg = msg;
        this.isEmptyMsg = isEmptyMsg;
    }

    String readMessage(){
        while (this.isEmptyMsg);

        this.isEmptyMsg = true;
        return this.msg;//return message read by reader
    }

    void writeMessage(String msg){
        //if msg is not empty then wait
        while (!isEmptyMsg);

        System.out.println("Message written is : " + msg);
        this.msg = msg;
        this.isEmptyMsg = false;
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
    public static void main(String[] args) {
        Message message = new Message("", true);

        Thread readerThread = new Thread(new Reader(message));
        Thread writerThread = new Thread(new Writer(message));

        readerThread.start();
        writerThread.start();
    }
}
