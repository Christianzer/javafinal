package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import oracle.jdbc.OracleTypes;
import sample.database.AgriConnexion;
import sample.models.DossierPris;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class RdvControllers implements Initializable {

    public ObservableList<DossierPris> donnePrix;
    public TableView<DossierPris> tableauDossierPrix;
    public TableColumn<DossierPris,String> datanom;
    public TableColumn<DossierPris,String> dataprenoms;
    public TableColumn<DossierPris,String> datatypecult;
    public TableColumn<DossierPris,String> datardv;
    public TableColumn<DossierPris,Integer> datadossier;
    public TextField numdossier;
    public DatePicker dtrdv;
    ResultSet res = null;


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
        MenuChanger("connexionjury");
    }


    public void deliberationCafe(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
    }

    public void deliberationCacao(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
    }

    public void getDossierPrix() throws SQLException {
        this.donnePrix = FXCollections.observableArrayList();
        String query = "call AFFICHERDOSSIERPRIS(?)";
        CallableStatement call = AgriConnexion.getInstance().prepareCall(query);
        call.registerOutParameter(1, OracleTypes.CURSOR);
        call.executeUpdate();
        res = (ResultSet) call.getObject(1);
        while (res.next()){
            this.donnePrix.add(new DossierPris(res.getInt(1),res.getString(2),res.getString(3), res.getString(4), res.getString(5)));
        }

        this.datadossier.setCellValueFactory(new PropertyValueFactory<DossierPris,Integer>("dossierid"));
        this.datanom.setCellValueFactory(new PropertyValueFactory<DossierPris,String>("nomcand"));
        this.dataprenoms.setCellValueFactory(new PropertyValueFactory<DossierPris,String>("prenomsCand"));
        this.datardv.setCellValueFactory(new PropertyValueFactory<DossierPris,String>("typeculture"));
        this.datatypecult.setCellValueFactory(new PropertyValueFactory<DossierPris,String>("daterdv"));

        this.tableauDossierPrix.setItems(null);
        this.tableauDossierPrix.setItems(this.donnePrix);


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
        try {
            this.getDossierPrix();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        tableauDossierPrix.setOnMouseClicked(e->{
            DossierPris dossierPris = (DossierPris) tableauDossierPrix.getSelectionModel().getSelectedItem();
            int docapp = dossierPris.getDossierid();
            System.out.println(dossierPris.getDossierid());
            numdossier.setText(String.valueOf(docapp));
        });
    }

    public void valider(MouseEvent mouseEvent) throws SQLException {
        String query = "{call RENDEZVOUS(?,?)}";
        int dossiernumero = Integer.parseInt(numdossier.getText());
        String daterdv = dtrdv.getValue().toString();
        System.out.println(dossiernumero);
        PreparedStatement stat = AgriConnexion.getInstance().prepareCall(query);
        stat.setInt(1,dossiernumero);
        stat.setString(2,daterdv);
        int result = stat.executeUpdate();
        if (result == 1){
            numdossier.setText("");
            dtrdv.getEditor().clear();
            dtrdv.setValue(null);
            this.getDossierPrix();
        }
    }
}
