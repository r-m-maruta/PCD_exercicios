import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//Este exercício é a solução para a Semana 5!
public class MesaWaitNotifyAll {
    private ArrayList<Javali> javalis;
    private int CAPACIDADE;
    private ReentrantLock lock;
    private Condition emptyTable;
    private Condition fullTable;

    public MesaWaitNotifyAll(int CAPACIDADE) {
        this.CAPACIDADE=CAPACIDADE;
        javalis=new ArrayList<Javali>();
        lock = new ReentrantLock();
        emptyTable = lock.newCondition();
        fullTable = lock.newCondition();
    }
    public  void coloca(Javali j) throws InterruptedException {
        lock.lock();
        try {
            while(javalis.size()==CAPACIDADE)
                fullTable.await();
            javalis.add(j);
            emptyTable.signalAll();
        }
        finally {
            lock.unlock();
        }
    }
    public  Javali retira() throws InterruptedException {
        lock.lock();
        try {
            while(javalis.isEmpty())
                emptyTable.await();
            fullTable.signalAll();
            return javalis.remove(0);
        }
        finally {
            lock.unlock();
        }

    }











    public class Cozinheiro extends Thread {
        public final int id;
        public Cozinheiro(int id) {	this.id = id;}
        public void run() {
            for(int i=0; i!=10;i++)
                try {
                    coloca(new Javali(i, id));
                    System.out.println("Coloquei:"+id+":"+i);
                } catch (InterruptedException e) {		}
        }
    }








    public class Glutao extends Thread {
        @Override
        public void run() {
            for(int i=0; i!=5;i++)
                try {
                    System.out.println("Consumi:"+retira());
                } catch (InterruptedException e) {}
        }
    }





    public static void main(String[] args) {
        Vector<Thread> threads=new Vector<>();
        MesaWaitNotifyAll m=new MesaWaitNotifyAll(10);
        for(int i=0; i!=10;i++) {
            threads.add(m.new Glutao());
            threads.lastElement().start();
        }
        for(int i=0; i!=5;i++) {
            threads.add(m.new Cozinheiro(i));
            threads.lastElement().start();
        }
    }
}
