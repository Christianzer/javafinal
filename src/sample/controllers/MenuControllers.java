package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.database.AgriConnexion;
import sample.models.DossierInscription;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class MenuControllers implements Initializable {

    public BorderPane border;
    public ObservableList<DossierInscription> donneIns;
    public TableView<DossierInscription> tableauDossier;
    public TableColumn<DossierInscription,Integer> datadossier;
    public TableColumn<DossierInscription,String> dataprenoms;
    public TableColumn<DossierInscription,String> datanom;
    public TableColumn<DossierInscription,String> datatypecult;
    public TableColumn<DossierInscription,String> dataetat;


    private void getDossier() throws SQLException {
        String query = "SELECT D.DOSSIER,C2.NOM_CANDIDAT,C2.PRENOM_CANDIDAT,TY.LIBELLE_TYPE_CULTURES ,E.LIBELLE_ETAT FROM DOSSIER_INSCRIPTIONS D JOIN CANDIDATS C2 on D.ID_CAND = C2.ID_CAND JOIN ETATS E on E.ID_TABLE = D.VALIDATION\n" +
                "JOIN AVOIR_CULTURES AC on D.ID_PLANT = AC.ID_PLANT JOIN TYPE_CULTURES TY ON TY.ID_TYPE_CULTURES = AC.ID_TYPE_CULT ORDER BY VALIDATION ASC ";
        this.donneIns = FXCollections.observableArrayList();
        Statement statement = AgriConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet res2 = statement.executeQuery(query);
        while (res2.next()){
            this.donneIns.add(new DossierInscription
                    (res2.getInt(1),res2.getString(2),
                            res2.getString(3),res2.getString(4),res2.getString(5)));
        }
        this.datadossier.setCellValueFactory(new PropertyValueFactory<DossierInscription,Integer>("dossier"));
        this.datanom.setCellValueFactory(new PropertyValueFactory<DossierInscription,String>("nom"));
        this.dataprenoms.setCellValueFactory(new PropertyValueFactory<DossierInscription,String>("prenoms"));
        this.datatypecult.setCellValueFactory(new PropertyValueFactory<DossierInscription,String>("typecult"));
        this.dataetat.setCellValueFactory(new PropertyValueFactory<DossierInscription,String>("etat"));

        this.tableauDossier.setItems(null);
        this.tableauDossier.setItems(this.donneIns);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getDossier();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public void tableauBord(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        MenuChanger("menu.fxml");
    }

    public void ajouterCandidat(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
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

    public void fermer(MouseEvent mouseEvent) {
        Stage stage = (Stage) border.getScene().getWindow();
        stage.close();
    }

    private void MenuChanger(String page){
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