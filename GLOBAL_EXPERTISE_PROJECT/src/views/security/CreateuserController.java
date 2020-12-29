/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.security;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Adresse;
import models.Employe;
import models.Client;
import models.User;
import models.User.Type;
import services.BasicsService;

/**
 * FXML Controller class
 *
 * @author Albéric
 */
public class CreateuserController implements Initializable {

    @FXML
    private AnchorPane viewLoadedPane;
    @FXML
    private Label clientNumberOrMatriculeSearchTextFieldLabel;
    @FXML
    private JFXTextField clientNumberOrMatriculeSearchTextField;
    @FXML
    private JFXButton clientNumberOrMatriculeSearchButton;
    @FXML
    private JFXTextField nameTextField;
    @FXML
    private JFXTextField emailTextField;
    @FXML
    private JFXTextField numberTextField;
    @FXML
    private JFXTextField serviceTextField;
    @FXML
    private JFXTextField loginTextField;
    @FXML
    private Label nameTextFieldLabel;
    @FXML
    private Label surnameTextFieldLabel;
    @FXML
    private Label emailTextFieldLabel;
    @FXML
    private Label numberTextFieldLabel;
    @FXML
    private Label serviceTextFieldLabel;
    @FXML
    private Label loginTextFieldLabel;
    @FXML
    private Label passwordTextFieldLabel;
    @FXML
    private JFXPasswordField passwordTextField;
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
    private Label creationSuccesOrFailureLabel;
    @FXML
    private JFXButton createUserButton;
    @FXML
    private JFXButton modifyUserButton;
    @FXML
    private JFXToggleButton userChoiceToggleButton;
    
    private static CreateuserController ctrler;
    private BasicsService service;
    private User existingUser;
    private User userToAdd;
    private Adresse addressToAdd;
    private User userToUpdate;
    private Adresse addressToUpdate;
    @FXML
    private JFXTextField adressDetailsTextField;
    @FXML
    private Label clientNumberOrMatriculeSearchErrorLabel;
    @FXML
    private JFXTextField surnameTextField;
    @FXML
    private JFXButton updateUserButton;
    @FXML
    private GridPane textFieldsGridPane;
    
    
    public static CreateuserController getCtrler() {
        return ctrler;
    }

    public JFXToggleButton getUserChoiceToggleButton() {
        return userChoiceToggleButton;
    }
    
    
    
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
    
    @FXML
    public void setVisibleOrNotEmployeFields(){
        if(userChoiceToggleButton.isSelected()){
            makeClientAdressFieldsVisibleOrNot(false);
            makeEmployeFieldsVisibleOrNot(true);
        } else {
            makeClientAdressFieldsVisibleOrNot(true);
            makeEmployeFieldsVisibleOrNot(false);
        }
        textFieldsGridPane.setDisable(false);
        updateUserButton.setDisable(false);
        createUserButton.setDisable(false);
        clearAllTextFields();
        creationSuccesOrFailureLabel.setText("");
    }
    
    public void makeEmployeFieldsVisibleOrNot(boolean value){
        serviceTextFieldLabel.setVisible(value);
        serviceTextField.setVisible(value);
        loginTextFieldLabel.setVisible(value);
        loginTextField.setVisible(value);
        passwordTextFieldLabel.setVisible(value);
        passwordTextField.setVisible(value);
        if(value=true) {
            clientNumberOrMatriculeSearchTextFieldLabel.setText("Matricule");
        } else {
            clientNumberOrMatriculeSearchTextFieldLabel.setText("Numéro du client");
        }
    }
    
    public void makeClientAdressFieldsVisibleOrNot(boolean value){
        adressRoadTextFieldLabel.setVisible(value);
        adressRoadTextField.setVisible(value);
        adressDistrictTextFieldLabel.setVisible(value);
        adressDistrictTextField.setVisible(value);
        adressTownTextFieldLabel.setVisible(value);
        adressTownTextField.setVisible(value);
        adressDetailsTextFieldLabel.setVisible(value);
        adressDetailsTextField.setVisible(value);
        if(value==true){
            clientNumberOrMatriculeSearchTextFieldLabel.setText("Numéro du client");
        } else {
            clientNumberOrMatriculeSearchTextFieldLabel.setText("Matricule");
        }
    }
    
    public void fillFieldsWhenClientNumberOrMatriculeExists(User user){
        nameTextField.setText(user.getNom());
        surnameTextField.setText(user.getPrenom());
        emailTextField.setText(user.getEmail());
        numberTextField.setText(user.getTelephone());

        if(User.Type.CLIENT.equals(user.getType())){
            adressRoadTextField.setText(service.getAdresseDao().selectAllForOne(user.getId()).get(0).getRue());
            adressDistrictTextField.setText(service.getAdresseDao().selectAllForOne(user.getId()).get(0).getQuartier());
            adressTownTextField.setText(service.getAdresseDao().selectAllForOne(user.getId()).get(0).getVille());
            adressDetailsTextField.setText(service.getAdresseDao().selectAllForOne(user.getId()).get(0).getDetails());
        }
        if(User.Type.EMPLOYE.equals(user.getType())){
            serviceTextField.setText(((Employe)user).getService());
            loginTextField.setText(((Employe)user).getLogin());
            passwordTextField.setText(((Employe)user).getPassword());
        }
    }
    
    public void clearAllTextFields(){
        service.getUtils().clearTextfields(new ArrayList<JFXTextField>(Arrays.asList(loginTextField, serviceTextField, adressDetailsTextField, adressDetailsTextField, adressTownTextField, adressDistrictTextField, adressRoadTextField, numberTextField, clientNumberOrMatriculeSearchTextField, nameTextField, surnameTextField, emailTextField)));
        passwordTextField.clear();
    }
    
    @FXML
    public void validateClientNumberOrMatriculeSearchTextField(){
        creationSuccesOrFailureLabel.setText("");
        if(clientNumberOrMatriculeSearchTextField.getText().isEmpty()){
            clientNumberOrMatriculeSearchErrorLabel.setText("Remplissez le champ");
        } else {
            if(userChoiceToggleButton.isSelected()){
                //existingUser = service.getUserDao().selectOneEmployeByMatricule(clientNumberOrMatriculeSearchTextField.getText());
                service.getUserDao().setTypeOfSelect("SQL_SELECT_ALL_EMPLOYES");
                existingUser = service.printSpecificUser(clientNumberOrMatriculeSearchTextField.getText());
            } else {
                //existingUser = service.getUserDao().selectOneClientByNumClient(clientNumberOrMatriculeSearchTextField.getText());
                service.getUserDao().setTypeOfSelect("SQL_SELECT_ALL_CLIENTS");
                existingUser = service.printSpecificUser(clientNumberOrMatriculeSearchTextField.getText());
            }
            System.out.println(existingUser);
            if(existingUser!=null){
                fillFieldsWhenClientNumberOrMatriculeExists(existingUser);
                textFieldsGridPane.setDisable(true);
                updateUserButton.setDisable(true);
                createUserButton.setDisable(true);
                creationSuccesOrFailureLabel.setText("");
            } else {
                clientNumberOrMatriculeSearchTextField.clear();
                clearAllTextFields();
                textFieldsGridPane.setDisable(false);
                clientNumberOrMatriculeSearchErrorLabel.setText("Utilsateur non créé, veuillez en créer un nouveau");
                createUserButton.setDisable(false);
            }
        }
    }
    
    public boolean validateAllTextFields(){
        if(nameTextField.getText().isEmpty() 
           || surnameTextField.getText().isEmpty() 
           || emailTextField.getText().isEmpty() 
           || numberTextField.getText().isEmpty() 
           || adressRoadTextField.getText().isEmpty()
           || adressDistrictTextField.getText().isEmpty()
           || adressTownTextField.getText().isEmpty()
           || adressDetailsTextField.getText().isEmpty()
           || loginTextField.getText().isEmpty()
           || passwordTextField.getText().isEmpty()
           || serviceTextField.getText().isEmpty() == true){
            return true;
        } else {
            return false;
        }
    }
    @FXML
    public void enableTextfieldsGridPaneAndUpdateUserButton(){
        textFieldsGridPane.setDisable(false);
        updateUserButton.setDisable(false);
        createUserButton.setDisable(true);
    }

    @FXML
    private void createUser(ActionEvent event) {
        if(validateAllTextFields() == false){
            if(userChoiceToggleButton.isSelected()){
                userToAdd = new Employe(loginTextField.getText(), passwordTextField.getText(), serviceTextField.getText(), nameTextField.getText(), surnameTextField.getText(), emailTextField.getText(), numberTextField.getText(), Type.EMPLOYE);
            } else {
                userToAdd = new Client(nameTextField.getText(), surnameTextField.getText(), emailTextField.getText(), numberTextField.getText(), Type.CLIENT);
                addressToAdd = new Adresse(adressDetailsTextField.getText(), adressRoadTextField.getText(), adressDistrictTextField.getText(), adressTownTextField.getText());
            }
            userToAdd = service.createUser(userToAdd, addressToAdd);
            if(userToAdd!=null && userToAdd.getId()>0){
                System.out.println(userToAdd);
                creationSuccesOrFailureLabel.setTextFill(Color.GREEN);
                creationSuccesOrFailureLabel.setText("Création réussie");
            } else {
                creationSuccesOrFailureLabel.setTextFill(Color.RED);
                 creationSuccesOrFailureLabel.setText("Création non réussie, veuillez réessayer");
            }
            clearAllTextFields();
        } else {
            creationSuccesOrFailureLabel.setTextFill(Color.RED);
            creationSuccesOrFailureLabel.setText("Veuillez remplir tous les champs");
        }
    }

    @FXML
    private void updateUser(ActionEvent event) {
        if(validateAllTextFields() == true){
            userToUpdate = existingUser;
            if(userChoiceToggleButton.isSelected()){
                userToUpdate = new Employe(loginTextField.getText(), passwordTextField.getText(), ((Employe)userToUpdate).getMatricule(), serviceTextField.getText(), userToUpdate.getId(), nameTextField.getText(), surnameTextField.getText(), emailTextField.getText(), numberTextField.getText(), Type.EMPLOYE);
            } else {
                userToUpdate = new Client(((Client)userToUpdate).getNumClient(), ((Client)userToUpdate).getId(), nameTextField.getText(), surnameTextField.getText(), emailTextField.getText(), numberTextField.getText(), Type.CLIENT);
                addressToUpdate = new Adresse(adressDetailsTextField.getText(), adressRoadTextField.getText(), adressDistrictTextField.getText(), adressTownTextField.getText());
            }
            userToUpdate = service.updateUser(userToUpdate, addressToUpdate);
            if(userToUpdate!=null){
                creationSuccesOrFailureLabel.setTextFill(Color.GREEN);
                creationSuccesOrFailureLabel.setText("Modification réussie");
                createUserButton.setDisable(false);
            } else {
                creationSuccesOrFailureLabel.setTextFill(Color.RED);
                creationSuccesOrFailureLabel.setText("Modification non réussie, veuillez réessayer");
                createUserButton.setDisable(false);
            }
            clearAllTextFields();
        } else {
            creationSuccesOrFailureLabel.setTextFill(Color.RED);
            creationSuccesOrFailureLabel.setText("Veuillez remplir tous les champs");
        }
    }
    

    
    
}
