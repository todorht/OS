package Auditoriska2;

public class main2 {
    public static void main(String[] args) {
        Runnable obj = new T2();
        Thread tobj = new Thread((obj));
        tobj.start();
    }

}


class T2 implements Runnable{ //run() doagja od Runnable (interface)

    @Override
    public void run() {

    }
}

