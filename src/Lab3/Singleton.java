package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Singleton {

    static Semaphore killer = new Semaphore(1);

    private static volatile Singleton singleton;
    private static int count;
//    private int count;

    private Singleton() {

    }

    public static Singleton getInstance() throws InterruptedException {
        // TODO: 3/29/20 Synchronize this

        killer.acquire();
        if(count == 0) {
            singleton = new Singleton();
            count++;
        }
        killer.release();
        return singleton;
    }

    public static void main(String[] args) {
        // TODO: 3/29/20 Simulate the scenario when multiple threads call the method getInstance

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i <100;i++){
            Thread t = new Thread(){
                @Override
                public void run() {
                    try {
                        System.out.println(getInstance());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            threads.add(t);
        }

        for (Thread t:threads){
            t.start();
        }
    }

}



