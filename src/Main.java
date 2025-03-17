import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== CONFIGURATION DES VOISINS =====\n");
        int count = 1;
        for (String appName : Network.applications.keySet()) {
            System.out.printf("[%d/%d] Voisins pour '%s' (nom coût), 'fin' pour terminer :\n", count++, Network.applications.size(), appName);
            while (scanner.hasNext()) {
                String neighbor = scanner.next();
                if (neighbor.equals("fin")) break;
                int cost = scanner.nextInt();
                Network.addConnection(appName, neighbor, cost);
            }
            System.out.println();
        }

        Network.printGraph();

        System.out.println("\n===== ENVOI D'UN MESSAGE =====");
        System.out.print("Application source : ");
        String source = scanner.next();
        scanner.nextLine();

        System.out.println("\n Choisissez l'option d'envoi :");
        System.out.println("1 - Envoyer à une seule application");
        System.out.println("2 - Diffusion à tout le réseau");
        System.out.println("3 - Envoyer à un sous-ensemble d’applications");

        int choix = scanner.nextInt();
        scanner.nextLine();

        String message;
        if (choix == 1) {
            System.out.print("Application destination : ");
            String destination = scanner.next();
            scanner.nextLine();
            System.out.print("Message à envoyer : ");
            message = scanner.nextLine();
            System.out.print("\n Chemin emprunté : ");
            MessageRouter.sendMessage(source, destination, message);
        } else if (choix == 2) {
            System.out.print("Message à diffuser à tout le réseau : ");
            message = scanner.nextLine();
            MessageRouter.broadcastMessage(Network.applications.get(source), message);
        } else if (choix == 3) {
            System.out.print("Entrez les applications cibles séparées par un espace : ");
            String[] targets = scanner.nextLine().split(" ");
            System.out.print("Message à envoyer : ");
            message = scanner.nextLine();
            MessageRouter.multicastMessage(Network.applications.get(source), targets, message);
        } else {
            System.out.println(" Choix invalide.");
        }
    }
}
