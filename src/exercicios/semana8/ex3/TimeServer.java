package exercicios.semana8.ex3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class TimeServer {

    public static final int PORT = 2424;
    private ConcurrentHashMap<String, ClientHandler> clients = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        new TimeServer().startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor de tempo iniciado na porta " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClient(socket)).start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket socket) {
        try {
            socket.setSoTimeout(2000); // 2s para esperar confirmação
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Ler nome do cliente
            String clientName = (String) in.readObject();
            System.out.println("Cliente registado: " + clientName);

            ClientHandler handler = new ClientHandler(clientName, socket, in, out);
            clients.put(clientName, handler);

            // Envio periódico do tempo
            while (true) {
                long currentTime = System.currentTimeMillis();
                TimeMessage timeMsg = new TimeMessage(currentTime);
                out.writeObject(timeMsg);
                out.flush();

                try {
                    // Espera confirmação
                    ReceptionConfirmationMessage conf = (ReceptionConfirmationMessage) in.readObject();
                    System.out.println("Confirmação recebida de " + conf.getClientName());
                } catch (Exception e) {
                    System.out.println("Cliente " + clientName + " não confirmou a hora. Desligado.");
                    clients.remove(clientName);
                    socket.close();
                    break;
                }

                Thread.sleep(5000); // enviar hora a cada 5s (pode mudar)
            }

        } catch (Exception e) {
            System.out.println("Cliente desconectado.");
        }
    }

    private static class ClientHandler {
        String name;
        Socket socket;
        ObjectInputStream in;
        ObjectOutputStream out;

        public ClientHandler(String name, Socket socket, ObjectInputStream in, ObjectOutputStream out) {
            this.name = name;
            this.socket = socket;
            this.in = in;
            this.out = out;
        }
    }
}
