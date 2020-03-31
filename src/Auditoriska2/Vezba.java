package Auditoriska2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ThreadExecutor extends Thread{

    private String name;

    private Incrementor incrementor;

    public ThreadExecutor(String name, Incrementor incrementor ){
        this.name = name;
        this.incrementor=incrementor;
    }

    @Override
    public void run() {

        for (int i = 0; i<20;i++){
              incrementor.increment();

        }
    }

}

class Incrementor{ // sekoj objekt vo java ima monitor

    private int count = 0;
   // private Lock lock = new ReentrantLock();
    //    private Semaphore semaphore = new Semaphore(1);

    public void increment() {
        synchronized (this) { //sinhronizacija ovozmozuva na thread-vite da vlezat vo increment, no ne i da se izvrshi dokolku nekoj drug ja ima dozvolata
//            lock.lock();  //thread-ot dobiva dozvola i ne dozvoluva na drugite thread-ovi da vlezat
            count++;
//            lock.unlock();
//        }

            //read count -> T1(vremenski interval), T2(0)
            //add 1 to count -> T2(1), T1(1)
            //write count -> T1(1), T2(1) / namesto count = 2, dobivame count = 1
//        try {
//            Thread.sleep(10);  //go blokira thread-ot za 10ms
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        }
    } //kraj na increment

    //    void safeIncrementWithSemaphore() throws InterruptedException {
//        semaphore.acquire(); //bara dozvola
//        count++;
//        semaphore.release();
//    }

    public int getCount(){
        synchronized (this) {
            return this.count;
        }
    }

}

//class Incrementor{ // sekoj objekt vo java ima monitor
//
//    private int count = 0;
//    private Lock lock = new ReentrantLock();
//
//    private Semaphore semaphore = new Semaphore(1);
//
//    void unsafeIncrement(){
//        count++;
//    }
//
//    void safeIncrementWithSemaphore() throws InterruptedException {
//        semaphore.acquire(); //bara dozvola
//        count++;
//        semaphore.release();
//    }
//
//    void safeSynchronizedIncrement(){
//        synchronized (this){
//            count++;
//        }
//    }
//
//    void safeSynchronizedLock(){
//        lock.lock();
//        count++;
//        lock.unlock();
//    }
//
//}


public class Vezba {
    public static void main(String[] args) throws InterruptedException {
       // for (int i =0; i<10;i++) {

            Incrementor incrementor = new Incrementor();  //ako koristime dva inkrementori nema potreba od sinhronizacija, bidejki se raboti za dve razlicni memorii
//            Incrementor incrementor1 = new Incrementor(); //dokolku promenlivata count i metodite vo klasata count se static koristat edna memorija

            Thread e = new ThreadExecutor("Thread 1", incrementor);
            Thread e1 = new ThreadExecutor("Thread 2", incrementor);

            e.start();
            e1.start();

            e.join(); // main se blokira se dur "e" ne zavrshi so rabota
            e1.join();// main se blokira se dur "e2" ne zavrshi so rabota

//            if(e.isAlive() && e2.isAlive()){
//                e.interrupt(); //posle cekanjeto proveruvame dali e ziv thread-ot i go prekinuvame
//                e2.interrupt();
//            }

            int count = incrementor.getCount();
            System.out.println("Count = " + count); //moze da ne se dobie tocen rezultat

      //  }
    }
}
