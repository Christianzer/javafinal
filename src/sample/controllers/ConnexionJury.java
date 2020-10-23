package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Main;
import sample.database.AgriConnexion;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ConnexionJury implements Initializable {

    public TextField identifiant;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void connexion(ActionEvent actionEvent) throws SQLException {
        String ident = identifiant.getText();
        String query = "select * from JURY where IDENTIFIANT_JURY = ? ";
        PreparedStatement statement = AgriConnexion.getInstance().prepareStatement(query);
        statement.setString(1,ident);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            Main.setIdentifiantjury(ident);
            String nomJury = res.getString("NOM_JURY");
            Main.setNomjury(nomJury);
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            MenuChanger("TableauJury.fxml");
        }
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
}
