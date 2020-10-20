package sample.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.database.AgriConnexion;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;

public class ValidationDossierControllers implements Initializable {
    public Label numDossier;
    private int dossierId;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dossierId = Main.getDossierId();
        System.out.println(dossierId);
        numDossier.setText("DOSSIER NUMERO :"+dossierId);
        try {
            this.getAfficherDossier();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void getAfficherDossier() throws SQLException {
        String afficher = "{call SELECTDOSSIER(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        CallableStatement callableStatement = AgriConnexion.getInstance().prepareCall(afficher);
        callableStatement.setInt(1,dossierId);
        callableStatement.registerOutParameter(2,Types.VARCHAR);
        callableStatement.registerOutParameter(3,Types.VARCHAR);
        callableStatement.registerOutParameter(4, Types.BLOB);
        callableStatement.registerOutParameter(5,Types.VARCHAR);
        callableStatement.registerOutParameter(6,Types.VARCHAR);
        callableStatement.registerOutParameter(7,Types.BLOB);
        callableStatement.registerOutParameter(8,Types.VARCHAR);
        callableStatement.registerOutParameter(9,Types.VARCHAR);
        callableStatement.registerOutParameter(10, Types.VARCHAR);
        callableStatement.registerOutParameter(11,Types.VARCHAR);
        callableStatement.registerOutParameter(12,Types.VARCHAR);
        callableStatement.registerOutParameter(13, Types.VARCHAR);
        callableStatement.registerOutParameter(14,Types.VARCHAR);
        callableStatement.registerOutParameter(15,Types.BLOB);
        callableStatement.registerOutParameter(16, Types.VARCHAR);
        callableStatement.registerOutParameter(17,Types.VARCHAR);
        callableStatement.registerOutParameter(18,Types.VARCHAR);
        callableStatement.registerOutParameter(19,Types.BLOB);
        callableStatement.registerOutParameter(20,Types.INTEGER);
        callableStatement.registerOutParameter(21,Types.VARCHAR);
        callableStatement.registerOutParameter(22, Types.INTEGER);
        callableStatement.registerOutParameter(23,Types.INTEGER);
        callableStatement.registerOutParameter(24,Types.INTEGER);
        callableStatement.executeUpdate();

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

    public void valider(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        MenuChanger("menu.fxml");
    }

    public void rejeter(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        MenuChanger("menu.fxml");
    }
}
