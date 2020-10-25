package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import sample.Main;
import sample.database.AgriConnexion;

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

public class EvaluationCafeControllers implements Initializable {

    public int dossiervaleur;

    public int idvisitedoc;

    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    public String datanote = format.format(date);

    @FXML
    private TextField espacearbre;

    @FXML
    private TextField etatarbre;

    @FXML
    private TextField proprete;

    @FXML
    private TextField fruit;

    @FXML
    private TextField cabosse;

    @FXML
    private TextField gout;

    @FXML
    private TextField qualite;

    @FXML
    private TextField humidite;

    @FXML
    private TextField sol;

    @FXML
    private TextField kantite;

    @FXML
    private TextField secahge;

    @FXML
    private TextField conversation;

    @FXML
    private TextField bomus;

    @FXML
    private ComboBox appreciation;

    @FXML
    private TextArea commentaire;

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
        System.out.println(Main.getIdvisite());
        System.out.println(Main.getDossiervisite());
        dossiervaleur = Main.getDossiervisite();
        idvisitedoc = Main.getIdvisite();
        appreciation.getItems().add("Mediocre");
        appreciation.getItems().add("Insuffisant");
        appreciation.getItems().add("Passable");
        appreciation.getItems().add("Bien");
        appreciation.getItems().add("Excellent");
    }

    public void validercafe(ActionEvent actionEvent) throws SQLException {
        float etarbre = Float.parseFloat(etatarbre.getText());
        float espace = Float.parseFloat(espacearbre.getText());
        float pro = Float.parseFloat(proprete.getText());
        float bo = Float.parseFloat(cabosse.getText());
        float fr = Float.parseFloat(fruit.getText());
        float gt = Float.parseFloat(gout.getText());
        float hum = Float.parseFloat(humidite.getText());
        float kalite = Float.parseFloat(qualite.getText());
        float prot = Float.parseFloat(sol.getText());
        float kant = Float.parseFloat(kantite.getText());
        float sech = Float.parseFloat(secahge.getText());
        float cons = Float.parseFloat(conversation.getText());
        float bon = Float.parseFloat(bomus.getText());
        String comment = commentaire.getText();
        String app = appreciation.getSelectionModel().getSelectedItem().toString();
        float moyen = etarbre + espace + pro + bo + fr + gt + hum + kalite + prot + kant + sech + cons + bon;
        float calcul = moyen/13 ;
        String updatevi = "{call UPDATEVISITE(?,?,?,?,?)}";
        PreparedStatement update = AgriConnexion.getInstance().prepareStatement(updatevi);
        update.setInt(1,idvisitedoc);
        update.setFloat(2,calcul);
        update.setString(3,datanote);
        update.setString(4,comment);
        update.setString(5,app);
        int resultupdate = update.executeUpdate();
        if (resultupdate == 1){

            String juryquery = "select count(ID_JURY) as nbrejury from JURY";
            Statement statement = AgriConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery(juryquery);
            resultSet.next();
            int nbreju = resultSet.getInt(1);
            String nbretes = "select count(etat) as nbretat,sum(moyenne_obtenue) as total from VISITE where ETAT = 2 and DOSSIER = ? GROUP BY DOSSIER";
            PreparedStatement sta = AgriConnexion.getInstance().prepareStatement(nbretes);
            sta.setInt(1,dossiervaleur);
            ResultSet resultSet1 = sta.executeQuery();
            resultSet1.next();
            int nbreetat = resultSet1.getInt(1);
            float moytotal = resultSet1.getFloat(2);
            if (nbreju == nbreetat){
                float moyenntotal = moytotal /nbreju;
                String res = "{call RESULTATFIN(?,?,?)}";
                PreparedStatement test = AgriConnexion.getInstance().prepareStatement(res);
                test.setFloat(1,moyenntotal);
                test.setInt(2,dossiervaleur);
                test.setString(3,datanote);
                int testres = test.executeUpdate();
                if (testres == 1){
                    System.out.println("ok");
                }else {
                    System.out.println("pas ok");
                }
            }

            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
            MenuChanger("TableauJury.fxml");
        }
    }
}
