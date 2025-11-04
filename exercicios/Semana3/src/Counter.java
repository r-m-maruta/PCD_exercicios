import java.util.concurrent.atomic.AtomicInteger;

public class Counter extends Thread{

    //private int value = 0; //antes de nascer tem o valor a zero
    private int value;

    private AtomicInteger counter;

    //diferença entre programar com objetos (quando nascem têm o valor a zero)
    /*public Counter(){
        value = 0;
    }*/

    /*public synchronized void increment(){
        counter++;
    }*/

    /*    public int getValue() {
        return value;
    }*/

    public Counter(){
        counter = new AtomicInteger(0);
    }

    public void increment(){
        counter.incrementAndGet();
    }

    public int getValue() {
        return counter.get();
    }


}