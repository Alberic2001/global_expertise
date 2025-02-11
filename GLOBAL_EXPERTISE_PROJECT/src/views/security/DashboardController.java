/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.security;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.AnchorPane;
import models.Employe;
import models.User;
import services.BasicsService;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author Albéric
 */
public class DashboardController implements Initializable {

    @FXML
    private Label connectedUserUsername;
    @FXML
    private Label connectedUserService;
    @FXML
    private JFXButton logingOutButton;

    
    private User connectedUser;
    private Utils utils;
    private BasicsService service;
    
    @FXML
    private AnchorPane adminFunctionsPane;
    @FXML
    private JFXButton categoriesAndProductsListing;
    @FXML
    private AnchorPane commandFunctionsPane;
    @FXML
    private JFXButton commandListingButton;
    @FXML
    private JFXButton commandEditButton;
    @FXML
    private JFXButton commandCancelButton;
    @FXML
    private JFXButton editAndPrintBillButton;
    @FXML
    private AnchorPane PaymentFunctionsPane1;
    @FXML
    private JFXButton establishFinalBill;
    @FXML
    private JFXButton archiveBillButton;
    @FXML
    private MenuBar menuBar;
    @FXML
    private AnchorPane viewLoader;
    @FXML
    private JFXButton categoryandProductCreateButton;
    @FXML
    private JFXButton listUsersButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new BasicsService();
        utils = new Utils();
        //Recuperation du User Connecté
        connectedUser=ConnexionController.getConnexion().getConnectedUser();
        connectedUserUsername.setText(connectedUser.getNom()+" "+connectedUser.getPrenom() );
        connectedUserService.setText(((Employe)connectedUser).getService());
        
        //getAutorisation();
        
    }    

    public void loadView(String viewName,AnchorPane anchorContent) throws IOException{
        AnchorPane viewLoader = FXMLLoader.load(getClass().getResource("./"+viewName+".fxml"));
            anchorContent.getChildren().clear();
            anchorContent.getChildren().add(viewLoader);
    }
    
    @FXML
    private void closeAppThenLaunchSignInView(ActionEvent event) throws IOException {
        service.disconnect(logingOutButton, "security/connexion");
        connectedUser = null;
    }

    private void loadUserCreationView(ActionEvent event) throws IOException {
        try {
            this.loadView("createuser", viewLoader);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void loadCategoryandProductCreationView(ActionEvent event) {
        try {
            this.loadView("../stock/createcategory", viewLoader);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void loadCategoriesAndProductsListingView(ActionEvent event) {
        try {
            this.loadView("../stock/listCategories", viewLoader);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    private void loadUsersListingView(ActionEvent event) {
        try {
            this.loadView("listUsers", viewLoader);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @FXML
    private void loadCommandListingView(ActionEvent event) {
        try {
            this.loadView("../command/listCommands", viewLoader);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadCommandEditView(ActionEvent event) {
        try {
            this.loadView("../command/editCommand", viewLoader);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void loadCommandCancellationView(ActionEvent event) {
    }

    @FXML
    private void loadEditAndPrintingBillView(ActionEvent event) {
    }

    @FXML
    private void loadEstablishFinalBillView(ActionEvent event) {
    }

    @FXML
    private void loadArchiveBillButtonView(ActionEvent event) {
    }

}
