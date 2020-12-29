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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Adresse;
import models.Client;
import models.User;
import services.BasicsService;

/**
 * FXML Controller class
 *
 * @author Albéric
 */
public class CreateClientController implements Initializable {

    private JFXTextField clientNumberOrMatriculeSearchTextField;
    @FXML
    private GridPane textFieldsGridPane;
    @FXML
    private JFXTextField nameTextField;
    @FXML
    private JFXTextField surnameTextField;
    @FXML
    private JFXTextField emailTextField;
    @FXML
    private JFXTextField numberTextField;
    @FXML
    private Label nameTextFieldLabel;
    @FXML
    private Label surnameTextFieldLabel;
    @FXML
    private Label emailTextFieldLabel;
    @FXML
    private Label numberTextFieldLabel;
    @FXML
    private Label adressRoadTextFieldLabel;
    @FXML
    private Label adressDistrictTextFieldLabel;
    @FXML
    private Label adressTownTextFieldLabel;
    @FXML
    private Label adressDetailsTextFieldLabel;
    @FXML
    private JFXTextField adressRoadTextField;
    @FXML
    private JFXTextField adressDistrictTextField;
    @FXML
    private JFXTextField adressTownTextField;
    @FXML
    private JFXTextField adressDetailsTextField;
    @FXML
    private Label creationSuccesOrFailureLabel;

    private static CreateClientController ctrler;
    private BasicsService service;
    private User existingUser;
    private User userToAdd;
    private Adresse addressToAdd;
    private User userToUpdate;
    private Adresse addressToUpdate;

    public static CreateClientController getCtrler() {
        return ctrler;
    }
    @FXML
    private JFXButton clientNumberSearchButton;
    @FXML
    private JFXButton createClientButton;
    @FXML
    private JFXButton updateClientButton;
    @FXML
    private JFXButton editBtn;
    @FXML
    private Label clientNumberTextFieldLabel;
    @FXML
    private JFXTextField clientNumberSearchTextField;
    @FXML
    private Label clientNumberSearchErrorLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrler = this;
        service = new BasicsService();
        existingUser = null;
        userToAdd = null;
        addressToAdd = null;
        userToUpdate = null;
        addressToUpdate = null;
    }

    public void fillFieldsWhenClientNumberExists(User user) {
        nameTextField.setText(user.getNom());
        surnameTextField.setText(user.getPrenom());
        emailTextField.setText(user.getEmail());
        numberTextField.setText(user.getTelephone());

        if (User.Type.CLIENT.equals(user.getType())) {
            adressRoadTextField.setText(service.getAdresseDao().selectAllForOne(user.getId()).get(0).getRue());
            adressDistrictTextField.setText(service.getAdresseDao().selectAllForOne(user.getId()).get(0).getQuartier());
            adressTownTextField.setText(service.getAdresseDao().selectAllForOne(user.getId()).get(0).getVille());
            adressDetailsTextField.setText(service.getAdresseDao().selectAllForOne(user.getId()).get(0).getDetails());
        }
    }

    public void clearAllTextFields() {
        service.getUtils().clearTextfields(new ArrayList<JFXTextField>(Arrays.asList(adressDetailsTextField, adressDetailsTextField, adressTownTextField, adressDistrictTextField, adressRoadTextField, numberTextField, clientNumberOrMatriculeSearchTextField, nameTextField, surnameTextField, emailTextField)));
    }

    public boolean validateAllTextFields() {
        if (nameTextField.getText().isEmpty()
                || surnameTextField.getText().isEmpty()
                || emailTextField.getText().isEmpty()
                || numberTextField.getText().isEmpty()
                || adressRoadTextField.getText().isEmpty()
                || adressDistrictTextField.getText().isEmpty()
                || adressTownTextField.getText().isEmpty()
                || adressDetailsTextField.getText().isEmpty() == true) {
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void validateClientNumberSearchTextField(ActionEvent event) {
        creationSuccesOrFailureLabel.setText("");
        if (clientNumberSearchTextField.getText().isEmpty()) {
            clientNumberSearchErrorLabel.setText("Remplissez le champ");
        } else {
            service.getUserDao().setTypeOfSelect("SQL_SELECT_ALL_CLIENTS");
            existingUser = service.printSpecificUser(clientNumberSearchTextField.getText());

            System.out.println(existingUser);
            if (existingUser != null) {
                fillFieldsWhenClientNumberExists(existingUser);
                textFieldsGridPane.setDisable(true);
                updateClientButton.setDisable(true);
                createClientButton.setDisable(true);
                creationSuccesOrFailureLabel.setText("");
            } else {
                clientNumberSearchTextField.clear();
                clearAllTextFields();
                textFieldsGridPane.setDisable(false);
                clientNumberSearchErrorLabel.setText("Utilsateur non créé, veuillez en créer un nouveau");
                createClientButton.setDisable(false);
            }
        }
    }

    @FXML
    private void createClient(ActionEvent event) {
        if (validateAllTextFields() == false) {
            userToAdd = new Client(nameTextField.getText(), surnameTextField.getText(), emailTextField.getText(), numberTextField.getText(), User.Type.CLIENT);
            addressToAdd = new Adresse(adressDetailsTextField.getText(), adressRoadTextField.getText(), adressDistrictTextField.getText(), adressTownTextField.getText());
            userToAdd = service.createUser(userToAdd, addressToAdd);
            System.out.println(userToAdd);
           if (userToAdd != null && userToAdd.getId() > 0) {
                System.out.println(userToAdd);
                creationSuccesOrFailureLabel.setTextFill(Color.GREEN);
                creationSuccesOrFailureLabel.setText("Création réussie");
            } else {
                creationSuccesOrFailureLabel.setTextFill(Color.RED);
                creationSuccesOrFailureLabel.setText("Création non réussie, veuillez réessayer");
            }
            //clearAllTextFields();
        } else {
            creationSuccesOrFailureLabel.setTextFill(Color.RED);
            creationSuccesOrFailureLabel.setText("Veuillez remplir tous les champs");
        }
    }

    @FXML
    private void updateClient(ActionEvent event) {
        if (validateAllTextFields() == false) {
            userToUpdate =  new Client(((Client)existingUser).getNumClient(), ((Client)existingUser).getId(), nameTextField.getText(), surnameTextField.getText(), emailTextField.getText(), numberTextField.getText(), User.Type.CLIENT);
            addressToUpdate = new Adresse(adressDetailsTextField.getText(), adressRoadTextField.getText(), adressDistrictTextField.getText(), adressTownTextField.getText());
            userToUpdate = service.updateUser(userToUpdate, addressToUpdate);
            System.out.println(userToUpdate);
            if (userToUpdate != null) {
                creationSuccesOrFailureLabel.setTextFill(Color.GREEN);
                creationSuccesOrFailureLabel.setText("Modification réussie");
                createClientButton.setDisable(false);
            } else {
                creationSuccesOrFailureLabel.setTextFill(Color.RED);
                creationSuccesOrFailureLabel.setText("Modification non réussie, veuillez réessayer");
                createClientButton.setDisable(false);
            }
            //clearAllTextFields();
        } else {
            creationSuccesOrFailureLabel.setTextFill(Color.RED);
            creationSuccesOrFailureLabel.setText("Veuillez remplir tous les champs");
        }
    }

    @FXML
    private void enableTextfieldsGridPaneAndUpdateClientButton(ActionEvent event) {
        textFieldsGridPane.setDisable(false);
        updateClientButton.setDisable(false);
        createClientButton.setDisable(true);
    }

}
