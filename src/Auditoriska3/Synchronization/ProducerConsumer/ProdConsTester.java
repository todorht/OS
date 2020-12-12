package Auditoriska3.Synchronization.ProducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ProdConsTester  {

    public static Semaphore prodBuffer;
    public static Semaphore consumerSem[];
    public static Semaphore empBuffer;

    public static final int NUM_RUNS = 30;


    public static void main(String[] args) throws InterruptedException {

        int numberOfConsumer = 120;
        MyState myState = new MyState(numberOfConsumer);
        init(numberOfConsumer);
        List<Thread> threads = new ArrayList<>();

        for(int i = 0; i < numberOfConsumer; i++){
            Consumer consumer = new Consumer(myState, i);
            threads.add(consumer);
        }

        Producer producer = new Producer(myState);
        producer.start();

        for(Thread t: threads){
            t.start();
        }

        for (Thread t: threads){
                t.join();
        }

        System.out.println("Uspeshna sinhronizacija");
    }

    public static void init(int numCons){
        prodBuffer = new Semaphore(1);
        empBuffer = new Semaphore(1);
        consumerSem = new Semaphore[numCons];
        for(int i = 0; i <numCons;i++){
            consumerSem[i] = new Semaphore(0);
        }
    }

}
