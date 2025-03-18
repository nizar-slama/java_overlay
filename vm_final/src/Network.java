import java.util.*;

public class Network {
    public static Map<String, AppInstance> applications = new LinkedHashMap<>();

    static {
        applications.put("app1", new AppInstance("app1", "198.18.60.246", 50011));
        applications.put("app2", new AppInstance("app2", "198.18.60.247", 50012));
        applications.put("app3", new AppInstance("app3", "198.18.60.248", 50013));
        applications.put("app4", new AppInstance("app4", "198.18.60.249", 50014));
        applications.put("app5", new AppInstance("app5", "198.18.60.250", 50015));
    }

    public static void addConnection(String app1, String app2, int cost) {
        AppInstance a1 = applications.get(app1);
        AppInstance a2 = applications.get(app2);
        if (a1 != null && a2 != null) {
            a1.addNeighbor(a2, cost);
            a2.addNeighbor(a1, cost);
        }
    }

    public static void printGraph() {
        System.out.println("\nArbre de diffusion formé :");
        Set<AppInstance> visited = new HashSet<>();
        AppInstance root = applications.get("app1");
        if (root != null) {
            printSubtree(root, "", visited);
        } else {
            System.out.println("Erreur : Point d'entrée (app1) introuvable.");
        }
    }

    private static void printSubtree(AppInstance app, String prefix, Set<AppInstance> visited) {
        if (visited.contains(app)) return;
        visited.add(app);

        System.out.println(prefix + "└── " + app.name);

        List<AppInstance> neighborsList = new ArrayList<>(app.neighbors.keySet());
        neighborsList.sort(Comparator.comparing(a -> a.name));

        for (int i = 0; i < neighborsList.size(); i++) {
            AppInstance neighbor = neighborsList.get(i);
            String newPrefix = prefix + (i == neighborsList.size() - 1 ? "    " : "│   ");
            printSubtree(neighbor, newPrefix, visited);
        }
    }
}
