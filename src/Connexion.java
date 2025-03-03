class Connexion {
    private String adresseLogique;
    private String adresseIP;
    private int port;
    private String urlRMI;
    private int metrique;
    private String description;

    public Connexion(String adresseLogique, String adresseIP, int port, String urlRMI, int metrique, String description) {
        this.adresseLogique = adresseLogique;
        this.adresseIP = adresseIP;
        this.port = port;
        this.urlRMI = urlRMI;
        this.metrique = metrique;
        this.description = description;
    }

    @Override
    public String toString() {
        return "AdresseLogique: " + adresseLogique + ", IP: " + adresseIP + ":" + port +
                ", RMI: " + urlRMI + ", Métrique: " + metrique + ", Description: " + description;
    }
}
