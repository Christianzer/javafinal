package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.database.AgriConnexion;
import sample.models.DossierCafe;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class RecompenseCafeControllers implements Initializable {


    public TableView<DossierCafe> tableaurecomp;
    public ObservableList<DossierCafe> donnerecomp;
    public TableColumn<DossierCafe,Integer> recompdossier;
    public TableColumn<DossierCafe,String> recompnom;
    public TableColumn<DossierCafe,Float> recompmoy;
    public TableColumn<DossierCafe,Integer> recompclass;
    public TableColumn<DossierCafe,String> recompresultat;
    public TextField numdoc;
    public TextField imglib;
    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    public String daterecomp = format.format(date);
    private FileChooser fileChooser;
    private File filestick;

    public void getRecompense() throws SQLException {
        String rep = "select RE.DOSSIER,C2.NOM_CANDIDAT,C2.PRENOM_CANDIDAT,RE.MOYENNEFINAL,RE.ETAT from RESULTATFINAL RE JOIN DOSSIER_INSCRIPTIONS DI on RE.DOSSIER = DI.DOSSIER JOIN CANDIDATS C2 on DI.ID_CAND = C2.ID_CAND JOIN AVOIR_CULTURES AC on DI.ID_PLANT = AC.ID_PLANT WHERE AC.ID_TYPE_CULT = 1 ORDER BY RE.MOYENNEFINAL DESC";
        this.donnerecomp = FXCollections.observableArrayList();
        Statement statement = AgriConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet res2 = statement.executeQuery(rep);
        int num = 0;
        while (res2.next()){
            num = num+1;
            System.out.println(res2.getFloat(4));
            this.donnerecomp.add(new DossierCafe(res2.getInt(1),res2.getString(2),res2.getString(3),res2.getDouble(4),num,res2.getInt(5)));
        }
        this.recompdossier.setCellValueFactory(new PropertyValueFactory<DossierCafe,Integer>("docrecomp"));
        this.recompnom.setCellValueFactory(new PropertyValueFactory<DossierCafe,String>("infocandidat"));
        this.recompmoy.setCellValueFactory(new PropertyValueFactory<DossierCafe, Float>("resultatMoye"));
        this.recompclass.setCellValueFactory(new PropertyValueFactory<DossierCafe,Integer>("classement"));
        this.recompresultat.setCellValueFactory(new PropertyValueFactory<DossierCafe,String>("resultat"));

        this.tableaurecomp.setItems(null);
        this.tableaurecomp.setItems(this.donnerecomp);
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
        MenuChanger("recompensecafe.fxml");
    }

    public void deliberationCacao(MouseEvent mouseEvent) {
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        MenuChanger("recompensecacao.fxml");
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
            this.getRecompense();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images","*.png*")
        );
    }

    public void valider(ActionEvent actionEvent) throws SQLException {
        String query = "update RESULTATFINAL set ETAT = 2 ,STICKER = ? , DATE_RESULTAT = ? WHERE DOSSIER = ?";
        int numdossierid = Integer.parseInt(numdoc.getText());
        PreparedStatement statement = AgriConnexion.getInstance().prepareStatement(query);
        statement.setString(1,filestick.getName());
        statement.setString(2,daterecomp);
        statement.setInt(3,numdossierid);
        int result = statement.executeUpdate();
        if (result == 1){
            String recomp = "{}";
        }
    }

    public void Ajoutersticker(ActionEvent actionEvent) {
        filestick = fileChooser.showOpenDialog(null);
        if (filestick != null){
            imglib.setText(filestick.getName());
        }
    }
}
