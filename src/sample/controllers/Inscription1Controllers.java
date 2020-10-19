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
import javafx.scene.control.DatePicker;
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
import java.util.ResourceBundle;

public class Inscription1Controllers implements Initializable {
    public TextField contact;
    public TextField mail;

    private FileChooser fileChooser;
    private File filephoto;
    private File filePiece;
    private File fileDipl;
    private FileInputStream fileInputStreamPhoto;
    private FileInputStream fileInputStreamPiece;
    private FileInputStream fileInputStreamDipl;
    @FXML
    private Button photoCand;

    @FXML
    private Button imgPiece;

    @FXML
    private Button imgDip;

    @FXML
    private TextField nomCand;

    @FXML
    private TextField prenomsCand;

    @FXML
    private DatePicker dateCand;

    @FXML
    private TextField lieuCand;

    @FXML
    private TextField natioCand;

    @FXML
    private TextField numPieceCand;

    @FXML
    private ComboBox typePiece;


    @FXML
    private ComboBox typeDipCand;

    @FXML
    private TextField numDipCand;

    @FXML
    private TextField photoLib;

    @FXML
    private TextField libPiece;

    @FXML
    private TextField libDip;

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

    public void getTypePiece(){
        try{
            String query1 = "SELECT * FROM TYPE_PIECES ORDER BY ID_PIECE ASC ";
            Statement statement = AgriConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res1 = statement.executeQuery(query1);
            typePiece.getItems().add("");
            while (res1.next()){
                typePiece.getItems().add(res1.getString("libelle_piece"));
            }
            typePiece.getSelectionModel().select(14);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }

    }

    public void getTypeDiplome(){
        try{
            String query2 = "select * from TYPE_DIPLOMES order by ID_TYPE_DIPLOMES asc ";
            Statement statement =AgriConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res2 = statement.executeQuery(query2);
            typeDipCand.getItems().add("");
            while (res2.next()){
                typeDipCand.getItems().add(res2.getString("libelle_type_diplomes"));
            }
            typeDipCand.getSelectionModel().select(14);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public void AjoutPhoto(ActionEvent actionEvent) throws FileNotFoundException {
        filephoto = fileChooser.showOpenDialog(null);
        if (filephoto != null){
            photoLib.setText(filephoto.getAbsolutePath());
        }
        fileInputStreamPhoto = new FileInputStream(filephoto);
        System.out.println(fileInputStreamPhoto);
    }

    public void AjoutPiece(ActionEvent actionEvent) throws FileNotFoundException {
        filePiece = fileChooser.showOpenDialog(null);
        if (filePiece != null){
            libPiece.setText(filePiece.getAbsolutePath());
        }
        fileInputStreamPiece = new FileInputStream(filePiece);
        System.out.println(fileInputStreamPiece);
    }

    public void AjoutDipl(ActionEvent actionEvent) throws FileNotFoundException {
        fileDipl = fileChooser.showOpenDialog(null);
        if (fileDipl != null) {
            libDip.setText(fileDipl.getAbsolutePath());
        }
        fileInputStreamDipl = new FileInputStream(fileDipl);
        System.out.println(fileInputStreamDipl);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.getTypeDiplome();
        this.getTypePiece();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Tous les fichiers","*.*")
        );
    }

    public void suivant(MouseEvent mouseEvent) throws SQLException {
        if (!"".equals(nomCand.getText()) && !"".equals(prenomsCand.getText()) && !"".equals(dateCand.getValue()) && !"".equals(lieuCand.getText()) && !"".equals(contact.getText()) && !"".equals(mail.getText())&& !"".equals(numPieceCand.getText()) && !"".equals(numDipCand.getText())){
            Main.setMail(mail.getText());
            String query = "{call INSERTCAND(?,?,?,?,?,?,?,?)}";
            PreparedStatement preparedStatement =AgriConnexion.getInstance().prepareCall(query);
            if(!"0".equals(typePiece.getSelectionModel().getSelectedIndex()) && !"0".equals(typeDipCand.getSelectionModel().getSelectedIndex())) {
                preparedStatement.setBinaryStream(1,(InputStream)fileInputStreamPhoto,(int)filephoto.length());
                preparedStatement.setString(2,nomCand.getText());
                preparedStatement.setString(3,prenomsCand.getText());
                preparedStatement.setString(4,dateCand.getValue().toString());
                preparedStatement.setString(5,lieuCand.getText());
                preparedStatement.setString(6,natioCand.getText());
                preparedStatement.setString(7,contact.getText());
                preparedStatement.setString(8,mail.getText());
                int result =preparedStatement.executeUpdate();
                if(result == 1){
                    String query1 = "SELECT MAX(id_cand) as identifiant_cand FROM CANDIDATS";
                    Statement statement = AgriConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet resultSet = statement.executeQuery(query1);
                    resultSet.next();
                    int id_cand = Integer.parseInt(resultSet.getString("identifiant_cand"));
                    Main.setIdentifiant_cand(id_cand);
                    String queryPiece = "{call INSERTPIECE(?,?,?,?)}";
                    PreparedStatement preparedStatement1 = AgriConnexion.getInstance().prepareStatement(queryPiece);
                    preparedStatement1.setInt(1,typePiece.getSelectionModel().getSelectedIndex());
                    preparedStatement1.setInt(2,id_cand);
                    preparedStatement1.setString(3,numPieceCand.getText());
                    preparedStatement1.setBinaryStream(4,(InputStream)fileInputStreamPiece,(int)filePiece.length());
                    int resultatPiece = preparedStatement1.executeUpdate();
                    if (resultatPiece == 1){
                        String queryDiplome = "{call INSERTDIPLOME(?,?,?,?)}";
                        PreparedStatement preparedStatement2 = AgriConnexion.getInstance().prepareStatement(queryDiplome);
                        preparedStatement2.setInt(1,typeDipCand.getSelectionModel().getSelectedIndex());
                        preparedStatement2.setInt(2,id_cand);
                        preparedStatement2.setString(3,numDipCand.getText());
                        preparedStatement2.setBinaryStream(4,(InputStream)fileInputStreamDipl,(int)fileDipl.length());
                        int resultatDiplome = preparedStatement2.executeUpdate();
                        if (resultatDiplome == 1){
                            ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
                            MenuChanger("Inscription2.fxml");
                        }
                    }
                }
            }
        }

        /*

         */

   }


}
