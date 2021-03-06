package Lab2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue1 {

    public static class BlockingQueue<T> {
        T[] contents;
        int capacity;
        List<T> list = new ArrayList<>();

        private Lock lock = new ReentrantLock();

        public BlockingQueue(int capacity){
            contents = (T[]) new Object[capacity];
            this.capacity = capacity;
        }

        public void enqueue(T item) {
            lock.lock();
            if(list.size()<capacity){
                list.add(item);
            }
            lock.unlock();
        }

        public T dequeue() {

            T item = null;
            lock.lock();
            if(list.size()>0){
                item = list.remove(list.size()-1);
            }
            lock.unlock();
            return item;
        }
    }

    static class ThreadA extends Thread {

        private BlockingQueue<Integer> tBlockingQueue;

        public ThreadA(BlockingQueue tBlockingQueue) {
            this.tBlockingQueue = tBlockingQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i<10;i++) {
                tBlockingQueue.enqueue(i);
            }

        }
    }

    static class ThreadB extends Thread {

        private BlockingQueue<Integer> tBlockingQueue;

        public ThreadB(BlockingQueue tBlockingQueue) {
            this.tBlockingQueue = tBlockingQueue;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                tBlockingQueue.dequeue();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        BlockingQueue<Integer> bq = new BlockingQueue<Integer>(10);

        HashSet<Thread> threadsA = new HashSet<Thread>();
        HashSet<Thread> threadsB = new HashSet<Thread>();

        for(int i= 0;i < 2; i++){
            ThreadA ta = new ThreadA(bq);
            ThreadB tb = new ThreadB(bq);
            threadsA.add(ta);
            threadsB.add(tb);
        }

        for (Thread ta : threadsA){
            ta.start();
        }
        for (Thread tb : threadsB){
            tb.start();
        }

        for (Thread ta : threadsA){
            ta.join();
        }
        for (Thread tb : threadsB){
            tb.join();
        }

        System.out.println("Successfully executed");

    }
}


