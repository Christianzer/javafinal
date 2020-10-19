package sample.controllers;

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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.getTypeDiplome();
        this.getTypePiece();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Tous les fichiers","*.*")
        );
        photoCand.setOnAction(actionEvent -> {
            filephoto = fileChooser.showOpenDialog(null);
            if (filephoto != null){
                photoLib.setText(filephoto.getAbsolutePath());
            }
        });
        imgDip.setOnAction(actionEvent -> {
            fileDipl = fileChooser.showOpenDialog(null);
            if (fileDipl != null){
                libDip.setText(fileDipl.getAbsolutePath());
            }
        });
        imgPiece.setOnAction(actionEvent -> {
            filePiece = fileChooser.showOpenDialog(null);
            if (filePiece != null){
                libPiece.setText(filePiece.getAbsolutePath());
            }
        });

    }

    public void suivant(MouseEvent mouseEvent) throws SQLException, FileNotFoundException {
        fileInputStreamPhoto = new FileInputStream(filephoto);
        fileInputStreamDipl = new FileInputStream(fileDipl);
        fileInputStreamPiece = new FileInputStream(filePiece);

        /*
        if (!"".equals(nomCand.getText()) && !"".equals(prenomsCand.getText()) && !"".equals(dateCand.getValue()) && !"".equals(lieuCand.getText()) && !"".equals(contact.getText()) && !"".equals(mail.getText())&& !"".equals(numPieceCand.getText()) && !"".equals(numDipCand.getText())){
            Main.setMail(mail.getText());
            String query = "{call INSERTCAND(?,?,?,?,?,?,?,?)}";
            PreparedStatement preparedStatement =AgriConnexion.getInstance().prepareCall(query);
            if(!"0".equals(typePiece.getSelectionModel().getSelectedIndex()) && !"0".equals(typeDipCand.getSelectionModel().getSelectedIndex())) {


            }
        }
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
        MenuChanger("Inscription2.fxml");

         */
    }
}
