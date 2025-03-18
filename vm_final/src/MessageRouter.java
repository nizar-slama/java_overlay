import java.util.*;

public class MessageRouter {

    public static void sendMessage(String source, String destination, String message) {
        List<AppInstance> path = findShortestPath(Network.applications.get(source), Network.applications.get(destination));
        if (path.isEmpty()) {
            System.out.println("Erreur : Aucun chemin trouvé entre " + source + " et " + destination);
            return;
        }

        System.out.println("\nChemin emprunté : " + formatPath(path));

        for (int i = 1; i < path.size(); i++) {
            AppInstance fromApp = path.get(i - 1);
            AppInstance toApp = path.get(i);
            toApp.sendMessage(toApp.ip, toApp.port, "Message de " + fromApp.name + " : " + message);
        }
    }

    public static void broadcastMessage(AppInstance source, String message) {
        if (source == null) {
            System.out.println("Erreur : L'application source est introuvable.");
            return;
        }

        System.out.println("\nDiffusion globale depuis " + source.name + " ...");

        Set<AppInstance> visited = new HashSet<>();
        Queue<AppInstance> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {
            AppInstance current = queue.poll();
            for (AppInstance neighbor : current.neighbors.keySet()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                    neighbor.sendMessage(neighbor.ip, neighbor.port, "Message de " + source.name + " (BROADCAST) : " + message);
                }
            }
        }
    }

    public static void multicastMessage(AppInstance source, String[] targets, String message) {
        if (source == null) {
            System.out.println("Erreur : L'application source est introuvable.");
            return;
        }

        System.out.println("\nEnvoi d'un message à un sous-ensemble d’applications...");

        Set<AppInstance> visited = new HashSet<>();
        for (String targetName : targets) {
            AppInstance target = Network.applications.get(targetName);
            if (target != null) {
                List<AppInstance> path = findShortestPath(source, target);
                if (!path.isEmpty()) {
                    System.out.println("Chemin vers " + target.name + " : " + formatPath(path));
                    for (int i = 1; i < path.size(); i++) {
                        AppInstance fromApp = path.get(i - 1);
                        AppInstance toApp = path.get(i);
                        if (!visited.contains(toApp)) {
                            toApp.sendMessage(toApp.ip, toApp.port, "Message de " + fromApp.name + " : " + message);
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

    private static List<AppInstance> findShortestPath(AppInstance source, AppInstance destination) {
        Map<AppInstance, Integer> distances = new HashMap<>();
        Map<AppInstance, AppInstance> previous = new HashMap<>();
        PriorityQueue<AppInstance> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));

        for (AppInstance app : Network.applications.values()) {
            distances.put(app, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        queue.add(source);

        while (!queue.isEmpty()) {
            AppInstance current = queue.poll();
            if (current == destination) break;
            for (Map.Entry<AppInstance, Integer> entry : current.neighbors.entrySet()) {
                AppInstance neighbor = entry.getKey();
                int newDist = distances.get(current) + entry.getValue();
                if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDist);
                    previous.put(neighbor, current);
                    queue.add(neighbor);
                }
            }
        }

        List<AppInstance> path = new ArrayList<>();
        for (AppInstance at = destination; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }

    private static String formatPath(List<AppInstance> path) {
        return path.stream().map(app -> app.name).reduce((a, b) -> a + " -> " + b).orElse("");
    }
}
