import java.io.*;
import java.net.*;
import java.util.*;

public class Application {
    String name;
    String ip;
    Map<Application, Integer> neighbors;
    int port;

    public Application(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.neighbors = new HashMap<>();
    }

    public void addNeighbor(Application app, int cost) {
        neighbors.put(app, cost);
    }

    public void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println(name + " écoute sur " + ip + " port " + port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message = in.readLine();
                    System.out.println("[" + name + "] MESSAGE REÇU : " + message);
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String ip, int port, String message) {
        try (Socket socket = new Socket(ip, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
