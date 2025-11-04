import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
//Este exercício é a solução para a Semana 5!
public class MesaWaitNotifyAll {
	private ArrayList<Javali> javalis=new ArrayList<>();
	public static final int CAPACIDADE=10;
	public synchronized void coloca(Javali j) throws InterruptedException {
			while(javalis.size()==CAPACIDADE)
				wait();
			javalis.add(j);
			notifyAll();
			}
	public synchronized Javali retira() throws InterruptedException {
			while(javalis.isEmpty())
				wait();
			notifyAll();
			return javalis.remove(0);
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
		MesaWaitNotifyAll m=new MesaWaitNotifyAll();
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
