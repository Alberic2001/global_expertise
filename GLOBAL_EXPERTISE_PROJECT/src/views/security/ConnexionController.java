package views.security;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.User;
import services.BasicsService;
import utils.Utils;

/**
 *
 * @author Albéric
 */
public class ConnexionController implements Initializable {
    
    private Label label;
    @FXML
    private Label errorLabeledText;
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXPasswordField passwordTextField;
    @FXML
    private JFXButton signingInButton;
    @FXML
    private ImageView closeButton;
    
    private BasicsService service;
    private Utils utils;
    private User connectedUser;
    private static ConnexionController connexion;
    
    
    public static ConnexionController getConnexion() {
        return connexion;
    }

    public  User getConnectedUser() {
        return connectedUser;
    }
    
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new BasicsService();
        utils = new Utils();
        connexion = this;
    }    


    @FXML
    private void closeApplication(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    private void validateConnexionThenLaunchApp(ActionEvent event) {
        if(!utils.validateFields(usernameTextField, passwordTextField).equals("")){
            errorLabeledText.setText(utils.validateFields(usernameTextField, passwordTextField));
        } else {
            connectedUser = service.getUserDao().selectUserByloginAndPassword(usernameTextField.getText(), passwordTextField.getText());
            if(connectedUser!=null){
                this.loadDashboard();
            } else {
                errorLabeledText.setText("Username ou password incorrect");
            }
        }
        System.out.println(errorLabeledText);
    }
    
    private void loadDashboard(){
        System.out.println(connectedUser.toString());
        Stage stage = (Stage) signingInButton.getScene().getWindow(); 
        stage.close();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
            Scene scene = new Scene(root);
            stage=new Stage();
            stage.setTitle("Transfert Argent");
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ConnexionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
