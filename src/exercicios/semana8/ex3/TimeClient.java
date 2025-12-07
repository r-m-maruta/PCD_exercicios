package exercicios.semana8.ex3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class TimeClient {

    private String clientName;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static void main(String[] args) {
        new TimeClient("Cliente1").startClient();
    }

    public TimeClient(String name) {
        this.clientName = name;
    }

    public void startClient() {
        try {
            InetAddress endereco = InetAddress.getByName(null);
            socket = new Socket(endereco, TimeServer.PORT);
            System.out.println("Conectado ao servidor: " + socket);

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // Enviar nome do cliente
            out.writeObject(clientName);
            out.flush();

            while (true) {
                TimeMessage timeMsg = (TimeMessage) in.readObject();
                System.out.println("Hora recebida: " + timeMsg.getTimestamp());

                // Responder com confirmação
                ReceptionConfirmationMessage conf = new ReceptionConfirmationMessage(clientName);
                out.writeObject(conf);
                out.flush();
            }

        } catch (Exception e) {
            System.out.println("Servidor desconectado ou erro.");
        } finally {
            try { socket.close(); } catch (Exception e) {}
        }
    }
}
