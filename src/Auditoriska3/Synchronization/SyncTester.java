package Auditoriska3.Synchronization;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SyncTester {
    public static void main(String[] args) throws InterruptedException {

        List<ExampleThread> threads = new ArrayList<>();
        int defaultValue = 0;
        IntegerWrapper wrapper = new IntegerWrapper();

        for (int i = 0; i < 100; i++) {
            ExampleThread exampleThread = new ExampleThread(defaultValue, wrapper);
            threads.add(exampleThread);
        }

        for (ExampleThread t : threads) {
            t.start();
        }

        for (ExampleThread t : threads) { //main thread pristapuva do publicField
            //t.safeIncrementThreadPublicField();
            t.safeIncrementIntegerWrapper();
        }

        for (ExampleThread t: threads){
            t.join();
        }

        System.out.println(wrapper.getValue());
    }
}

    class IntegerWrapper {
        private int value;

        public void increment() {
            this.value++;
        }

        public int getValue() {
            return value;
        }
    }

    class ExampleThread extends Thread {
        private int threadPrivateField;

        // ako se static nemame paralelizacija, sekoj thread posebno, za static promenivi i instanci
        Lock lockThreadPublicField;
        Semaphore semaphoreThreadPublicField;
        Object mutex;
        public int threadPublicField;

        public static int threadStaticField;

        Semaphore semaphoreIntegerWrapperLocal;
        static Semaphore semaphoreIntegerWrapperGlobal = new Semaphore(1);//bidejki e static(globalen) moze da go inicaijalizirame tuka, ne vo konstruktor
        private IntegerWrapper wrapper;

        public ExampleThread(int init, IntegerWrapper wrapper) {
            this.threadPrivateField = init;
            this.wrapper = wrapper;

            this.lockThreadPublicField = new ReentrantLock();
            this.semaphoreThreadPublicField = new Semaphore(1);
            this.mutex = new Object();

            this.semaphoreIntegerWrapperLocal = new Semaphore(1);
        }

        public void forceSwitch(int milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void incrementThreadPrivateField() {
            this.threadPrivateField++;
            int localVar = this.threadPrivateField;
            this.forceSwitch(30);
            if (localVar != this.threadPrivateField) {
//         System.err.println(String.format("private-mismatch-%d",getId()));
            } else {
           System.out.println(String.format("private-%d, value: %d", getId(), this.threadPrivateField));
            }
        }

        public void safeIncrementThreadPublicField() throws InterruptedException {

//        synchronized (mutex) { // ili this mesto mutex
//            this.incrementThreadPublicField();
//        }

//        lockThreadPublicField.lock();
//        this.incrementThreadPublicField();
//        lockThreadPublicField.unlock();

            try {
                semaphoreThreadPublicField.acquire();
                this.incrementThreadPublicField();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphoreThreadPublicField.release();
            }
        }


        public void incrementThreadPublicField() {
            this.threadPublicField++;
            int localVar = this.threadPublicField;
            this.forceSwitch(30);
            if (localVar != this.threadPublicField) {
                System.err.println(String.format("public-mismatch-%d", getId()));
            } else {
           System.out.println(String.format("public-%d, value: %d", getId(), this.threadPublicField));
            }
        }

        public void incrementThreadStaticField() {
            threadStaticField++;
            int localVar = threadStaticField;
            forceSwitch(30);
            if (localVar != threadStaticField) {
//            System.err.println(String.format("static-mismatch-%d",getId()));
            } else {
//           System.out.println(String.format("static-%d, value: %d", getId(), threadStaticField));
            }
        }

        public void safeIncrementIntegerWrapper() throws InterruptedException {

//       synchronized (ExampleThread.class){ //isto kako static lock, vazi globalno za site instanci - eksluziven pristap
//            this.incrementIntegerWrapper();
//        }

//        try {
//            this.semaphoreIntegerWrapperLocal.acquire();
//            this.incrementIntegerWrapper();
//        } catch (InterruptedException e){
//            e.printStackTrace();
//        } finally {
//            this.semaphoreIntegerWrapperLocal.release();
//        }
            try {
                semaphoreIntegerWrapperGlobal.acquire();
                if (wrapper.getValue() < 5) {
                this.incrementIntegerWrapper();
                }
            } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphoreIntegerWrapperGlobal.release();
                }

        }


        public void incrementIntegerWrapper() {
            this.wrapper.increment();
            int localVar = this.wrapper.getValue();
            this.forceSwitch(30);
            if (localVar != this.wrapper.getValue()) {
                System.err.println(String.format("wrapper-mismatch-%d", getId()));
            } else {
                System.out.println(String.format("wrapper-%d, value: %d", getId(), this.wrapper.getValue()));
            }
        }

        @Override
        public void run() {
        this.incrementThreadPrivateField();
//
//        try {
//            this.safeIncrementThreadPublicField();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

            try {
                this.safeIncrementIntegerWrapper();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//        this.incrementThreadStaticField();
        }
    }

