import java.util.*;

public class MessageRouter {
    public static List<Application> findShortestPath(Application source, Application destination) {
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
                if (newDist < distances.get(neighbor)) {
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

    public static void sendMessage(String source, String destination, String message) {
        Application src = Network.applications.get(source);
        Application dest = Network.applications.get(destination);
        List<Application> path = findShortestPath(src, dest);
        System.out.println(String.join(" -> ", path.stream().map(app -> app.name).toList()));
        for (int i = 1; i < path.size(); i++) {
            Application fromApp = path.get(i - 1);
            Application toApp = path.get(i);
            toApp.sendMessage("localhost", toApp.port, "Message de " + fromApp.name + " : " + message);
        }
    }
}
