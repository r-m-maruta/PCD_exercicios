package exercicios.semana2.ex2;

import javax.swing.*;
import java.util.Random;

public class Horse extends Thread {
    private JTextField textField;
    private int movimentosRestantes;
    private String nome;
    private Random random;

    public Horse(String nome, JTextField textField) {
        this.textField = textField;
        this.nome = nome;
        this.movimentosRestantes = 30;
        this.random = new Random();
    }

    @Override
    public void run() {
        while (movimentosRestantes > 0) {
            movimentosRestantes--;

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    // ✅ FORMATO como na imagem: [28], [27], etc.
                    textField.setText("[" + movimentosRestantes + "]");
                }
            });

            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e){
                break;
            }
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                textField.setText("CHEGOU!");
            }
        });    }
}
