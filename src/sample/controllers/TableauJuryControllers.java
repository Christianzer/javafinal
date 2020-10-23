package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import oracle.jdbc.OracleTypes;
import sample.Main;
import sample.database.AgriConnexion;
import sample.models.DossierPris;
import sample.models.DossierVisiter;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableauJuryControllers implements Initializable {

    public ObservableList<DossierVisiter> donneJury;
    public TableView<DossierVisiter> tableauDossierJury;
    public TableColumn<DossierVisiter,Integer> datadossier;
    public TableColumn<DossierVisiter, String> datacandidat;
    public TableColumn<DossierVisiter,String> dataculture;
    public TableColumn<DossierVisiter,Float> datatresultat;
    public Label nomjury;
    private String idenjury;
    public int idvisite;
    public int idtype;
    ResultSet res;


    public void getDossierVisite() throws SQLException {
        this.donneJury = FXCollections.observableArrayList();
        idenjury = Main.getIdentifiantjury();
        System.out.println(idenjury);
        String query = "call afficherdossiervisite(?,?)";
        CallableStatement call = AgriConnexion.getInstance().prepareCall(query);
        call.setString(1,idenjury);
        call.registerOutParameter(2, OracleTypes.CURSOR);
        call.executeUpdate();
        res = (ResultSet) call.getObject(2);
        while (res.next()){
            this.donneJury.add(new DossierVisiter(
                    res.getInt(1),res.getInt(2),res.getString(3),res.getString(4),res.getFloat(5),res.getInt(6),res.getString(7)
            ));
            this.datadossier.setCellValueFactory(new PropertyValueFactory<DossierVisiter,Integer>("dossiervisiteid"));
            this.datacandidat.setCellValueFactory(new PropertyValueFactory<DossierVisiter,String>("candidat"));
            this.dataculture.setCellValueFactory(new PropertyValueFactory<DossierVisiter,String>("culturevisite"));
            this.datatresultat.setCellValueFactory(new PropertyValueFactory<DossierVisiter,Float>("resultvisite"));
            this.tableauDossierJury.setItems(null);
            this.tableauDossierJury.setItems(this.donneJury);


        }



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
        MenuChanger("rendezvous.fxml");
    }

    public void evaluationCafe(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        MenuChanger("connexionjury.fxml");
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nomjury.setText("JURY "+Main.getNomjury());
        try {
            this.getDossierVisite();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        tableauDossierJury.setOnMouseClicked(e->{
            DossierVisiter dossierVisiter = (DossierVisiter) tableauDossierJury.getSelectionModel().getSelectedItem();
            int docapp = dossierVisiter.getVisiteid();
            Main.setIdvisite(docapp);
            idtype = dossierVisiter.getTypevisite();
        });
    }

    public void evaluation(ActionEvent actionEvent) {
        if (idtype == 1){
            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            MenuChanger("evaluationcafe.fxml");
        }else {
            MenuChanger("evaluationcacao.fxml");
        }
    }
}
