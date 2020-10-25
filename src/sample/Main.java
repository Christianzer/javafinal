package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static int identifiant_cand;
    private static String mail;
    private static String mdp;
    private static int DossierId;
    private static int idvisite;
    private static String identifiantjury;
    private static String nomjury;
    private static int dossiervisite;

    public static int getDossiervisite() {
        return dossiervisite;
    }

    public static void setDossiervisite(int dossiervisite) {
        Main.dossiervisite = dossiervisite;
    }

    public static int getIdvisite() {
        return idvisite;
    }

    public static void setIdvisite(int idvisite) {
        Main.idvisite = idvisite;
    }

    public static String getIdentifiantjury() {
        return identifiantjury;
    }

    public static void setIdentifiantjury(String identifiantjury) {
        Main.identifiantjury = identifiantjury;
    }

    public static String getNomjury() {
        return nomjury;
    }

    public static void setNomjury(String nomjury) {
        Main.nomjury = nomjury;
    }

    public static int getDossierId() {
        return DossierId;
    }

    public static void setDossierId(int dossierId) {
        DossierId = dossierId;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/connexion.fxml"));
        primaryStage.setTitle("SYSTEME AGRI CONNEXION");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static String getMdp() {
        return mdp;
    }

    public static void setMdp(String mdp) {
        Main.mdp = mdp;
    }

    public static String getMail() {
        return mail;
    }

    public static void setMail(String mail) {
        Main.mail = mail;
    }

    public static int getIdentifiant_cand() {
        return identifiant_cand;
    }

    public static void setIdentifiant_cand(int identifiant_cand) {
        Main.identifiant_cand = identifiant_cand;
    }




    public static void main(String[] args) {
        launch(args);
    }
}
