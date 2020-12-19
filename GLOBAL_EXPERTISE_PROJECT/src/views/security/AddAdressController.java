/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.security;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import models.Adresse;
import models.Client;
import services.AddressService;
import services.BasicsService;

/**
 * FXML Controller class
 *
 * @author Albéric
 */
public class AddAdressController implements Initializable {

    @FXML
    private Label adressDetailsTextFieldLabel;
    @FXML
    private Label adressTownTextFieldLabel;
    @FXML
    private Label adressDistrictTextFieldLabel;
    @FXML
    private Label adressRoadTextFieldLabel;
    @FXML
    private JFXButton cancelBtn;
    @FXML
    private JFXButton addBtn;
    
    private BasicsService service;
    private AddressService addressService;
    @FXML
    private JFXTextField detailsTextField;
    @FXML
    private JFXTextField townTextField;
    @FXML
    private JFXTextField districtTextField;
    @FXML
    private JFXTextField roadTextField;
    @FXML
    private Label errorLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        service = new BasicsService();
    }    

    @FXML
    private void close(ActionEvent event) {
        service.getUtils().closeWindow(cancelBtn);
    }

    @FXML
    private void addNewAddress(ActionEvent event) {
        ListIterator<JFXTextField> li = new ArrayList<>(Arrays.asList(detailsTextField, roadTextField, districtTextField, townTextField)).listIterator();
        boolean result = false;
        while (li.hasNext()) {
            result = li.next().getText().isEmpty() || result;
        }
        if(result==true){
            errorLbl.setText("Veuillez remplir tous les champs");
        } else {
            service.getAdresseDao().add(new Adresse(detailsTextField.getText(), roadTextField.getText(), districtTextField.getText(), townTextField.getText(), (Client) ListUsersController.getCtrler().getUser()));
            ListUsersController.getCtrler().getOblAddressList().clear();
            ListUsersController.getCtrler().getOblAddressList().addAll(service.getAdresseDao().selectAllForOne(ListUsersController.getCtrler().getUser().getId()));
            errorLbl.setTextFill(Color.GREEN);
            errorLbl.setText("Ajout réussie");
            //service.getUtils().clearTextfields(new ArrayList<>(Arrays.asList(detailsTextField, roadTextField, districtTextField, townTextField)));
            while (li.hasNext()) {
                li.next().clear();
            }
            service.getUtils().closeWindow(cancelBtn);
        }
            
    }
    
}
