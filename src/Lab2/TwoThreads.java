package Lab2;

public class TwoThreads {

    public static void main(String[] args) {
        ThreadAB t1 = new ThreadAB("A","B");
        Thread tt1 = new Thread(t1);
        ThreadAB t2 = new ThreadAB("1", "2");
        Thread tt2 = new Thread(t2);

        tt1.start();
        tt2.start();
    }

    public static class ThreadAB implements Runnable{
        String a;
        String b;



        public ThreadAB(String a, String b) {
            this.a = a;
            this.b = b;

        }

        @Override
        public void run() {
            System.out.println(a);
            System.out.println(b);
        }
    }
}





