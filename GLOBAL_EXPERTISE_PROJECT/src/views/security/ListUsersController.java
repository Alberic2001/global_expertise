/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.security;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Adresse;
import models.User;
import models.User.Type;
import services.AddressService;
import services.BasicsService;

/**
 * FXML Controller class
 *
 * @author Albéric
 */
public class ListUsersController implements Initializable {

    @FXML
    private TableView<User> usersTblv;
    @FXML
    private JFXComboBox<String> usersFilterComb;
    @FXML
    private JFXTextField userSearchTextField;
    @FXML
    private TableView<Adresse> usersAddressesTblv;
    @FXML
    private JFXButton addAddressBtn;
    @FXML
    private TableColumn<User, String> nameTblc;
    @FXML
    private TableColumn<User, String> surnameTblc;
    @FXML
    private TableColumn<User, String> emailTblc;
    @FXML
    private TableColumn<User, String> numberTblc;
    @FXML
    private TableColumn<User, String> typeTblc;
    @FXML
    private TableColumn<?, ?> userActionsTblc;
    @FXML
    private TableColumn<Adresse, String> roadTblc;
    @FXML
    private TableColumn<Adresse, String> districtTblc;
    @FXML
    private TableColumn<Adresse, String> townTblc;
    @FXML
    private TableColumn<Adresse, String> detailsTblc;
    @FXML
    private TableColumn<?, ?> addresseActionsTblc;
    
    
    private ObservableList<User> oblUsersList = FXCollections.observableArrayList();
    private ObservableList<Adresse> oblAddressList = FXCollections.observableArrayList();
    private ObservableList<String> oblTypeList = FXCollections.observableArrayList();
    private BasicsService userService;
    private AddressService addressService;
    private User user;
    private Adresse adresse;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        userService = new BasicsService();
        addressService = new AddressService();
        user = new User();
        adresse = new Adresse();
        
        nameTblc.setCellValueFactory(new PropertyValueFactory<>("nom"));
        surnameTblc.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailTblc.setCellValueFactory(new PropertyValueFactory<>("email"));
        numberTblc.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        typeTblc.setCellValueFactory(new PropertyValueFactory<>("type"));
        
        roadTblc.setCellValueFactory(new PropertyValueFactory<>("rue"));
        districtTblc.setCellValueFactory(new PropertyValueFactory<>("quartier"));
        townTblc.setCellValueFactory(new PropertyValueFactory<>("ville"));
        detailsTblc.setCellValueFactory(new PropertyValueFactory<>("details"));
        
        //userService.assignValueToTableColumn(new ArrayList<>(Arrays.asList(nameTblc, surnameTblc, emailTblc, numberTblc, typeTblc)), user);
        //addressService.assignValueToTableColumn(new ArrayList<>(Arrays.asList(roadTblc, districtTblc, townTblc, detailsTblc)), adresse);
        userService.getUserDao().setTypeOfSelect("SQL_SELECT_ALL");
        oblUsersList.addAll(userService.getUserDao().selectAll());
        oblTypeList.addAll(new ArrayList<>(Arrays.asList("TOUS", "CLIENT", "EMPLOYE")));
        
        // Wrap the oblList in FilteredList(initially display all data)
        FilteredList<User> filteredData = new FilteredList<>(oblUsersList, b->true);
        // Set the filter predicate whenever the filter change
        userSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(userFiltered -> {
                //if filter text is empty, display all persons
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                // Compare firstname and last name of every person with filter text
                String lowerCaseFilter = newValue.toLowerCase();
                if(userFiltered.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if (userFiltered.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else if (userFiltered.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                } else {
                    return false;
                }
            });
        });
        // Wrap the filteredList in a sortedList
        SortedList<User> sortedData = new SortedList<>(filteredData);
        // Bind the sortedList comparator in a TableView comparator
        //  Otherwise sorting the tableview would have no effect
        sortedData.comparatorProperty().bind(usersTblv.comparatorProperty());
        // Add sorted (and filtered data to the table)
        usersTblv.setItems(sortedData);
        usersFilterComb.setItems(oblTypeList);
        
        usersFilterComb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            if(newValue.equals("CLIENT")){
                userService.getUserDao().setTypeOfSelect("SQL_SELECT_ALL_CLIENTS");
                oblUsersList.clear();
                oblUsersList.addAll(userService.getUserDao().selectAll());
            } else if(newValue.equals("EMPLOYE")){
                oblUsersList.clear();
                userService.getUserDao().setTypeOfSelect("SQL_SELECT_ALL_EMPLOYES");
                oblUsersList.addAll(userService.getUserDao().selectAll());
            } else {
                oblUsersList.clear();
                userService.getUserDao().setTypeOfSelect("SQL_SELECT_ALL");
                oblUsersList.addAll(userService.getUserDao().selectAll());
            }
            
            usersTblv.setItems(sortedData);
        });
        
        usersTblv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)->{
            oblAddressList.clear();
            oblAddressList.addAll(userService.getAdresseDao().selectAllForOne(newValue.getId()));
            usersAddressesTblv.setItems(oblAddressList);
        });
    }
    
    
    
}
