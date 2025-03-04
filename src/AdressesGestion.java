import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class AdressesGestion {
    private String prefixe;
    private int dernierOctet;
    private List<String> adressesAttribuees;

    public AdressesGestion(String prefixe) {
        this.prefixe = prefixe;
        this.dernierOctet = 1;
        this.adressesAttribuees = new ArrayList<>();
    }

    public String attribuerNouvelleAdresse() {
        while (adressesAttribuees.contains(prefixe + dernierOctet)) {
            dernierOctet++;
        }
        String nouvelleAdresse = prefixe + dernierOctet;
        adressesAttribuees.add(nouvelleAdresse);
        return nouvelleAdresse;
    }
}