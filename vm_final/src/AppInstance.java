import java.io.*;
import java.net.*;
import java.util.*;

public class AppInstance {
    String name;
    String ip;
    int port;
    Map<AppInstance, Integer> neighbors;

    public AppInstance(String name, String ip, int port) {
        this.name = name;
        this.ip = ip;
        this.port = port;
        this.neighbors = new HashMap<>();
    }

    public void addNeighbor(AppInstance neighbor, int cost) {
        neighbors.put(neighbor, cost);
    }

    public void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println(name + " écoute sur le port " + port);
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

    public void sendMessage(String toHost, int toPort, String message) {
        try (Socket socket = new Socket(toHost, toPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java AppInstance <app_name>");
            return;
        }

        String appName = args[0];
        AppInstance instance = Network.applications.get(appName);
        if (instance != null) {
            instance.startServer();
        } else {
            System.out.println("Erreur : Application inconnue.");
        }
    }
}
