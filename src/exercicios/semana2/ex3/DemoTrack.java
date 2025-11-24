package exercicios.semana2.ex3;

import javax.swing.*;

public class DemoTrack {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Demo Track");

        Track track = new Track(3, 100);
        Car c0 = new Car(0, 100);
        Car c1 = new Car(1, 100);
        Car c2 = new Car(2, 100);

        c0.addObserver(track);
        c1.addObserver(track);
        c2.addObserver(track);

        frame.add(track);
        frame.setSize(500, 300);
        frame.setVisible(true);

        // ✅ Criar e iniciar threads
        Thread thread0 = new Thread(c0);
        Thread thread1 = new Thread(c1);
        Thread thread2 = new Thread(c2);

        thread0.start();
        thread1.start();
        thread2.start();

        System.out.println("Todos os carros iniciados!");

        // ✅ Thread para verificar o vencedor e mostrar JOptionPane
        Thread winnerChecker = new Thread(() -> {
            try {
                // Esperar que todas as threads terminem
                thread0.join();
                thread1.join();
                thread2.join();

                // ✅ Mostrar vencedor
                Integer winner = Car.getWinner();
                if (winner != null) {
                    // Opção 1: JOptionPane
                    JOptionPane.showMessageDialog(frame,
                            "🏁 CARRO VENCEDOR: nº " + winner + "! 🏁",
                            "FIM DA CORRIDA",
                            JOptionPane.INFORMATION_MESSAGE);

                    // Opção 2: Console também
                    System.out.println("🎉 VENCEDOR FINAL: Carro nº " + winner);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        winnerChecker.start();
    }
}