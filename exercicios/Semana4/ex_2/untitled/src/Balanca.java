import javax.swing.*;
import java.util.concurrent.locks.ReentrantLock;

public class Balanca {

    public double peso;
    private final JTextField campo;


    public Balanca(JTextField campo) {
        this.campo = campo;
    }

    public synchronized void adicionar(double quantidade) throws InterruptedException {
        while(peso >= 12.5){
            System.out.println(Thread.currentThread() + ": antes de wait (balança cheia)");
            wait();
            System.out.println(Thread.currentThread() + ": depois de wait (balança vazia)");
        }
        peso += quantidade;
        atualizarCampo();
        System.out.printf("%s - Adicionou %.2f kg. Total = %.2f kg%n",
                Thread.currentThread(), quantidade, peso);
        notifyAll();
    }

    public synchronized double remover() throws InterruptedException {
        while(peso < 12.5){
            System.out.println(Thread.currentThread() + ": antes de wait (balança insuficiente)");
            wait();
            System.out.println(Thread.currentThread() + ": antes de wait (balança suficiente)");
        }
        peso -= 12.5;
        atualizarCampo();
        notifyAll();
        return 12.5;
    }


    private void atualizarCampo() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            campo.setText(String.format("%.2f kg", peso));
        });
    }
}
