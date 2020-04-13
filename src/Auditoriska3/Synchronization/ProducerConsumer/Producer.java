package Auditoriska3.Synchronization.ProducerConsumer;



import java.util.concurrent.Semaphore;

import static Auditoriska3.Synchronization.ProducerConsumer.ProdConsTester.NUM_RUNS;

public class Producer extends Thread {

    MyState myState;

    public Producer(MyState myState){
        this.myState = myState;
    }

    @Override
    public void run() {
        for(int i = 0;i < NUM_RUNS; i++){
            try {
                execute();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void execute() throws InterruptedException {
        Locks.bufferEmpty.acquire();

        Locks.bufferLock.acquire();
        myState.fillBuffer();

        for (Semaphore s: Locks.items){
            s.release();
        }
        Locks.bufferLock.release();

    }
}
