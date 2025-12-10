package exercicios.semana8.ex4;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String username;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Recebe o nome do cliente na primeira mensagem
            Message firstMessage = (Message) in.readObject();
            username = firstMessage.getSender();

            ServerChat.broadcastUsers();

        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void run() {
        try {
            Message message;
            while ((message = (Message) in.readObject()) != null) {

                switch (message.getType()) {

                    case "PRIVATE":
                        ServerChat.sendPrivate(
                                message.getSender(),
                                message.getReceiver(),
                                message.getMessage()
                        );
                        break;

                    case "PUBLIC":
                        ServerChat.broadcast(
                                message.getSender() + ": " +  message.getMessage()
                        );
                        break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                socket.close();
            } catch (IOException e) {}
            ServerChat.removeClient(this);
            ServerChat.broadcast(username + " saiu do chat.");
        }
    }

    public void sendMessage(Message message) {
        try {
            out.writeObject(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername(){
        return username;
    }
}
