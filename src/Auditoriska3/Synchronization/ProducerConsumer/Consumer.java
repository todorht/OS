package Auditoriska3.Synchronization.ProducerConsumer;

import static Auditoriska3.Synchronization.ProducerConsumer.ProdConsTester.*;

public class Consumer extends Thread {

    MyState myState;
    int id;

    public Consumer(MyState myState, int id){
        this.myState = myState;
        this.id = id;
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
        consumerSem[id].acquire();
        myState.getItemById(id);
        prodBuffer.acquire();
        myState.decrementBuffer();
        if(myState.isBufferEmpty()){
            empBuffer.release();
        }
        prodBuffer.release();

    }
}
