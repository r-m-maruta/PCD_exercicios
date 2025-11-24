package exercicios.semana1.ex1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class FrameTest {
    private JFrame frame;

    public FrameTest() {
        frame = new JFrame("Test");

        // para que o botao de fechar a janela termine a aplicacao
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        frame.setLocation (dimension.width/2 - 200 , dimension.height/2 - 200);

        addFrameContent();

        // para que a janela se redimensione de forma a ter todo o seu conteudo visivel
        frame.pack();
    }

    public void open() {
        // para abrir a janela (torna-la visivel)
        frame.setVisible(true);
    }

    private void addFrameContent() {
		/* para organizar o conteudo em grelha (linhas x colunas)
		se um dos valores for zero, o numero de linhas ou colunas (respetivamente) fica indefinido,
		e estas sao acrescentadas automaticamente */
        frame.setLayout(new GridLayout(4,2));
        //frame.setLayout(new FlowLayout());
        JLabel titleLabel = new JLabel("Title");
        frame.add(titleLabel);
        JTextField Titletext = new JTextField("Title");
        frame.add(Titletext);

        JLabel widthLabel = new JLabel("Width");
        frame.add(widthLabel);
        JTextField widthtext = new JTextField("200");
        frame.add(widthtext);

        JLabel heigthLabel = new JLabel("Heigth");
        frame.add(heigthLabel);
        JTextField heigthtext = new JTextField("200");
        frame.add(heigthtext);


        JCheckBox check = new JCheckBox("Center");
        frame.add(check);

        JButton button = new JButton("button");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String TitleToSet = Titletext.getText();
                System.out.println(TitleToSet);
                frame.setTitle(TitleToSet);
                int widthToSet = 200;
                int heigthToSet =200;
                try {
                    widthToSet = Integer.parseInt(widthtext.getText());
                    heigthToSet = Integer.parseInt(heigthtext.getText());
                } catch (Exception e2) {
                    System.out.println("A altura e comprimento definidos nao sao validos ") ;
                }
                frame.setSize(widthToSet, heigthToSet);
                System.out.println("A largura da janela a definir é " + widthToSet + " e a altura " + heigthToSet);

                if(check.isSelected()) {
                    Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

                    int x = (dimension.width-widthToSet)/2;
                    int y = (dimension.height-heigthToSet)/2;
                    frame.setLocation(x, y);
                    System.out.println("Janela centrada a posição: ("+x+","+y+")");
                }

            }
        });
        frame.add(button);
    }

    public static void main(String[] args) {
        FrameTest window = new FrameTest();
        window.open();
    }
}
