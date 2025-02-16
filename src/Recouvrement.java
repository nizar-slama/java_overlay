import java.util.HashMap;
import java.util.Map;

public class Recouvrement {
    private String nom;
    private Map<String, Application> applicationsConnectees;

    // Constructeur pour initialiser le recouvrement
    public Recouvrement(String nom) {
        this.nom = nom;
        this.applicationsConnectees = new HashMap<>();
    }

    // Ajout d'une application au réseau de recouvrement
    public void ajouterApplication(Application app) {
        applicationsConnectees.put(app.getAdresse(), app);
    }

    // Diffusion message aux applications connectées
    public void diffuserMessage(String message) {
        System.out.println(nom + " diffuse : " + message);
        for (Application app : applicationsConnectees.values()) {
            app.recevoirMessage(message);
        }
    }
}
