import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Recouvrement {
    private String nom;
    private Map<String, Application> applicationsConnectees;
    private Map<Application, List<Application>> arbreDeDiffusion;

    public Recouvrement(String nom) {
        this.nom = nom;
        this.applicationsConnectees = new HashMap<>();
        this.arbreDeDiffusion = new HashMap<>();
    }

    public void ajouterApplication(Application app) {
        applicationsConnectees.put(app.getAdresse(), app);
    }

    public void construireArbreDeDiffusion(Application source) {
        List<Application> ordreDeDiffusion = new ArrayList<>(applicationsConnectees.values());
        arbreDeDiffusion.put(source, ordreDeDiffusion);
    }

    public void diffuserMessage(String message, Application source) {
        System.out.println(nom + " diffuse depuis " + source.getNom() + " : " + message);
        if (arbreDeDiffusion.containsKey(source)) {
            for (Application app : arbreDeDiffusion.get(source)) {
                if (!app.equals(source)) {
                    app.recevoirMessage(message);
                }
            }
        }
    }
}