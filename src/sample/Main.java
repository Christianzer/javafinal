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
