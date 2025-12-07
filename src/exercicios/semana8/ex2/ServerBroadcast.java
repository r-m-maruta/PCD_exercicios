package exercicios.semana8.ex2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerBroadcast {

    public static final int PORTO = 8080;

    // Lista de todos os ObjectOutputStream ativos
    private final List<ObjectOutputStream> clientOutputs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        new ServerBroadcast().startServing();
    }

    public void startServing() throws IOException {
        ServerSocket ss = new ServerSocket(PORTO);
        System.out.println("Servidor iniciado na porta " + PORTO);

        try {
            while (true) {
                Socket socket = ss.accept();
                System.out.println("Cliente ligado: " + socket);

                new Thread(() -> handleClient(socket)).start();
            }
        } finally {
            ss.close();
        }
    }

    private void handleClient(Socket socket) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Adiciona o stream do cliente à lista
            synchronized (clientOutputs) {
                clientOutputs.add(out);
            }

            Message message;
            while ((message = (Message) in.readObject()) != null) {
                System.out.println("Mensagem recebida: " + message);
                broadcast(message);
                System.out.println("Enviando mensagem de " + message.getSender() + " para todos os clientes...");
            }

        } catch (IOException | ClassNotFoundException e) {
            // Ignorar para simplificação
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }

            // Remove o stream do cliente da lista quando desconecta
            synchronized (clientOutputs) {
                clientOutputs.removeIf(out -> out.equals(socket));
            }
            System.out.println("Cliente desconectado da thread " + Thread.currentThread().getName());
        }
    }

    // Envia a mensagem para todos os clientes conectados
    private void broadcast(Message message) {
        synchronized (clientOutputs) {
            for (ObjectOutputStream out : clientOutputs) {
                try {
                    out.writeObject(message);
                    out.flush();
                } catch (IOException e) {
                    // Ignorar erros de envio
                }
            }
        }
    }
}
