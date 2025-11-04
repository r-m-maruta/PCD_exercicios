package pt.iscte.dcti.pcd.race;

import java.util.Observable;

public class Car extends Observable implements Runnable {
    private int id;
    private int limit;
    private int position = 0;
    private static Integer winnerId = null; // ✅ Static para partilhar entre todos os carros
    private static final Object lock = new Object(); // ✅ Para sincronização

    public int getId() {
        return id;
    }

    public int getPosition() {
        return position;
    }

    public Car(int id, int limit) {
        super();
        this.id = id;
        this.limit = limit;
    }

    public static Integer getWinner() {
        synchronized (lock) {
            return winnerId;
        }
    }

    @Override
    public void run() {
        while (position < limit) {
            position++;

            setChanged();
            notifyObservers();

            try {
                Thread.sleep((long) (Math.random() * 200));
            } catch (InterruptedException e) {
                break;
            }
        }

        // ✅ DETECTAR VENCEDOR (parte crítica - precisa sincronização)
        synchronized (lock) {
            if (winnerId == null) { // Se ainda não há vencedor
                winnerId = this.id; // Eu sou o vencedor!
                System.out.println("🏆 CARRO VENCEDOR: nº " + winnerId + " 🏆");
            }
        }

        System.out.println("Carro " + id + " CHEGOU!");
    }
}