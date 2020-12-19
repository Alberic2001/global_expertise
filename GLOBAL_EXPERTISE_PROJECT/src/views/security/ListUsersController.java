/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.security;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.stage.Modality;
import javafx.util.Callback;
import models.Adresse;
import models.User;
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
    private TableColumn userActionsTblc;
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

    private static ListUsersController ctrler;
    private ObservableList<User> oblUsersList = FXCollections.observableArrayList();
    private ObservableList<Adresse> oblAddressList = FXCollections.observableArrayList();
    private ObservableList<String> oblTypeList = FXCollections.observableArrayList();
    private BasicsService userService;
    private AddressService addressService;
    private User user;
    private Adresse adresse;

    public static ListUsersController getCtrler() {
        return ctrler;
    }

    public User getUser() {
        return user;
    }

    public ObservableList<User> getOblUsersList() {
        return oblUsersList;
    }

    public ObservableList<Adresse> getOblAddressList() {
        return oblAddressList;
    }

    public TableView<User> getUsersTblv() {
        return usersTblv;
    }

    public TableView<Adresse> getUsersAddressesTblv() {
        return usersAddressesTblv;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrler = this;
        userService = new BasicsService();
        addressService = new AddressService();

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
        FilteredList<User> filteredData = new FilteredList<>(oblUsersList, b -> true);
        // Set the filter predicate whenever the filter change
        userSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(userFiltered -> {
                //if filter text is empty, display all persons
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare firstname and last name of every person with filter text
                String lowerCaseFilter = newValue.toLowerCase();
                if (userFiltered.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (userFiltered.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (userFiltered.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
        
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = (param) -> {
            // The tablecell gonna contain buttons
            final TableCell<User, String> cell = new TableCell<User, String>() {
                //Override updateItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    //Ensure that cell is created only on non-empt rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        //We can create action button
                        final JFXButton deleteBtn = new JFXButton("Delete");
                        //attach listener on button
                        deleteBtn.setOnAction(event -> {
                            //delete the clicked person object and update
                            User u = getTableView().getItems().get(getIndex());
                            //Confirm
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.setContentText("Etes-vous sûr de vouloir supprimer l'utilisateur " + u.getNom() + " " + u.getPrenom() + " ?");
                            alert.setHeaderText("Suppression");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                userService.deleteUser(u);
                                oblUsersList.clear();
                                userService.getUserDao().setTypeOfSelect("SQL_SELECT_ALL");
                                oblUsersList.addAll(userService.getUserDao().selectAll());
                                usersTblv.setItems(sortedData);
                            } else {
                                alert.close();
                            }
                        });
                        setGraphic(deleteBtn);
                        setText(null);

                    }
                }
            };

            return cell;

        };
        
        // set the custom factory to action column
        userActionsTblc.setCellFactory(cellFactory);
        
        usersFilterComb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("CLIENT")) {
                userService.getUserDao().setTypeOfSelect("SQL_SELECT_ALL_CLIENTS");
                oblUsersList.clear();
                oblUsersList.addAll(userService.getUserDao().selectAll());
            } else if (newValue.equals("EMPLOYE")) {
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

        usersTblv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            addAddressBtn.setDisable(false);
            oblAddressList.clear();
            oblAddressList.addAll(userService.getAdresseDao().selectAllForOne(newValue.getId()));
            usersAddressesTblv.setItems(oblAddressList);
            user = newValue;
        });
    }

    @FXML
    private void handleLoadAddAddressWindow(ActionEvent event) throws IOException {
        userService.getUtils().loadWindow(usersTblv, "security/addAdress");
    }

}
