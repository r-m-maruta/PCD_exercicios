import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI extends JFrame {

    private final JTextField campoPeso;
    private final JButton botaoStop;

    private final Escavadora escavadora1;
    //private final Escavadora escavadora2;
    private final Ourives ourives;

    public GUI() {
        super("Simulação De Ouro");

        campoPeso = new JTextField("0.00kg", 10);
        campoPeso.setEditable(false);
        botaoStop = new JButton("Stop");

        Balanca balanca = new Balanca(campoPeso);
        escavadora1 = new Escavadora(balanca);
        //escavadora2 = new Escavadora(balanca);
        ourives = new Ourives(balanca);

        setLayout(new FlowLayout());
        add(new JLabel("Peso:"));
        add(campoPeso);
        add(botaoStop);

        botaoStop.addActionListener(e -> {
           escavadora1.parar();
           //escavadora2.parar();
            ourives.parar();

           try{
               System.out.println(Thread.currentThread() + " - antes de join (escavadora).");
               escavadora1.join();
               //escavadora2.join();
               System.out.println(Thread.currentThread() + " - antes de join (ourives).");
               ourives.join();
           } catch (InterruptedException ex) {
               System.out.println(Thread.currentThread() + " - antes de tratar InterruptedException (GUI).");
           }
           dispose();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(250, 120);
        setVisible(true);

        escavadora1.start();
        //escavadora2.start();
        ourives.start();

    }


}
