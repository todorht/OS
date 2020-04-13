package Auditoriska3.Synchronization.ProducerConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ProdConsTester  {

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
        threads.add(producer);

        for(Thread t: threads){
            t.start();
        }

        for (Thread t: threads){
                t.join();
        }

        System.out.println("Uspeshna sinhronizacija");
    }

    public static void init(int numCons){
        Locks.bufferLock = new Semaphore(1);
        Locks.bufferEmpty = new Semaphore(1); // za Producer-ot
        Locks.items = new Semaphore[numCons];  //za sekoj item za Consumer-ot
        for (int i = 0; i < numCons; i++){
            Locks.items[i] = new Semaphore(0);
        }

    }

}
