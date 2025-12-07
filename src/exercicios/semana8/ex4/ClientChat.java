package exercicios.semana8.ex4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ClientChat extends JFrame {
    private JTextField textField;
    private JTextArea textArea;
    private JButton sendButton;

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientChat(String username) {
        super("Chat - " + username);
        this.username = username;

        textField = new JTextField();
        sendButton = new JButton("Enviar");
        textArea = new JTextArea(20, 50);
        textArea.setEditable(false);

        JPanel top = new JPanel(new BorderLayout());
        top.add(textField, BorderLayout.CENTER);
        top.add(sendButton, BorderLayout.EAST);

        add(top, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        sendButton.addActionListener(e -> sendMessage());
        textField.addActionListener(e -> sendMessage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        // envia o username ao servidor
        out.println(username);

        // Thread para ler mensagens do servidor
        new Thread(() -> {
            String line;
            try {
                while ((line = in.readLine()) != null) {
                    textArea.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendMessage() {
        String msg = textField.getText().trim();
        if (!msg.isEmpty()) {
            out.println(msg);
            textField.setText("");
        }
    }

    public static void main(String[] args) throws IOException {
        String username = JOptionPane.showInputDialog("Nome do utilizador:");
        ClientChat client = new ClientChat(username);
        client.connect("localhost", 9090);
    }
}
