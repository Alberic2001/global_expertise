/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.EmployeDao;
import dao.ServiceDao;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Employe;
import models.Service;
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
    private GridPane textFieldsGridPane;
    @FXML
    private Label nameTextFieldLabel;
    @FXML
    private JFXTextField nameTextField;
    
    private static CreateEmployeController ctrler;
    private EmployeService service;
    private ServiceService serviceService;
    private Service serviceToAdd;
    private Employe userToAdd;
    ObservableList<String> serviceList;
    private ObservableList<Employe> oblEmployesList = FXCollections.observableArrayList();
    
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
    private JFXComboBox<String> comb;
    @FXML
    private TableView<Employe> tblv;
    @FXML
    private TableColumn<Employe, String> nameTblc;
    @FXML
    private TableColumn<Employe, String> dateTblc;
    @FXML
    private Label errorLbl;
    @FXML
    private Label servErrorLbl;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ServiceDao dao = new ServiceDao();
        EmployeDao daoEmp = new EmployeDao();
        serviceService = new ServiceService();
        service = new EmployeService();
        serviceList = FXCollections.observableArrayList(ServiceService.comboBoxCategoryListToString(dao.selectAll()));
        comb.setItems(serviceList);
        
        nameTblc.setCellValueFactory(new PropertyValueFactory<>("nomComplet"));
        dateTblc.setCellValueFactory(new PropertyValueFactory<>("dateEmbauche"));
        if(!service.affichage().isEmpty())
            oblEmployesList.addAll(service.affichage());
        
        tblv.setItems(oblEmployesList);
    }    


    @FXML
    private void create(ActionEvent event) {
        userToAdd = new Employe(nameTextField.getText(), LocalDate.now());
            userToAdd = serviceService.affecterEmployerInService(userToAdd, serviceService.printSpecific(comb.getSelectionModel().getSelectedItem()));
            if(userToAdd!=null && userToAdd.getIdEmploye()>0){
                System.out.println(userToAdd);
                errorLbl.setTextFill(Color.GREEN);
                errorLbl.setText("Création réussie");
            } else {
                errorLbl.setTextFill(Color.RED);
                 errorLbl.setText("Création non réussie, veuillez réessayer");
            }
    }



    @FXML
    private void createService(ActionEvent event) {
        serviceToAdd = new Service(nameTextField1.getText());
        serviceService.add(serviceToAdd);
        serviceList.add(serviceToAdd.getLibelle());
        comb.setItems(serviceList);
        servErrorLbl.setTextFill(Color.GREEN);
        servErrorLbl.setText("Création réussie");
    }
    
}
