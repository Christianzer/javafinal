package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;
import sample.database.AgriConnexion;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class Inscription2Controllers implements Initializable {

    private FileChooser fileChooser;
    private File filePlant;
    private File fileEmpl;
    private FileInputStream fileInputStreamPlant;
    private FileInputStream fileInputStreamEmpl;
    private int id_candi;

    private int id_plant;

    private String email;

    @FXML
    private Button ImgPlantation;

    @FXML
    private Button certEmploye;

    @FXML
    private TextField localisation;

    @FXML
    private TextField superficie;

    @FXML
    private TextField nombreEmpl;

    @FXML
    private TextField salaireMax;

    @FXML
    private ComboBox mineur;

    @FXML
    private TextField libPlantation;

    @FXML
    private TextField libcertEmpl;

    @FXML
    private ComboBox methode;

    @FXML
    private ComboBox typeCulture;

    @FXML
    private TextField nbreFemme;



    public void getCulture(){
        try{
            String query1 = "select * from TYPE_CULTURES order by ID_TYPE_CULTURES ASC ";
            Statement statement = AgriConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res1 = statement.executeQuery(query1);
            typeCulture.getItems().add("");
            while (res1.next()){
                typeCulture.getItems().add(res1.getString("libelle_type_cultures"));
            }
            typeCulture.getSelectionModel().select(14);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public void getMethod(){
        try{
            String query1 = "select * from METHODE_CULTURES order by ID_METHODES_CULTURES ASC";
            Statement statement =AgriConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res1 = statement.executeQuery(query1);
            methode.getItems().add("");
            while (res1.next()){
                methode.getItems().add(res1.getString("libelle_methodes_cultures"));
            }
            methode.getSelectionModel().select(14);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public static String getRandomStr(int n)
    {
        //choisissez un caractére au hasard à partir de cette chaîne
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder s = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(str.length() * Math.random());
            s.append(str.charAt(index));
        }
        return s.toString();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.getCulture();
        this.getMethod();
        id_candi = Main.getIdentifiant_cand();
        id_plant = Main.getIdentifiant_plant();
        email = Main.getMail();
        mineur.getItems().add("");
        mineur.getItems().add("OUI");
        mineur.getItems().add("NON");
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Tous les fichiers","*.*")
        );
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


    public void enregistrer(MouseEvent mouseEvent) throws SQLException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String datainscription = format.format(date);
        if (!"".equals(localisation.getText()) && !"".equals(superficie.getText()) && !"".equals(localisation.getText()) && !"0".equals(typeCulture.getSelectionModel().getSelectedIndex()) && !"0".equals(methode.getSelectionModel().getSelectedIndex())){
            if (!"".equals(nombreEmpl.getText()) && !"".equals(nbreFemme.getText()) && !"".equals(salaireMax.getText()) && !"0".equals(mineur.getSelectionModel().getSelectedIndex())) {
                String plant = "{call INSERTPLANT(?,?,?)}";
                PreparedStatement preparedStatement = AgriConnexion.getInstance().prepareStatement(plant);
                preparedStatement.setString(1,localisation.getText());
                preparedStatement.setString(2,superficie.getText());
                preparedStatement.setBinaryStream(3,(InputStream)fileInputStreamPlant,(int)filePlant.length());
                int resultat = preparedStatement.executeUpdate();
                if (resultat == 1){
                    String query1 = "SELECT MAX(ID_PLANT) AS IDENTIFIANT_PLANT FROM PLANTATION_CANDIDATS";
                    Statement statement = AgriConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet resultSet = statement.executeQuery(query1);
                    resultSet.next();
                    int id_plant = Integer.parseInt(resultSet.getString("identifiant_plant"));
                    String culture = "{call INSERTCULTURE(?,?)}";
                    PreparedStatement preparedStatement1 = AgriConnexion.getInstance().prepareStatement(culture);
                    preparedStatement1.setInt(1,id_plant);
                    preparedStatement1.setInt(2,typeCulture.getSelectionModel().getSelectedIndex());
                    int resultat1 = preparedStatement1.executeUpdate();
                    if(resultat1 == 1){
                        String methodeCult = "{call INSERTMETHODE(?,?)}";
                        PreparedStatement preparedStatement2 = AgriConnexion.getInstance().prepareStatement(methodeCult);
                        preparedStatement2.setInt(1,id_plant);
                        preparedStatement2.setInt(2,methode.getSelectionModel().getSelectedIndex());
                        int resultat2 = preparedStatement2.executeUpdate();
                        if(resultat2 == 1){
                            int nbreEmp = Integer.parseInt(nombreEmpl.getText());
                            int nbreFem = Integer.parseInt(nbreFemme.getText());
                            int sal = Integer.parseInt(salaireMax.getText());
                            String password = getRandomStr(6);
                        }
                    }
                }
            }
        }
            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        MenuChanger("FinInscription.fxml");
    }

    public void AjouterCertPlant(ActionEvent actionEvent) throws FileNotFoundException {
        filePlant = fileChooser.showOpenDialog(null);
        if (filePlant != null){
            libPlantation.setText(filePlant.getAbsolutePath());
        }
        fileInputStreamPlant = new FileInputStream(filePlant);
        System.out.println(fileInputStreamPlant);
    }

    public void AjoutEmpl(ActionEvent actionEvent) throws FileNotFoundException {
        fileEmpl = fileChooser.showOpenDialog(null);
        if (fileEmpl != null){
            libcertEmpl.setText(fileEmpl.getAbsolutePath());
        }
        fileInputStreamEmpl = new FileInputStream(fileEmpl);
        System.out.println(fileInputStreamEmpl);
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

}
