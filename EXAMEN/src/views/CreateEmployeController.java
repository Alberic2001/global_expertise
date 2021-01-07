/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.ServiceDao;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Employe;
import services.EmployeService;
import services.ServiceService;

/**
 *
 * @author Albéric
 */
public class CreateEmployeController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Label clientNumberOrMatriculeSearchTextFieldLabel;
    @FXML
    private GridPane textFieldsGridPane;
    @FXML
    private Label nameTextFieldLabel;
    @FXML
    private Label creationSuccesOrFailureLabel;
    @FXML
    private Label matriculeSearchErrorLabel;
    @FXML
    private JFXTextField matriculeSearchTextField;
    @FXML
    private JFXButton matriculeSearchButton;
    @FXML
    private JFXTextField nameTextField;
    @FXML
    private JFXButton modifyEmployeeButton;
    
    private static CreateEmployeController ctrler;
    private EmployeService service;
    private ServiceService serviceService;
    private Employe existingUser;
    private Employe userToAdd;
    @FXML
    private JFXButton createEmployeButton;
    @FXML
    private GridPane textFieldsGridPane1;
    @FXML
    private JFXTextField nameTextField1;
    @FXML
    private Label nameTextFieldLabel1;
    @FXML
    private JFXButton createServiceButton;
    @FXML
    private JFXComboBox<?> comb;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    

    @FXML
    private void validateMatriculeSearchTextField(ActionEvent event) {
    }

    @FXML
    private void create(ActionEvent event) {
        userToAdd = new Employe(nameTextField.getText(), LocalDate.now());
        ServiceDao dao = new ServiceDao();
        dao.selectAllForOne(comb.getSelectionModel().getSelectedItem().)
            userToAdd = serviceService.affecterEmployerInService(userToAdd, comb.getSelectionModel().getSelectedItem())
            if(userToAdd!=null && userToAdd.getId()>0){
                System.out.println(userToAdd);
                creationSuccesOrFailureLabel.setTextFill(Color.GREEN);
                creationSuccesOrFailureLabel.setText("Création réussie");
            } else {
                creationSuccesOrFailureLabel.setTextFill(Color.RED);
                 creationSuccesOrFailureLabel.setText("Création non réussie, veuillez réessayer");
            }
    }


    @FXML
    private void enableTextfieldsGridPaneAndUpdateEmployeeButton(ActionEvent event) {
    }
    
}
