package exercicios.semana3.ex_por_ver;

import java.time.Duration;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {

        Counter sharedCounter = new Counter();
        //Counter sharedCounter2 = new Counter();

        IncrementedThread t1 = new IncrementedThread(sharedCounter);
        IncrementedThread t2 = new IncrementedThread(sharedCounter);
        IncrementedThread t3 = new IncrementedThread(sharedCounter);

        Instant start = Instant.now();

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
            return;

        }

        Instant end = Instant.now();

        long miliseconds = Duration.between(start, end).toMillis();

        System.out.println("Valor final do contador: " + sharedCounter.getValue() + " e demorei: " +  miliseconds + " ms");
        //System.out.println("Valor final do contador2: " + sharedCounter2.getValue());
    }
}
