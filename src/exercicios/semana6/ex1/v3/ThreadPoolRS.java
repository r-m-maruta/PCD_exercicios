package exercicios.semana6.ex1.v3;

import java.util.ArrayList;

public class ThreadPoolRS {

    ArrayList<Runnable> tasks;


    public ThreadPoolRS(int numWorkers) {
        tasks = new ArrayList<Runnable>();
        for(int i = 0 ; i< numWorkers ; i++) {
            WorkerThread worker = new WorkerThread(this);
            worker.start();
        }

    }

    public synchronized void submit(Runnable taksToAdd) {
        tasks.add(taksToAdd);
        notifyAll();
    }

    public synchronized Runnable getTask() throws InterruptedException {
        while(tasks.isEmpty()) {
            wait();
        }
        return tasks.remove(0);
    }
}