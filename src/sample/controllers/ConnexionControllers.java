package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.AgriConnexion;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ConnexionControllers implements Initializable{
    @FXML
    private TextField identifiant;
    @FXML
    private PasswordField mdp;

    private String query;

    public void connexion(ActionEvent actionEvent) {
        if(!"".equals(identifiant.getText()) && !"".equals(mdp.getText())) {
            query = "SELECT * FROM USERS WHERE IDENTIFIANT ='" + identifiant.getText() + "' AND MOT_DE_PASSE='" + mdp.getText() + "'";
            try {
                Statement state = AgriConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = state.executeQuery(query);
                if (res.next()){
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    Parent parent = FXMLLoader.load(getClass().getResource("../view/menu.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("MENU");
                    stage.show();
                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Identifiant ou Mot de Passe incorrecte");
                    alert.showAndWait();
                }
            } catch (SQLException | IOException sqlException) {
                JOptionPane.showMessageDialog(null, sqlException.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Veuillez renseignez tous les champs");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
