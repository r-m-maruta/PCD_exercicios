package exercicios.semana8.ex1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class SimpleServer {

	private BufferedReader in;

	private PrintWriter out;

	void doConnections(Socket socket) throws IOException {
		in = new BufferedReader(new InputStreamReader(
				socket.getInputStream()));
		out = new PrintWriter(new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream())),
				true);
	}
	private void serve() throws IOException {
		while (true) {
			String str = in.readLine();
			if (str.equals("FIM"))
				break;
			System.out.println("Eco:" + str);
			out.println(str);
		}
	}

	public static final int PORTO = 8080;
	public static void main(String[] args) {
		try {
			new SimpleServer().startServing();
		} catch (IOException e) {
			// ...
		}
	}

    public void startServing() throws IOException {
        ServerSocket ss = new ServerSocket(PORTO);
        try {
            while (true) {  //agora aceita vários clientes
                Socket socket = ss.accept();
                System.out.println(
                        "Cliente ligado: " + socket +
                                " | Aceito pela thread: " + Thread.currentThread().getName()
                );

                // cada cliente tratado numa thread
                new Thread(() -> {
                    System.out.println("A servir cliente em thread: " + Thread.currentThread().getName());
                    try {
                        doConnections(socket);
                        serve();
                    } catch (IOException e) {
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                        }
                        System.out.println("Cliente desconectado da thread " + Thread.currentThread().getName());
                    }
                }).start();
            }
        } finally {
            ss.close();
        }
    }
}
