import java.util.HashSet;
import java.util.Set;

public class AdressesGestion {
    private String prefixe;
    private int dernierOctet;
    private Set<String> adressesAttribuees;


    public AdressesGestion(String prefixe) {
        this.prefixe = prefixe;
        this.dernierOctet = 1;
        this.adressesAttribuees = new HashSet<>();
    }

    // Génère une nouvelle adresse unique
    public String attribuerNouvelleAdresse() {
        while (adressesAttribuees.contains(prefixe + dernierOctet)) {
            dernierOctet++;  // Incrémente pour éviter les doublons
        }
        String nouvelleAdresse = prefixe + dernierOctet;
        adressesAttribuees.add(nouvelleAdresse);
        return nouvelleAdresse;
    }

    // Libère une adresse lorsqu'une application quitte le réseau
    public void libererAdresse(String adresse) {
        adressesAttribuees.remove(adresse);
    }
}
