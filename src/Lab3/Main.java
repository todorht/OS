package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

class H2OMachine {

    String[] molecule;
    int count;

    public H2OMachine() {
        molecule = new String[3];
        count = 0;
    }

    public void hydrogen() throws InterruptedException {
        // TODO: 3/29/20 synchronized logic here
        Main.h.acquire();
//        System.out.println("H vleguva");
        Main.hHere.release(1);
//        System.out.println("H osloboduva");
        Main.oHere.acquire();
//        System.out.println("O Stiga");
        Main.ready.acquire();
        System.out.println("The molecule H is formed");
        Main.h.release();
    }


    public void oxygen() throws InterruptedException {
        // TODO: 3/29/20 synchronized logic here
        Main.o.acquire();
//        System.out.println("o vleguva");
        Main.hHere.acquire(2);
//        System.out.println("H Stiga");
        Main.oHere.release(2);
//        System.out.println("O osloboduva");
        Main.ready.release(2);
        System.out.println("The molecule O is formed");
        Main.o.release();


    }
}
class H2OThread extends Thread {

    H2OMachine molecule;
    String atom;

    public H2OThread(H2OMachine molecule, String atom){
        this.molecule = molecule;
        this.atom = atom;
    }

    public void run() {

            if ("H".equals(atom)) {
                try {
                    molecule.hydrogen();
                } catch (Exception e) {
                }
            } else if ("O".equals(atom)) {
                try {
                    molecule.oxygen();
                } catch (Exception e) {
                }
            }

    }

}

public class Main
{
    static Semaphore h = new Semaphore(2);
    static Semaphore hHere = new Semaphore(0);
    static Semaphore o = new Semaphore(1);
    static Semaphore oHere = new Semaphore(0);
    static Semaphore ready = new Semaphore(0);


    public static void main(String[] args) throws InterruptedException {

        // TODO: 3/29/20 Simulate with multiple scenarios
        H2OMachine molecule = new H2OMachine();

        List<H2OThread> threadsH = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            H2OThread t = new H2OThread(molecule, "H");
            threadsH.add(t);
        }

        for (int i = 0; i < 10; i++) {
            H2OThread t1 = new H2OThread(molecule, "O");
            threadsH.add(t1);
        }

        for (Thread t: threadsH){
            t.start();
        }

//        Thread.sleep(1000);
//
//        for (Thread t: threadsH){
//            t.join(1000);
//        }
//
//        for (Thread t: threadsH){
//           if(t.isAlive()){
//                t.interrupt();
//           }
//        }


    }
}