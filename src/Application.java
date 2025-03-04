import java.util.*;
import java.io.*;
import java.net.*;

class Application {
    private String nom;
    private Niveau niveau;
    private PrintWriter out;
    private BufferedReader in;

    public Application(String nom, Niveau niveau) {
        this.nom = nom;
        this.niveau = niveau;

        try {
            Socket socket = new Socket("localhost", 5050);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Thread pour écouter les messages en temps réel
            new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (IOException e) {
            System.out.println("❌ Erreur : Impossible de se connecter au serveur Recouvrement !");
        }
    }

    public void envoyerMessage(String message) {
        System.out.println("📤 " + nom + " envoie : " + message);
        out.println(nom + " (Niveau " + niveau + ") : " + message);
    }




    public String getNom() {
        return nom;
    }
    public Niveau getNiveau() {
        return niveau;
    }
}

enum Niveau {
    NIVEAU_1, NIVEAU_2, NIVEAU_3;
}

class Application1 {
    public static void main(String[] args) {
        Application app = new Application("Application 1", Niveau.NIVEAU_1);

    }
}

class Application2 {
    public static void main(String[] args) {
        Application app = new Application("Application 2", Niveau.NIVEAU_2);

    }
}

class Application3 {
    public static void main(String[] args) {
        Application app = new Application("Application 3", Niveau.NIVEAU_3);

    }
}
class Application4 {
    public static void main(String[] args) {
        Application app = new Application("Application 4", Niveau.NIVEAU_2);

    }
}
class Application5 {
    public static void main(String[] args) {
        Application app = new Application("Application 5", Niveau.NIVEAU_3);

    }
}


