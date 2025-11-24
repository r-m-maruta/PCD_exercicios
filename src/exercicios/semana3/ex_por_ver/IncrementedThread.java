package exercicios.semana3.ex_por_ver;

public class IncrementedThread extends Thread {

    private int threadId;

    private Counter counter;

    /*public IncrementedThread(int threadId, Counter sharedCounter) {
        this.threadId = threadId;
        this.counter = sharedCounter;
    }*/

    public IncrementedThread(Counter sharedCounter) {
        this.counter = sharedCounter;
    }

    //metodo para correr o codigo!

    @Override //apenas documentação
    public void run() {
        for (int i = 0; i < 30000; i++) {
            counter.increment();
            //System.out.println("Thread " + threadId + "incrementou. Valor: " + counter.getValue());
            System.out.println("incrementou. Valor: " + counter.getValue());
        }
    }

}
