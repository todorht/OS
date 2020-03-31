package Lab2;

class Sum {

    void sum(int... vals){
        int total = 0;
        for(int i = 0; i < vals.length; i++){
            total += vals[i];
        }
        System.out.println(total);
    }

}

class ThreadAB implements Runnable{

    private Sum sum;
    private int[] list;

    public ThreadAB(Sum sum, int[] list) {
        this.sum = sum;
        this.list = list;
    }

    @Override
    public void run() {
        sum.sum(list);
    }
}


public class Sum1 {
    public static void main(String[] args) {
        int list[] = new int[]{1,1,1,1,1,1,1,1,1,1};
        int list1[] = new int[]{2,2,2,2,2,2,2,2,2,2};
        Sum sum = new Sum();

        Runnable thread = new ThreadAB(sum, list);
        Runnable thread1 = new ThreadAB(sum,list1);

        Thread e = new Thread(thread);
        Thread e1 = new Thread(thread1);
        e.start();
        e1.start();

    }
}
