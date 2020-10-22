package sample.controllers;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.database.AgriConnexion;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ValidationDossierControllers implements Initializable {

    public ImageView imageCand;
    public Label dossierid;
    private int dossierId;

    private int docid;
    @FXML
    private TextField nomcand;

    @FXML
    private TextField prenomCand;

    @FXML
    private TextField datenaiss;

    @FXML
    private TextField nationalite;

    @FXML
    private TextField piece;

    @FXML
    private TextField numpiece;

    @FXML
    private TextField dipl;


    @FXML
    private TextField numdipl;

    @FXML
    private TextField loacl;

    @FXML
    private TextField superf;

    @FXML
    private TextField typecult;

    @FXML
    private TextField methode;


    @FXML
    private TextField nbreempl;

    @FXML
    private TextField nbrefemme;

    @FXML
    private TextField mineur;

    @FXML
    private TextField salaire;

    @FXML
    private Hyperlink certempl;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dossierId = Main.getDossierId();
        System.out.println(dossierId);
        dossierid.setText("DOSSIER NUMERO : "+String.valueOf(dossierId));
        try {
            this.getAfficherDossier();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void getAfficherDossier() throws SQLException {
        String afficher = "{call SELECTDOSSIER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement callableStatement = AgriConnexion.getInstance().prepareCall(afficher);
        callableStatement.setInt(1,dossierId);
        callableStatement.registerOutParameter(2,Types.VARCHAR);
        callableStatement.registerOutParameter(3,Types.VARCHAR);
        callableStatement.registerOutParameter(4, Types.VARCHAR);
        callableStatement.registerOutParameter(5,Types.VARCHAR);
        callableStatement.registerOutParameter(6,Types.VARCHAR);
        callableStatement.registerOutParameter(7,Types.VARCHAR);
        callableStatement.registerOutParameter(8,Types.VARCHAR);
        callableStatement.registerOutParameter(9,Types.VARCHAR);
        callableStatement.registerOutParameter(10, Types.VARCHAR);
        callableStatement.registerOutParameter(11,Types.VARCHAR);
        callableStatement.registerOutParameter(12,Types.VARCHAR);
        callableStatement.registerOutParameter(13, Types.VARCHAR);
        callableStatement.registerOutParameter(14,Types.VARCHAR);
        callableStatement.registerOutParameter(15,Types.VARCHAR);
        callableStatement.registerOutParameter(16, Types.VARCHAR);
        callableStatement.registerOutParameter(17,Types.VARCHAR);
        callableStatement.registerOutParameter(18,Types.INTEGER);
        callableStatement.registerOutParameter(19,Types.VARCHAR);
        callableStatement.registerOutParameter(20,Types.INTEGER);
        callableStatement.registerOutParameter(21,Types.INTEGER);
        callableStatement.registerOutParameter(22, Types.INTEGER);
        callableStatement.executeUpdate();
        numdipl.setText(callableStatement.getString(2));
        String imageDipl = callableStatement.getString(3);
        numpiece.setText(callableStatement.getString(4));
        String imagePiece = callableStatement.getString(5);
        String photoCand = callableStatement.getString(6);
        nomcand.setText(callableStatement.getString(7));
        prenomCand.setText(callableStatement.getString(8));
        datenaiss.setText(callableStatement.getString(9));
        nationalite.setText(callableStatement.getString(10));
        methode.setText(callableStatement.getString(11));
        typecult.setText(callableStatement.getString(12));
        dipl.setText(callableStatement.getString(13));
        piece.setText(callableStatement.getString(14));
        String certifiPlant = callableStatement.getString(15);
        loacl.setText(callableStatement.getString(16));
        superf.setText(callableStatement.getString(17));
        int min = callableStatement.getInt(18);
        if (min == 1){
            mineur.setText("Oui");
        }else {
            mineur.setText("Non");
        }
        String certEmpl = callableStatement.getString(19);
        nbreempl.setText(String.valueOf(callableStatement.getInt(20)));
        nbrefemme.setText(String.valueOf(callableStatement.getInt(21)));
        salaire.setText(String.valueOf(callableStatement.getInt(22)));
        //AFFICHER DANS LE TABLEAU LES ELEMENTS
    }
    public void tableauBord(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        MenuChanger("menu.fxml");
    }

    public void ajouterCandidat(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        MenuChanger("Inscription1.fxml");
    }

    public void rendezVous(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
    }

    public void evaluationCafe(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
    }

    public void evaluationCacao(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
    }

    public void deliberationCafe(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
    }

    public void deliberationCacao(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
    }


    public void MenuChanger(String page){
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource("../view/"+page));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene2 = new Scene(parent);
        stage.setScene(scene2);
        stage.show();
    }

    public void valider(MouseEvent mouseEvent) throws SQLException {
        String accept = "{call VALIDATION(?,?)}";
        PreparedStatement preparedStatement = AgriConnexion.getInstance().prepareStatement(accept);
        int etat = 2;
        preparedStatement.setInt(1,etat);
        preparedStatement.setInt(2,dossierId);
        int resultat = preparedStatement.executeUpdate();
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        MenuChanger("menu.fxml");
    }

    public void rejeter(MouseEvent mouseEvent) throws SQLException {
        String rejet = "{call VALIDATION(?,?)}";
        PreparedStatement preparedStatement = AgriConnexion.getInstance().prepareStatement(rejet);
        int etat = 3;
        preparedStatement.setInt(1,etat);
        preparedStatement.setInt(2,dossierId);
        int resultat = preparedStatement.executeUpdate();
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        MenuChanger("menu.fxml");
    }
}
