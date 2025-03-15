import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===== CONFIGURATION DES VOISINS =====\n");
        int count = 1;
        for (String appName : Network.applications.keySet()) {
            System.out.printf("[%d/5] Voisins pour '%s' (nom coût), 'fin' pour terminer :\n", count++, appName);
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
        System.out.print("Application destination : ");
        String destination = scanner.next();
        scanner.nextLine();
        System.out.print("Message à envoyer : ");
        String message = scanner.nextLine();

        System.out.print("\nChemin emprunté : ");
        MessageRouter.sendMessage(source, destination, message);
    }
}
