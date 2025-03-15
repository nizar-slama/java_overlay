import java.util.*;

public class MessageRouter {

    public static void sendMessage(String source, String destination, String message) {
        List<Application> path = findShortestPath(Network.applications.get(source), Network.applications.get(destination));
        System.out.println("\nChemin emprunté : " + formatPath(path));

        for (int i = 1; i < path.size(); i++) {
            Application fromApp = path.get(i - 1);
            Application toApp = path.get(i);
            toApp.sendMessage("localhost", toApp.port, "Message de " + fromApp.name + " : " + message);
        }
    }

    public static void broadcastMessage(Application source, String message) {
        if (source == null) {
            System.out.println("Erreur : L'application source est introuvable.");
            return;
        }

        System.out.println("\nDiffusion globale depuis " + source.name + " ...");

        Set<Application> visited = new HashSet<>();
        Queue<Application> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {
            Application current = queue.poll();
            for (Application neighbor : current.neighbors.keySet()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    neighbor.sendMessage("localhost", neighbor.port, "Message de " + source.name + " (BROADCAST) : " + message);
                }
            }
        }
    }

    public static void multicastMessage(Application source, String[] targets, String message) {
        if (source == null) {
            System.out.println("Erreur : L'application source est introuvable.");
            return;
        }

        System.out.println("\nEnvoi d'un message à un sous-ensemble d’applications...");

        Set<Application> visited = new HashSet<>();
        for (String targetName : targets) {
            Application target = Network.applications.get(targetName);
            if (target != null) {
                List<Application> path = findShortestPath(source, target);
                if (!path.isEmpty()) {
                    System.out.println("Chemin vers " + target.name + " : " + formatPath(path));
                    for (int i = 1; i < path.size(); i++) {
                        Application fromApp = path.get(i - 1);
                        Application toApp = path.get(i);
                        if (!visited.contains(toApp)) {
                            toApp.sendMessage("localhost", toApp.port, "Message de " + fromApp.name + " : " + message);
                            visited.add(toApp);
                        }
                    }
                } else {
                    System.out.println("Aucun chemin trouvé vers " + target.name);
                }
            } else {
                System.out.println("Application cible " + targetName + " introuvable.");
            }
        }
    }

    private static List<Application> findShortestPath(Application source, Application destination) {
        Map<Application, Integer> distances = new HashMap<>();
        Map<Application, Application> previous = new HashMap<>();
        PriorityQueue<Application> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (Application app : Network.applications.values()) {
            distances.put(app, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        queue.add(source);

        while (!queue.isEmpty()) {
            Application current = queue.poll();
            if (current == destination) break;
            for (Map.Entry<Application, Integer> entry : current.neighbors.entrySet()) {
                Application neighbor = entry.getKey();
                int newDist = distances.get(current) + entry.getValue();
                if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<Application> path = new ArrayList<>();
        for (Application at = destination; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    private static String formatPath(List<Application> path) {
        return path.stream().map(app -> app.name).reduce((a, b) -> a + " -> " + b).orElse("");
    }
}
