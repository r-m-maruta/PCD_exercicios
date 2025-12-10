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
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String username;

    private DefaultListModel<String> usersModel = new DefaultListModel<>();
    private JList<String> usersList = new JList<>(usersModel);

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

        JButton clearSelection = new JButton("Todos");
        clearSelection.addActionListener(e -> usersList.clearSelection());
        JScrollPane listPane = new JScrollPane(usersList);
        listPane.setPreferredSize(new Dimension(100, 300));
        listPane.setColumnHeaderView(clearSelection);
        add(listPane, BorderLayout.EAST);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public void connect(String host, int port) throws IOException {
        socket = new Socket(host, port);
        out = new ObjectOutputStream(socket.getOutputStream());
        out.flush();
        in = new ObjectInputStream(socket.getInputStream());

        // envia o username ao servidor
        out.writeObject(new Message("LOGIN", username, null, null));
        out.flush();

        // Thread para ler mensagens do servidor
        new Thread(() -> {
            try {
                Message message;
                while ((message = (Message) in.readObject()) != null) {
                    if (message.getType().equals("USERS")) {
                        updateUserList(message.getMessage());
                    } else {
                        textArea.append(message.getMessage() + "\n");
                    }
                }
            } catch (Exception igonored) {}
        }).start();
    }

    private void sendMessage() {
        String msg = textField.getText().trim();
        if (msg.isEmpty()) return;

        String receiver = usersList.getSelectedValue();
        try {
            if (receiver == null) {
                out.writeObject(new Message("PUBLIC", username, null, msg));
                out.flush();
            } else {
                out.writeObject(new Message("PRIVATE", username, receiver, msg));
                out.flush();
                textArea.append("(Mensagem privada para " + receiver + "): " + msg + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        textField.setText("");
    }

    private void updateUserList(String users) {
        usersModel.clear();
        String[] arr = users.split(",");
        for (String name : arr) {
            if (!name.equals(username))
                usersModel.addElement(name);
        }
    }


    public static void main(String[] args) throws IOException {
        String username = JOptionPane.showInputDialog("Nome do utilizador:");
        ClientChat client = new ClientChat(username);
        client.connect("localhost", 9090);
    }
}
