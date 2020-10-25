package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.database.AgriConnexion;
import sample.models.DossierCacao;
import sample.models.DossierCacao;

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

public class RecompenseCacaoControllers implements Initializable {

    public TableView<DossierCacao> tableauCacao;
    public TableColumn<DossierCacao,Integer> dossiercacao;
    public TableColumn<DossierCacao,String> candidat;
    public TableColumn<DossierCacao,Double> moyg;
    public TableColumn<DossierCacao,Integer> classe;
    public TableColumn<DossierCacao,String> resultat;
    public ObservableList<DossierCacao> donnecacao;
    public TextField imgcacao;
    public TextField docnum;
    DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    Date date = new Date();
    public String daterecompcacao = format.format(date);
    private FileChooser fileChooser;
    private File filestickcacao;

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

    public void getDossierCacao() throws SQLException {
        String rep = "select RE.DOSSIER,C2.NOM_CANDIDAT,C2.PRENOM_CANDIDAT,RE.MOYENNEFINAL,RE.ETAT from RESULTATFINAL RE JOIN DOSSIER_INSCRIPTIONS DI on RE.DOSSIER = DI.DOSSIER JOIN CANDIDATS C2 on DI.ID_CAND = C2.ID_CAND JOIN AVOIR_CULTURES AC on DI.ID_PLANT = AC.ID_PLANT WHERE AC.ID_TYPE_CULT = 2 ORDER BY RE.MOYENNEFINAL DESC";
        this.donnecacao = FXCollections.observableArrayList();
        Statement statement = AgriConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet res2 = statement.executeQuery(rep);
        int num = 0;
        while (res2.next()){
            num = num+1;
            System.out.println(res2.getFloat(4));
            this.donnecacao.add(new DossierCacao(res2.getInt(1),res2.getString(2),res2.getString(3),res2.getDouble(4),num,res2.getInt(5)));
        }
        this.dossiercacao.setCellValueFactory(new PropertyValueFactory<DossierCacao,Integer>("docrecomp"));
        this.candidat.setCellValueFactory(new PropertyValueFactory<DossierCacao,String>("infocandidat"));
        this.moyg.setCellValueFactory(new PropertyValueFactory<DossierCacao, Double>("resultatMoye"));
        this.classe.setCellValueFactory(new PropertyValueFactory<DossierCacao,Integer>("classement"));
        this.resultat.setCellValueFactory(new PropertyValueFactory<DossierCacao,String>("resultat"));

        this.tableauCacao.setItems(null);
        this.tableauCacao.setItems(this.donnecacao);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            this.getDossierCacao();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images","*.png*")
        );
    }

    public void AjouterCacao(ActionEvent actionEvent) {
        filestickcacao = fileChooser.showOpenDialog(null);
        if (filestickcacao != null){
            imgcacao.setText(filestickcacao.getName());
        }
    }

    public void validation(ActionEvent actionEvent) throws SQLException {
        String query = "update RESULTATFINAL set ETAT = 2 ,STICKER = ? , DATE_RESULTAT = ? WHERE DOSSIER = ?";
        int numdossierid = Integer.parseInt(docnum.getText());
        PreparedStatement statement = AgriConnexion.getInstance().prepareStatement(query);
        statement.setString(1,filestickcacao.getName());
        statement.setString(2,daterecompcacao);
        statement.setInt(3,numdossierid);
        int result = statement.executeUpdate();
        if (result == 1){
            String recomp = "{call RECOMPENSEFINALCACAO(?)}";
            PreparedStatement rec = AgriConnexion.getInstance().prepareStatement(recomp);
            rec.setInt(1,numdossierid);
            int re = rec.executeUpdate();
            if (re == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Recompense delivre avec succes");
                alert.showAndWait();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                MenuChanger("recompensecacao.fxml");
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Dossier inexistant");
                alert.showAndWait();
            }
        }
    }
}
