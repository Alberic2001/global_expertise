/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.command;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.util.Callback;
import models.Adresse;
import models.Commande;
import models.Produit;
import models.User;
import services.AddressService;
import services.BasicsService;
import services.stock.CategoryService;
import services.stock.ProductService;

/**
 *
 * @author Albéric
 */
public class EditCommandController implements Initializable  {

    private JFXComboBox<String> vendorComb;
    private JFXComboBox<String> productNameComb;
    @FXML
    private JFXTextField quantityTextField;
    @FXML
    private JFXTextField totalPriceTextField;
    @FXML
    private JFXButton addProductBtn;
    @FXML
    private TableView<Produit> productsTblv;
    @FXML
    private TableColumn<Produit, String> codeTblc;
    @FXML
    private TableColumn<Produit, String> productNameTblc;
    private TableColumn<Produit, String> priceTblc;
    private TableColumn<Produit, String> quantityTblc;
    @FXML
    private TableColumn actionsTblc;
    
    @FXML
    private Label errorlbl;
    @FXML
    private JFXButton registerCommand;
    @FXML
    private JFXButton previewBtn;
    @FXML
    private JFXButton printProFormaBill;
    
    
    private static EditCommandController ctrler;
    private ObservableList<User> oblUsersList = FXCollections.observableArrayList();
    private ObservableList<Adresse> oblAddressList = FXCollections.observableArrayList();
    private ObservableList<Produit> oblProductList = FXCollections.observableArrayList();
    private ObservableList oblVendorList = FXCollections.observableArrayList();
    private ObservableList<String> oblCategoryList = FXCollections.observableArrayList();
    private ObservableList<Produit> oblProductsList = FXCollections.observableArrayList();
    private ObservableList<Produit> oblProductsOrderedList = FXCollections.observableArrayList();
    private BasicsService userService;
    private AddressService addressService;
    private CategoryService categoryService;
    private ProductService productService;
    private User user;
    private Adresse adresse;

    public static EditCommandController getCtrler() {
        return ctrler;
    }
    @FXML
    private TableColumn<Produit, Double> unitPriceTblc;
    @FXML
    private TableColumn totalPriceTblc;
    @FXML
    private TableColumn orderedQtyTblc;
    @FXML
    private TableColumn categoryTblc;
    @FXML
    private JFXTextField productSearchTextField;
    @FXML
    private TableColumn productActionsTblc;
    @FXML
    private TableView<Produit> productsInCommandTblv;
    @FXML
    private TableColumn<Produit, String> productCodeTblc;
    @FXML
    private TableColumn<Produit, Double> productPriceTblc;
    @FXML
    private TableColumn<Produit, String> productQuantityTblc;
    @FXML
    private TableColumn<?, ?> nameTblc;

    public ObservableList<User> getOblUsersList() {
        return oblUsersList;
    }

    public void setOblUsersList(ObservableList<User> oblUsersList) {
        this.oblUsersList = oblUsersList;
    }

    public ObservableList<Adresse> getOblAddressList() {
        return oblAddressList;
    }

    public void setOblAddressList(ObservableList<Adresse> oblAddressList) {
        this.oblAddressList = oblAddressList;
    }

    public ObservableList<Produit> getOblProductList() {
        return oblProductList;
    }

    public void setOblProductList(ObservableList<Produit> oblProductList) {
        this.oblProductList = oblProductList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ctrler = this;
        userService = new BasicsService();
        addressService = new AddressService();
        productService = new ProductService();
        categoryService = new CategoryService();
        quantityTextField.setDisable(true);
        totalPriceTextField.setDisable(true);
        
        productCodeTblc.setCellValueFactory(new PropertyValueFactory<>("code"));
        productNameTblc.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        productPriceTblc.setCellValueFactory(new PropertyValueFactory<>("prix"));
        productQuantityTblc.setCellValueFactory(new PropertyValueFactory<>("quantité")); 
        
        codeTblc.setCellValueFactory(new PropertyValueFactory<>("code"));
        nameTblc.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        unitPriceTblc.setCellValueFactory(new PropertyValueFactory<>("prix"));        
        
        oblProductsList.addAll(productService.getProduitDao().selectAll());
        
        // Wrap the oblList in FilteredList(initially display all data)
        FilteredList<Produit> filteredData = new FilteredList<>(oblProductsList, b -> true);
        // Set the filter predicate whenever the filter change
        productSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(productFiltered -> {
                //if filter text is empty, display all persons
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare firstname and last name of every person with filter text
                String lowerCaseFilter = newValue.toLowerCase();
                if (productFiltered.getLibelle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (productFiltered.getCode().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }  else {
                    return false;
                }
            });
        });
        // Wrap the filteredList in a sortedList
        SortedList<Produit> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(productsTblv.comparatorProperty());
        productsTblv.setItems(sortedData);
        
        
        productsTblv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            addProductBtn.setDisable(false);
            quantityTextField.setDisable(false);
            totalPriceTextField.setDisable(false);
            oblProductsOrderedList.clear();
        });
        /*
        Callback<TableColumn<Produit, String>, TableCell<Produit, String>> cellActionsFactory = (param) -> {
            final TableCell<Produit, String> cell = new TableCell<Produit, String>() {
                //Override updateItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //Ensure that cell is created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final JFXButton deleteBtn = new JFXButton("Delete");
                        //attach listener on button
                        deleteBtn.setOnAction(event -> {
                            //delete the clicked person object and update
                            Produit produit = getTableView().getItems().get(getIndex());
                            //Confirm
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.setContentText("En faisant cela, vous supprimerez le produit de la commande "
                                    +"\nEtes-vous sûr de vouloir faire cela" + " ?");
                            alert.setHeaderText("Suppression");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                oblProductsList.remove(produit);
                                productsTblv.setItems(sortedData);
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
        productActionsTblc.setCellFactory(cellActionsFactory);
        */
        
        Callback<TableColumn<Produit, String>, TableCell<Produit, String>> cellQtyOrderedFactory = (param) -> {
            final TableCell<Produit, String> cell = new TableCell<Produit, String>() {
                //Override updateItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //Ensure that cell is created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final String qty = quantityTextField.getText();
                        setText(qty);
                    }
                }
            };
            return cell;
        };
        orderedQtyTblc.setCellFactory(cellQtyOrderedFactory);
        
        Callback<TableColumn<Produit, String>, TableCell<Produit, String>> cellTotalPriceFactory = (param) -> {
            final TableCell<Produit, String> cell = new TableCell<Produit, String>() {
                //Override updateItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //Ensure that cell is created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final String tp = totalPriceTextField.getText();
                        setText(tp);
                    }
                }
            };
            return cell;
        };
        totalPriceTblc.setCellFactory(cellTotalPriceFactory);
        
        
        
        totalPriceTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            int qty = Integer.parseInt(quantityTextField.getText());
            Produit produit = productsInCommandTblv.getSelectionModel().getSelectedItem();
            Double total = produit.getPrix()*qty;
            totalPriceTextField.setText(String.valueOf(total));
        });
    }


    @FXML
    private void handleLoadPreviewCommandWindow(ActionEvent event) {
    }

    @FXML
    private void addProduct(ActionEvent event) {
        Produit produit = productsInCommandTblv.getSelectionModel().getSelectedItem();
        oblProductsOrderedList.add(produit);
        productsInCommandTblv.setItems(oblProductsOrderedList);
    }
    
}
