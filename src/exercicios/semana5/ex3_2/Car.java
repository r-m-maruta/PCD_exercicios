package exercicios.semana5.ex3_2;

import java.util.Observable;
import java.util.concurrent.Semaphore;


public class Car extends Observable implements Runnable {
	public static final int MAX_UPDATE_INTERVAL=100;
	private int id;
	private int limit;
	private int position=0;
    private Semaphore semaphore;

	public int getId() {
		return id;
	}

	public int getPosition() {
		return position;
	}

	public Car(int id, int limit, Semaphore semaphore) {
		super();
		this.id = id;
		this.limit = limit;
        this.semaphore = semaphore;
	}

	@Override
	public void run() {
		while(position<limit){
			try {
                System.out.printf("🚗 Carro %d -> TENTAR entrar (permits disponíveis: %d)%n",
                        id, semaphore.availablePermits());

                semaphore.acquire();

                System.out.printf("✅ Carro %d -> ENTROU (permits disponíveis: %d)%n",
                        id, semaphore.availablePermits());

				Thread.sleep((long) (MAX_UPDATE_INTERVAL*Math.random()));
                position++;
                setChanged();
                notifyObservers();
			} catch (InterruptedException e) {

                System.out.printf("⛔ Carro %d -> INTERROMPIDO!%n", id);

                return;
			} finally {
                semaphore.release();
                System.out.printf("🏁 Carro %d -> SAIU (permits disponíveis: %d)%n",
                        id, semaphore.availablePermits());
            }

		}
		
	}
	
	
}
