
//public class SafeSequence {
//    private int value;
//
//    public synchronized int getNext(){
//        return value++;
//    }
//}

public class SafeSequence{
    public static void main(String[] args) {

    }
    private int value;

    public int getNext(){
        synchronized (this){
            return value++;
        }
    }

    static class T extends Thread

}