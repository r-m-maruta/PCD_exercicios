package exercicios.semana2.ex2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HorseRace {
    private JTextField cavalo1Field, cavalo2Field, cavalo3Field;
    private JFrame frame;
    private JLabel titleLable;
    private JButton startButton;
    private Horse cavalo1, cavalo2, cavalo3;

    public HorseRace() {
        InitializeGUI();
    }

    private void InitializeGUI() {
        frame = new JFrame("Horse Race");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        titleLable = new JLabel("", SwingConstants.CENTER);
        titleLable.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(titleLable, BorderLayout.NORTH);

        JPanel horseMovements = new JPanel();
        horseMovements.setLayout(new GridLayout(1, 3, 10, 10));


        cavalo1Field = new JTextField();
        cavalo2Field = new JTextField();
        cavalo3Field = new JTextField();

        configureTextField(cavalo1Field);
        configureTextField(cavalo2Field);
        configureTextField(cavalo3Field);

        cavalo1Field.setText("[30]");
        cavalo2Field.setText("[30]");
        cavalo3Field.setText("[30]");

        horseMovements.add(cavalo1Field);
        horseMovements.add(cavalo2Field);
        horseMovements.add(cavalo3Field);

        frame.add(horseMovements, BorderLayout.CENTER);

        JPanel startButtonPanel = new JPanel();
        startButton = new JButton("Start");
        startButtonPanel.add(startButton);
        frame.add(startButtonPanel, BorderLayout.SOUTH);

        setupEventListeners();
        frame.setLocationRelativeTo(null);

    }

    private void configureTextField(JTextField textField) {
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.BOLD, 16));
        textField.setEditable(false);
        textField.setBackground(Color.white);
    }

    private void setupEventListeners(){
        startButton.addActionListener(e -> {
            cavalo1Field.setText("[30]");
            cavalo2Field.setText("[30]");
            cavalo3Field.setText("[30]");

            startButton.setEnabled(false);

            cavalo1 = new Horse("Cavalo 1", cavalo1Field);
            cavalo2 = new Horse("Cavalo 2", cavalo2Field);
            cavalo3 = new Horse("Cavalo 3", cavalo3Field);

            cavalo1.start();
            cavalo2.start();
            cavalo3.start();

            new Thread(() -> {
                try {
                    cavalo1.join();
                    cavalo2.join();
                    cavalo3.join();
                    SwingUtilities.invokeLater(() -> startButton.setEnabled(true));

                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }).start();
        });
    }

    public void open() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new HorseRace().open();
            }
        });
    }

}
