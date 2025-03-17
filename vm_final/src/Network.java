import java.util.*;

public class Network {
    public static Map<String, Application> applications = new LinkedHashMap<>();

    static {
        applications.put("app1", new Application("app1", "198.18.60.246", 50011));
        applications.put("app2", new Application("app2", "198.18.60.247", 50012));
        applications.put("app3", new Application("app3", "198.18.60.248", 50013));
        applications.put("app4", new Application("app4", "198.18.60.249", 50014));
        applications.put("app5", new Application("app5", "198.18.60.250", 50015));
    }

    public static void addConnection(String app1, String app2, int cost) {
        Application a1 = applications.get(app1);
        Application a2 = applications.get(app2);
        if (a1 != null && a2 != null) {
            a1.addNeighbor(a2, cost);
            a2.addNeighbor(a1, cost);
        }
    }


    public static void printGraph() {
        System.out.println("\nArbre de diffusion formé :");
        Set<Application> visited = new HashSet<>();
        Application root = applications.get("app1");
        if (root != null) {
            printSubtree(root, "", visited);
        } else {
            System.out.println("Erreur : Point d'entrée (app1) introuvable.");
        }
    }

    private static void printSubtree(Application app, String prefix, Set<Application> visited) {
        if (visited.contains(app)) return;
        visited.add(app);

        System.out.println(prefix + "└── " + app.name);

        List<Application> neighborsList = new ArrayList<>(app.neighbors.keySet());
        neighborsList.sort(Comparator.comparing(a -> a.name));

        for (int i = 0; i < neighborsList.size(); i++) {
            Application neighbor = neighborsList.get(i);
            String newPrefix = prefix + (i == neighborsList.size() - 1 ? "    " : "│   ");
            printSubtree(neighbor, newPrefix, visited);
        }
    }
}
