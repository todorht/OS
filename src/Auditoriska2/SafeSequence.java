//public class SafeSequence {
//    private int value;
//
//    public synchronized int getNext(){
//        return value++;
//    }
//}
//
//public class SafeSequence{
//    private int value;
//
//    public int getNext(){
//        synchronized (this){
//            return value++;
//        }
//    }
//}