package exercicios.semana2.ex2;

import javax.naming.Name;
import java.util.Random;

public class NameThread extends Thread {
    private int threadId;
    private Random random;

    public NameThread(int id) {
        this.threadId = id;
        this.random = new Random();
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i < 10; i++) {
                System.out.println("Thread: " + threadId + " - iteração: " + (i+1));

                int sleepTime = 500 +  random.nextInt(1000);
                Thread.sleep(sleepTime);

                System.out.println("Thread: " + threadId + " - Sleep: " + sleepTime + "ms");

            }

            System.out.println("Thread: " + threadId + " - TERMINOU!! ");

        } catch (InterruptedException e) {
            System.out.println("Thread " + threadId + " - INTERROMPIDA via exception!");

        } finally {
            System.out.println("Thread " + threadId + " - TERMINOU!");
        }
    }

    public static void main ( String args [] ) throws InterruptedException {

        NameThread t1 = new NameThread(1);
        NameThread t2 = new NameThread(2);

        t1.start();
        t2.start();

        System.out.println("Main vai dormir por 4 segundos)");
        Thread.sleep(4000);

        System.out.println("Main: A interromper Threads");
        t1.interrupt();
        t2.interrupt();

        try {
            t1.join();
            t2.join();
        } catch ( InterruptedException e ) {
            System.out.println("Thread principal foi interrompida!");
            Thread.currentThread().interrupt();
        }

        System.out.println ( " Programa principal TERMINOU!! " );
    }
}


