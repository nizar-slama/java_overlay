import java.io.*;
import java.net.*;
import java.util.*;

class Application {
    String name;
    Map<Application, Integer> neighbors;
    int port;

    public Application(String name, int port) {
        this.name = name;
        this.port = port;
        this.neighbors = new HashMap<>();
    }

    public void addNeighbor(Application app, int cost) {
        neighbors.put(app, cost);
    }

    public void startServer() {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                System.out.println("✅ " + name + " écoute sur le port " + port);
                while (true) {
                    Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String message = in.readLine();
                    System.out.println("[" + name + "] MESSAGE REÇU : " + message);
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String toHost, int toPort, String message) {
        try (Socket socket = new Socket(toHost, toPort);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Network {
    static Map<String, Application> applications = new LinkedHashMap<>();

    static {
        applications.put("app1", new Application("app1", 5001));
        applications.put("app2", new Application("app2", 5002));
        applications.put("app3", new Application("app3", 5003));
        applications.put("app4", new Application("app4", 5004));
        applications.put("app5", new Application("app5", 5005));
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

class MessageRouter {
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

class Main {
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

        System.out.println("\n===== DÉMARRAGE DES SERVEURS =====");
        for (Application app : Network.applications.values()) {
            app.startServer();
        }

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
