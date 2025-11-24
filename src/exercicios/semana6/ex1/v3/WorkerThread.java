package exercicios.semana6.ex1.v3;

public class WorkerThread extends Thread{

    private ThreadPoolRS threadPoolRS;

    public WorkerThread(ThreadPoolRS threadPoolRS) {
        this.threadPoolRS = threadPoolRS;
    }

    @Override
    public void run() {
        while(!interrupted()) {
            Runnable taskToRun;
            try {
                taskToRun = threadPoolRS.getTask();
            } catch (InterruptedException e) {
                System.out.println("Fui interrompido vou parar");
                return;
            }

            taskToRun.run();

        }
    }

}