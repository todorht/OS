package Lab3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.Semaphore;
import java.util.spi.CurrencyNameProvider;

public class BarberShop {

    int waitingCustomers = 0;
    static Semaphore customer = new Semaphore(5);
    static Semaphore berber = new Semaphore(0);
    static Semaphore izvestuva = new Semaphore(0);
    static Semaphore cw = new Semaphore(1);

    void customerComesIn() throws InterruptedException {
        // TODO: 3/29/20 Synchronize this method, invoked by a Customer thread
        customer.acquire();
        cw.acquire();
        waitingCustomers++;
        cw.release();
        System.out.println("VLAGA");
        izvestuva.release();
        berber.acquire();




    }

    void barber() throws InterruptedException {
        // TODO: 3/29/20 Synchronize this method, invoked by Barber thread

        izvestuva.acquire();
        berber.release();
        cw.acquire();
        waitingCustomers--;
        cw.release();
        System.out.println("Shisha");
        customer.release();



    }

    public static void main(String[] args) {
        // TODO: 3/29/20 Synchronize the scenario
        BarberShop barberShop = new BarberShop();

        List<Thread> threads = new ArrayList<>();
        Thread barber = new Barber(barberShop);

        for (int i =0; i < 20; i++){
            Thread t = new Customer(barberShop);
            threads.add(t);
        }

        for (Thread t: threads){
            t.start();
        }

        barber.start();


    }
}

class Customer extends Thread{
    BarberShop barberShop;

    public Customer(BarberShop barberShop){
        this.barberShop = barberShop;
    }

    @Override
    public void run() {
        try {
                barberShop.customerComesIn();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Barber extends Thread{
    BarberShop barberShop;

    public Barber(BarberShop barberShop){
        this.barberShop = barberShop;
    }

    @Override
    public void run() {
        try {
           BarberShop.cw.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (barberShop.waitingCustomers!=0){
           BarberShop.cw.release();
            //System.out.println(barberShop.waitingCustomers);
            try {
                barberShop.barber();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                BarberShop.cw.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}