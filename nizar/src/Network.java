import java.util.*;

public class Network {
    public static Map<String, Application> applications = new LinkedHashMap<>();

    static {
        applications.put("app1", new Application("app1", 50011));
        applications.put("app2", new Application("app2", 50012));
        applications.put("app3", new Application("app3", 50013));
        applications.put("app4", new Application("app4", 50014));
        applications.put("app5", new Application("app5", 50015));
    }

    public static void addConnection(String app1, String app2, int cost) {
        Application a1 = applications.get(app1);
        Application a2 = applications.get(app2);
        if (a1 != null && a2 != null) {
            a1.neighbors.put(a2, cost);
            a2.neighbors.put(a1, cost);
        }
    }
    public static void printGraph() {
        System.out.println("\n📡 Arbre de diffusion formé :");
        Set<Application> visited = new HashSet<>();
        for (Application app : applications.values()) {
            if (!visited.contains(app)) {
                printSubtree(app, "", visited);
            }
        }
    }

    private static void printSubtree(Application app, String prefix, Set<Application> visited) {
        if (visited.contains(app)) return;
        visited.add(app);
        System.out.println(prefix + "└── " + app.name);
        List<Application> neighborsList = new ArrayList<>(app.neighbors.keySet());
        neighborsList.removeAll(visited);
        for (int i = 0; i < neighborsList.size(); i++) {
            Application neighbor = neighborsList.get(i);
            String newPrefix = prefix + (i == neighborsList.size() - 1 ? "    " : "│   ");
            printSubtree(neighbor, newPrefix, visited);
        }
    }

}
