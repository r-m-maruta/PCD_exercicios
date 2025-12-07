package exercicios.semana8.ex4;

import java.io.*;
import java.net.*;
import java.util.*;

public class ServerChat {
    private static final int PORT = 9090;
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor de chat iniciado na porta " + PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Cliente ligado: " + socket);
            ClientHandler handler = new ClientHandler(socket);
            clients.add(handler);
            new Thread(handler).start();
        }
    }

    public static void broadcast(String message) {
        System.out.println("Mensagem recebida: " + message);
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    public static void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}
