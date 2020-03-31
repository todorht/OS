package Lab2;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class BlockingQueue1 {

    public static class BlockingQueue<T> {
        T[] contents;
        int capacity;

        public Semaphore semaphore = new Semaphore(1);

        public BlockingQueue(int capacity){
            contents = (T[]) new Object[capacity];
            this.capacity = capacity;
        }

        public void enqueue(T item) throws InterruptedException {
           semaphore.acquire();

           semaphore.release();
        }

        public T dequeue() throws InterruptedException {
           return contents[capacity-1];
        }
    }

    static class ThreadB extends Thread {

        private BlockingQueue<Integer> tBlockingQueue;
        private Integer item;

        public ThreadB(BlockingQueue tBlockingQueue, Integer item) {
          this.tBlockingQueue = tBlockingQueue;
          this.item = item;
        }

        @Override
        public void run() {
            try {
                tBlockingQueue.enqueue(item);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                tBlockingQueue.dequeue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Vnesi broj: ");
        int num = scan.nextInt();

        BlockingQueue<Integer> bq = new BlockingQueue<Integer>(num);

        HashSet<Thread> threads = new HashSet<Thread>();

        for(int i= 0;i < 5; i++){
            ThreadB t = new ThreadB(bq, i);
            threads.add(t);
        }

        for (Thread t : threads){
            t.start();
        }



    }
}

