import java.util.Scanner;

public class App5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Application app1 = new Application("app5", "198.18.60.250", 50015);

        System.out.println("Définition des voisins pour app5 (nom ip port coût), 'fin' pour terminer :");
        while (true) {
            String line = scanner.nextLine();
            if ("fin".equals(line)) break;
            String[] data = line.split(" ");
            if (data.length == 4) {
                String voisinNom = data[0];
                String voisinIp = data[1];
                int voisinPort = Integer.parseInt(data[2]);
                int cout = Integer.parseInt(data[3]);
                Application voisin = new Application(voisinNom, voisinIp, voisinPort);
                app1.addNeighbor(voisin, cout);
            } else {
                System.out.println("Format invalide. Réessayez (nom IP port coût).");
            }
        }

        app1.startServer();

        System.out.println("Prêt à recevoir des messages sur " + app1.ip + ":" + app1.port);
    }
}