package exercicios.semana8.ex2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ClientBroadcast {

    private String nomeCliente;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;
    public static final int PORTO = 8080;

    public ClientBroadcast(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public static void main(String[] args) {
        new Thread(() -> new ClientBroadcast("Cliente1").runClient()).start();
        new Thread(() -> new ClientBroadcast("Cliente2").runClient()).start();
        new Thread(() -> new ClientBroadcast("Cliente3").runClient()).start();
    }

    public void runClient() {
        try {
            connectToServer();
            startReceiving();
            sendMessages();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {}
        }
    }

    void connectToServer() throws IOException {
        InetAddress endereco = InetAddress.getByName(null);
        System.out.println("Endereco:" + endereco);
        socket = new Socket(endereco, ServerBroadcast.PORTO);
        System.out.println("Socket:" + socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    void startReceiving() {
        new Thread(() -> {
            try {
                while (true) {
                    Message msg = (Message) in.readObject();
                    if (!msg.getText().equals("FIM")) {
                        if (!msg.getSender().equals(nomeCliente)) {
                            System.out.println( "Recebido de " + msg.getSender() + ": " + msg.getText());
                        } else {
                            System.out.println("Eu " + msg.getSender() + " enviei: " + msg.getText());
                        }
                    } else {
                        System.out.println("Conexão encerrada pelo servidor");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Conexão encerrada pelo servidor");
            }
        }).start();
    }

    void sendMessages() throws IOException {
        for (int i = 0; i < 4; i++) {
            out.writeObject(new Message("Ola " + i, nomeCliente));
            out.flush();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {}
        }
        out.writeObject(new Message("FIM", nomeCliente));
        out.flush();
    }
}
