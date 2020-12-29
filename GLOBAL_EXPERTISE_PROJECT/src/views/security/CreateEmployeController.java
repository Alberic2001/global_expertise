/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.security;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Adresse;
import models.Client;
import models.Employe;
import models.User;
import services.BasicsService;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author Albéric
 */
public class CreateEmployeController implements Initializable {

    @FXML
    private Label clientNumberOrMatriculeSearchTextFieldLabel;
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
    private JFXTextField loginTextField;
    @FXML
    private JFXPasswordField passwordTextField;
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
    private Label creationSuccesOrFailureLabel;
    @FXML
    private JFXTextField matriculeSearchTextField;
    @FXML
    private JFXButton matriculeSearchButton;
    @FXML
    private JFXComboBox<String> serviceComb;
    @FXML
    private JFXButton createEmployeeButton;
    @FXML
    private JFXButton updateEmployeeButton;
    @FXML
    private Label matriculeSearchErrorLabel;
    @FXML
    private JFXButton modifyEmployeeButton;
    
    
    private static CreateEmployeController ctrler;
    private BasicsService service;
    private User existingUser;
    private User userToAdd;
    private User userToUpdate;
    ObservableList<String> serviceList;
    
    public static CreateEmployeController getCtrler() {
        return ctrler;
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
        userToUpdate = null;
        
        serviceList = FXCollections.observableArrayList("Commercial", "Comptabilité", "Préparateur", "Responsable Service Préparation");
        serviceComb.setItems(serviceList);
    }    
    
    public void fillFieldsWhenClientNumberOrMatriculeExists(User user){
        nameTextField.setText(user.getNom());
        surnameTextField.setText(user.getPrenom());
        emailTextField.setText(user.getEmail());
        numberTextField.setText(user.getTelephone());
        if(User.Type.EMPLOYE.equals(user.getType())){
            serviceComb.getSelectionModel().select(((Employe)user).getService());
            loginTextField.setText(((Employe)user).getLogin());
            passwordTextField.setText(((Employe)user).getPassword());
        }
    }
    
    public void clearAllTextFields(){
        service.getUtils().clearTextfields(new ArrayList<JFXTextField>(Arrays.asList(loginTextField, numberTextField, matriculeSearchTextField, nameTextField, surnameTextField, emailTextField)));
        passwordTextField.clear();
        serviceComb.getSelectionModel().clearSelection();
    }
    
    public boolean validateAllTextFields(){
        if(nameTextField.getText().isEmpty() 
           || surnameTextField.getText().isEmpty() 
           || emailTextField.getText().isEmpty() 
           || numberTextField.getText().isEmpty()
           || loginTextField.getText().isEmpty()
           || passwordTextField.getText().isEmpty()
           || serviceComb.getSelectionModel().getSelectedItem().toString().isEmpty() == true){
            return true;
        } else {
            return false;
        }
    }

    @FXML
    private void enableTextfieldsGridPaneAndUpdateEmployeeButton(ActionEvent event) {
        textFieldsGridPane.setDisable(false);
        updateEmployeeButton.setDisable(false);
        createEmployeeButton.setDisable(true);
    }
    
    
    @FXML
    private void validateMatriculeSearchTextField(ActionEvent event) {
        creationSuccesOrFailureLabel.setText("");
        if(matriculeSearchTextField.getText().isEmpty()){
            matriculeSearchErrorLabel.setText("Remplissez le champ");
        } else {
            //existingUser = service.getUserDao().selectOneEmployeByMatricule(clientNumberOrMatriculeSearchTextField.getText());
            service.getUserDao().setTypeOfSelect("SQL_SELECT_ALL_EMPLOYES");
            existingUser = service.printSpecificUser(matriculeSearchTextField.getText());
            System.out.println(existingUser);
            if(existingUser!=null){
                fillFieldsWhenClientNumberOrMatriculeExists(existingUser);
                textFieldsGridPane.setDisable(true);
                updateEmployeeButton.setDisable(true);
                createEmployeeButton.setDisable(true);
                creationSuccesOrFailureLabel.setText("");
            } else {
                matriculeSearchTextField.clear();
                clearAllTextFields();
                textFieldsGridPane.setDisable(false);
                matriculeSearchErrorLabel.setText("Utilsateur non créé, veuillez en créer un nouveau");
                createEmployeeButton.setDisable(false);
            }
        }
    }

    @FXML
    private void createEmployee(ActionEvent event) {
        if(validateAllTextFields() == false){
            userToAdd = new Employe(loginTextField.getText(), passwordTextField.getText(), serviceComb.getSelectionModel().getSelectedItem().toString(), nameTextField.getText(), surnameTextField.getText(), emailTextField.getText(), numberTextField.getText(), User.Type.EMPLOYE);
            userToAdd = service.createUser(userToAdd);
            if(userToAdd!=null && userToAdd.getId()>0){
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
    private void updateEmployee(ActionEvent event) {
        if(validateAllTextFields() == false){
            userToUpdate = new Employe(loginTextField.getText(), passwordTextField.getText(), ((Employe)existingUser).getMatricule(), serviceComb.getSelectionModel().getSelectedItem().toString(), existingUser.getId(), nameTextField.getText(), surnameTextField.getText(), emailTextField.getText(), numberTextField.getText(), User.Type.EMPLOYE);
            userToUpdate = service.updateUser(userToUpdate);
            if(userToUpdate!=null){
                creationSuccesOrFailureLabel.setTextFill(Color.GREEN);
                creationSuccesOrFailureLabel.setText("Modification réussie");
                createEmployeeButton.setDisable(false);
            } else {
                creationSuccesOrFailureLabel.setTextFill(Color.RED);
                creationSuccesOrFailureLabel.setText("Modification non réussie, veuillez réessayer");
                createEmployeeButton.setDisable(false);
            }
            //clearAllTextFields();
        } else {
            creationSuccesOrFailureLabel.setTextFill(Color.RED);
            creationSuccesOrFailureLabel.setText("Veuillez remplir tous les champs");
        }
    }
    
}
