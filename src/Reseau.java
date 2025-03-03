class Reseau {
    public static void main(String[] args) {
        AdressesGestion gestionnaire = new AdressesGestion("192.168.1.");

        Application app1 = new Application("Application 1", gestionnaire.attribuerNouvelleAdresse(), "10.0.0.1", 8080, "rmi://app1", "Texte");
        Application app2 = new Application("Application 2", gestionnaire.attribuerNouvelleAdresse(), "10.0.0.2", 9090, "rmi://app2", "Image");
        Application app3 = new Application("Application 3", gestionnaire.attribuerNouvelleAdresse(), "10.0.0.3", 7070, "rmi://app3", "Vidéo");

        Recouvrement recouvrement1 = new Recouvrement("Recouvrement1");
        recouvrement1.ajouterApplication(app1);
        recouvrement1.ajouterApplication(app2);
        recouvrement1.ajouterApplication(app3);

        app1.ajouterConnexion(app2, 5);
        app2.ajouterConnexion(app3, 10);
        app3.ajouterConnexion(app1, 3);

        recouvrement1.construireArbreDeDiffusion(app1);
        recouvrement1.diffuserMessage("Message multicast depuis " + app1.getNom(), app1);
    }
}