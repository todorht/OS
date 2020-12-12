package Auditoriska3.Synchronization.ProducerConsumer;

public class MyState {


    int items;
    int numOfConsumers;

    public MyState(int numOfConsumers){
        this.items = 0;
        this.numOfConsumers = numOfConsumers;
    }

    public boolean isBufferEmpty(){
        return this.items == 0;
    }

    public int getNumOfConsumers(){
        return numOfConsumers;
    }

    public void fillBuffer(){
        if(this.items != 0){
            System.err.println("GRESHKA");
            throw new RuntimeException("GRESHKA");
        }
        this.items = numOfConsumers;
        System.out.println("FillBuffer");
    }

    public void decrementBuffer(){
        this.items--;
        if(this.items < 0){
            System.err.println("GRESHKA");
            throw new RuntimeException("GRESHKA");
        }
        System.out.println("Decrement: " + items);
    }

    public void getItemById(int id){
        System.out.println(String.format("Get item by id %d", id));
    }
}
