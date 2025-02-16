import java.util.ArrayList;
import java.util.List;

public class Application {
    private String nom;
    private String adresseLogique;
    private String adresseIP;
    private int port;
    private String urlRMI;
    private String typeContenu;
    private List<Connexion> connexions; // Liste des connexions vers d'autres applications

    // Constructeur avec toutes les informations
    public Application(String nom, AdressesGestion gestionnaire, String adresseIP, int port, String urlRMI, String typeContenu) {
        this.nom = nom;
        this.adresseLogique = gestionnaire.attribuerNouvelleAdresse();
        this.adresseIP = adresseIP;
        this.port = port;
        this.urlRMI = urlRMI;
        this.typeContenu = typeContenu;
        this.connexions = new ArrayList<>();
    }

    // Ajouter une connexion à une autre application
    public void ajouterConnexion(Application app, int metrique) {
        connexions.add(new Connexion(app.getAdresse(), app.getAdresseIP(), app.getPort(), app.getUrlRMI(), metrique, app.getTypeContenu()));
    }

    public void recevoirMessage(String message) {
        System.out.println(nom + " (" + adresseIP + ") a reçu : " + message);
    }

    public String getAdresse() {
        return adresseLogique;
    }

    public String getAdresseIP() {
        return adresseIP;
    }

    public int getPort() {
        return port;
    }

    public String getUrlRMI() {
        return urlRMI;
    }

    public String getTypeContenu() {
        return typeContenu;
    }

    public void afficherConnexions() {
        System.out.println("Connexions de " + nom + " :");
        for (Connexion c : connexions) {
            System.out.println(" - " + c);
        }
    }
}
