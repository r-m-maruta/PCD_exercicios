package exercicios.semana8.ex4;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerChat {
    private static final int PORT = 9090;
    private static Map<String, ClientHandler> clients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor de chat iniciado na porta " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Cliente ligado: " + socket);
            ClientHandler handler = new ClientHandler(socket);
            clients.put(handler.getUsername(), handler);
            broadcast(handler.getUsername() + " entrou no chat!");
            broadcastUsers();
            new Thread(handler).start();
        }
    }

    public static void broadcast(String message) {
        System.out.println("Mensagem recebida: " + message);
        for (ClientHandler client : clients.values()) {
            client.sendMessage(new Message("PUBLIC", "SERVER", null, message));
        }
    }

    public static void broadcastUsers() {
        String users = "USERS|" + String.join(",", clients.keySet());
        for (ClientHandler client : clients.values()) {
            client.sendMessage(users);
        }
    }

    public static void sendPrivate(String sender, String reciver, String message) {
        ClientHandler destinatario = clients.get(reciver);
        if (destinatario != null) {
            destinatario.sendMessage(new Message("PRIVATE", sender, reciver, message));
        }
    }

    public static void removeClient(ClientHandler client) {
        clients.remove(client.getUsername());
        broadcastUsers();
    }
}
