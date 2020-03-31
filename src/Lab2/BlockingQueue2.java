package Lab2;

import java.io.IOException;
import java.util.HashSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue2 {

    public static class BlockingQueue<T> {
        T[] contents;
        int capacity;
        int rear = 0;
        int front = 0;

        private Lock lock = new ReentrantLock();

        public BlockingQueue(int capacity){
            contents = (T[]) new Object[capacity];
            this.capacity = capacity;
        }

        public void enqueue(T item) {
            lock.lock();
            if(capacity==contents.length) contents[rear++]=item;
            if(rear==contents.length)capacity++;

            lock.unlock();
        }

        public T dequeue() {
            if (capacity > 0) {
                lock.lock();
                contents[front++] = null;
                if(front==contents.length) front=0;
                capacity--;
                lock.unlock();
            }

            return (T) contents;
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

