package exercicios.semana1.ex1;

import javax.swing.*;

// Para testar sem argumentos de linha de comando
public class TestImageViewer {
    public static void main(String[] args) {
        // Testar com pasta específica
        String testPath = "/home/rmpgm/Downloads/Semana1/ex1/images"; // Ajuste o caminho conforme necessário

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageViewer(testPath).open();
            }
        });
    }
}