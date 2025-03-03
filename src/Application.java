import java.util.ArrayList;
import java.util.List;
class Application {
    private String nom;
    private String adresseLogique;
    private String adresseIP;
    private int port;
    private String urlRMI;
    private String typeContenu;
    private List<Connexion> connexions;

    public Application(String nom, String adresseLogique, String adresseIP, int port, String urlRMI, String typeContenu) {
        this.nom = nom;
        this.adresseLogique = adresseLogique;
        this.adresseIP = adresseIP;
        this.port = port;
        this.urlRMI = urlRMI;
        this.typeContenu = typeContenu;
        this.connexions = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void ajouterConnexion(Application app, int metrique) {
        connexions.add(new Connexion(app.getAdresse(), app.getAdresseIP(), app.getPort(), app.getUrlRMI(), metrique, app.getTypeContenu()));
    }

    public void recevoirMessage(String message) {
        System.out.println(nom + " (" + adresseIP + ") a reçu : " + message);
    }

    public String getAdresse() { return adresseLogique; }
    public String getAdresseIP() { return adresseIP; }
    public int getPort() { return port; }
    public String getUrlRMI() { return urlRMI; }
    public String getTypeContenu() { return typeContenu; }

    public void afficherConnexions() {
        System.out.println("Connexions de " + nom + " :");
        for (Connexion c : connexions) {
            System.out.println(" - " + c);
        }
    }
}

class Application1 {
    public static void main(String[] args) {
        AdressesGestion gestionnaire = new AdressesGestion("192.168.1.");
        Application app = new Application("Application 1", gestionnaire.attribuerNouvelleAdresse(), "10.0.0.1", 8080, "rmi://app1", "Texte");
        System.out.println(app.getNom() + " démarrée avec l'adresse " + app.getAdresse());
    }
}

class Application2 {
    public static void main(String[] args) {
        AdressesGestion gestionnaire = new AdressesGestion("192.168.1.");
        Application app = new Application("Application 2", gestionnaire.attribuerNouvelleAdresse(), "10.0.0.2", 9090, "rmi://app2", "Image");
        System.out.println(app.getNom() + " démarrée avec l'adresse " + app.getAdresse());
    }
}

class Application3 {
    public static void main(String[] args) {
        AdressesGestion gestionnaire = new AdressesGestion("192.168.1.");
        Application app = new Application("Application 3", gestionnaire.attribuerNouvelleAdresse(), "10.0.0.3", 7070, "rmi://app3", "Vidéo");
        System.out.println(app.getNom() + " démarrée avec l'adresse " + app.getAdresse());
    }
}
