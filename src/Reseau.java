public class Reseau {
    public static void main(String[] args) {
        // Création du gestionnaire d'adresses
        AdressesGestion AdressesGestion = new AdressesGestion("192.168.1.");

        // Création des applications avec plus d'infos
        Application app1 = new Application("Application 1", AdressesGestion, "10.0.0.1", 8080, "rmi://app1", "Texte");
        Application app2 = new Application("Application 2", AdressesGestion, "10.0.0.2", 9090, "rmi://app2", "Image");
        Application app3 = new Application("Application 3", AdressesGestion, "10.0.0.3", 7070, "rmi://app3", "Vidéo");

        // Création nœud de recouvrement
        Recouvrement recouvrement1 = new Recouvrement("Recouvrement1");

        // Connexion des applications au nœud de recouvrement
        recouvrement1.ajouterApplication(app1);
        recouvrement1.ajouterApplication(app2);
        recouvrement1.ajouterApplication(app3);

        // Ajout des connexions entre applications
        app1.ajouterConnexion(app2, 5);
        app2.ajouterConnexion(app3, 10);
        app3.ajouterConnexion(app1, 3);

        // Affichage des connexions
        app1.afficherConnexions();
        app2.afficherConnexions();
        app3.afficherConnexions();

        // Diffusion d'un message
        recouvrement1.diffuserMessage("Message important");
    }
}
