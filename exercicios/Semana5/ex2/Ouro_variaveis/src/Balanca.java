import javax.swing.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Balanca {

    public double peso;
    private final JTextField campo;
    private ReentrantLock lock = new ReentrantLock();
    private Condition balancaCheia;
    private Condition balancaVazia;

    public Balanca(JTextField campo) {
        this.campo = campo;
        balancaCheia = lock.newCondition();
        balancaVazia = lock.newCondition();
    }

    public void adicionar(double quantidade) throws InterruptedException {
        lock.lock();
        try {
            while (peso >= 12.5) {
                System.out.println(Thread.currentThread() + ": antes de wait (balança cheia)");
                balancaVazia.await();
                System.out.println(Thread.currentThread() + ": depois de wait (balança vazia)");
            }
            peso += quantidade;
            balancaCheia.signalAll();
            atualizarCampo();
            System.out.printf("%s - Adicionou %.2f kg. Total = %.2f kg%n",
                    Thread.currentThread(), quantidade, peso);
        } finally {
            lock.unlock();
        }
    }

    public double remover() throws InterruptedException {
        lock.lock();
        try {
            while(peso < 12.5){
                System.out.println(Thread.currentThread() + ": antes de wait (balança insuficiente)");
                balancaCheia.await();
                System.out.println(Thread.currentThread() + ": antes de wait (balança suficiente)");
            }
            peso -= 12.5;
            balancaVazia.signalAll();
            atualizarCampo();

            return 12.5;

        } finally {
            lock.unlock();
        }
    }


    private void atualizarCampo() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            campo.setText(String.format("%.2f kg", peso));
        });
    }
}
