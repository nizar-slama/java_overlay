import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import java.io.*;
import java.net.*;
import java.util.*;
class RecouvrementServer {
    private static final int PORT = 5050;
    private static List<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("🌍 [Recouvrement] Serveur en attente de connexions...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("✅ Nouveau client connecté !");
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                clients.add(out);

                new Thread(() -> {
                    try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                        String message;
                        while ((message = in.readLine()) != null) {
                            diffuserMessage(message);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void diffuserMessage(String message) {
        System.out.println("🌍 [Recouvrement] Diffusion : " + message);
        String sender = message.split(" : ")[0]; // Extraire le nom de l'expéditeur

        for (PrintWriter client : clients) {
            client.println("📥 Message reçu : " + message);
        }
    }

}
