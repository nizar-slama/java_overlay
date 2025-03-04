import java.util.*;
import java.io.*;

class Reseau {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Application> applications = Arrays.asList(
                new Application("Application 1", Niveau.NIVEAU_1),
                new Application("Application 2", Niveau.NIVEAU_2),
                new Application("Application 3", Niveau.NIVEAU_3),
                new Application("Application 4", Niveau.NIVEAU_2),
                new Application("Application 5", Niveau.NIVEAU_3)
        );

        while (true) {
            System.out.println("\n======================================");
            System.out.println("🏛️  MENU - RÉSEAU DE RECOUVREMENT 🏛️");
            System.out.println("======================================");
            System.out.println("1️⃣  Envoyer un message");
            System.out.println("2️⃣  Afficher l'arbre hiérarchique");
            System.out.println("3️⃣  Quitter");
            System.out.print("➡️  Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            if (choix == 1) {
                System.out.println("\n📢 Sélectionnez une application pour envoyer un message :");
                for (int i = 0; i < applications.size(); i++) {
                    System.out.println((i + 1) + "️⃣  " + applications.get(i).getNom());
                }

                System.out.print("➡️ Entrez le numéro de l'application : ");
                int appIndex = scanner.nextInt() - 1;
                scanner.nextLine();

                if (appIndex >= 0 && appIndex < applications.size()) {
                    Application emetteur = applications.get(appIndex);

                    System.out.print("💬 Entrez votre message : ");
                    String message = scanner.nextLine();

                    emetteur.envoyerMessage(message);

                    System.out.println("\n📩 Message envoyé à :");
                    applications.stream()
                            .filter(app -> !app.getNom().equalsIgnoreCase(emetteur.getNom()))
                            .forEach(app -> System.out.println("📥 " + app.getNom()));

                } else {
                    System.out.println("❌ Erreur : Application non trouvée !");
                }

            } else if (choix == 2) {
                System.out.println("\n🌳 Arbre hiérarchique des applications :");
                afficherArbreParNiveau(applications);
            } else if (choix == 3) {
                System.out.println("👋 Au revoir !");
                break;
            }
        }
    }

    private static void afficherArbreParNiveau(List<Application> applications) {
        Map<Niveau, List<Application>> arbre = new HashMap<>();
        for (Niveau niveau : Niveau.values()) {
            arbre.put(niveau, new ArrayList<>());
        }
        for (Application app : applications) {
            arbre.get(app.getNiveau()).add(app);
        }

        for (Niveau niveau : Niveau.values()) {
            System.out.println("\n⭐ Niveau " + niveau + " :");
            for (Application app : arbre.get(niveau)) {
                System.out.println("   📖 " + app.getNom());
            }
        }
    }
}
