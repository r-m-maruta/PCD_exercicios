package exercicios.semana8.ex4;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            // Recebe o nome do cliente na primeira mensagem
            username = in.readLine();
            ServerChat.broadcast(username + " entrou no chat.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.equalsIgnoreCase("FIM")) break;
                ServerChat.broadcast(username + ": " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {}
            ServerChat.removeClient(this);
            ServerChat.broadcast(username + " saiu do chat.");
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
