/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.command;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.mysql.cj.util.StringUtils;
import java.net.URL;
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
import models.Adresse;
import models.Categorie;
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

    @FXML
    private JFXComboBox<String> vendorComb;
    @FXML
    private JFXTextField commandNumberTextField;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXComboBox<String> categoryComb;
    @FXML
    private JFXComboBox<String> productNameComb;
    @FXML
    private JFXTextField quantityTextField;
    @FXML
    private JFXTextField unitPriceTextField;
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
    @FXML
    private TableColumn<Produit, String> priceTblc;
    @FXML
    private TableColumn<Produit, String> quantityTblc;
    @FXML
    private TableColumn actionsTblc;
    @FXML
    private TableView<User> usersTblv;
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
    private TableView<Adresse> addressesTblv;
    @FXML
    private TableColumn<Adresse, String> roadTblc;
    @FXML
    private TableColumn<Adresse, String> districtTblc;
    @FXML
    private TableColumn<Adresse, String> townTblc;
    @FXML
    private TableColumn<Adresse, String> detailsTblc;
    @FXML
    private JFXButton addClientBtn;
    @FXML
    private Label errorlbl;
    @FXML
    private JFXButton registerCommand;
    @FXML
    private JFXTextField clientSearchTextField;
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
    private ObservableList<String> oblProductNameList = FXCollections.observableArrayList();
    private BasicsService userService;
    private AddressService addressService;
    private CategoryService categoryService;
    private ProductService productService;
    private User user;
    private Adresse adresse;

    public static EditCommandController getCtrler() {
        return ctrler;
    }

    public static void setCtrler(EditCommandController ctrler) {
        EditCommandController.ctrler = ctrler;
    }

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
        
        
        /*
        userService.getUserDao().setTypeOfSelect("SQL_SELECT_ALL_EMPLOYES");
        oblVendorList.addAll(userService.comboBoxListToString(userService.listUsers()));
        vendorComb.setItems(oblVendorList);
        
        oblCategoryList.addAll(categoryService.comboBoxListToString(categoryService.list()));
        categoryComb.setItems(oblCategoryList);
        categoryComb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            oblProductNameList.addAll(productService.comboBoxListToString(productService.getProduitDao().selectAllForOne(productService.printSpecific(newValue).getCategorie().getIdCategorie())));
            productNameComb.setDisable(false);
            productNameComb.setItems(oblProductNameList);
        });
        
        oblProductNameList.addAll(productService.comboBoxListToString(productService.getProduitDao().selectAll()));
        productNameComb.setDisable(true);
        productNameComb.setItems(oblProductNameList);
        productNameComb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            unitPriceTextField.setText(productService.printSpecific(newValue).getPrix().toString());
        });
        
        quantityTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            //totalPriceTextField.setText((Integer.parseInt(unitPriceTextField.getText())*Integer.parseInt(newValue)));
        });
        
        */
        
        codeTblc.setCellValueFactory(new PropertyValueFactory<>("code"));
        productNameTblc.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        priceTblc.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantityTblc.setCellValueFactory(new PropertyValueFactory<>("quantité")); 
        
        
        nameTblc.setCellValueFactory(new PropertyValueFactory<>("nom"));
        surnameTblc.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        emailTblc.setCellValueFactory(new PropertyValueFactory<>("email"));
        numberTblc.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        typeTblc.setCellValueFactory(new PropertyValueFactory<>("type"));

        roadTblc.setCellValueFactory(new PropertyValueFactory<>("rue"));
        districtTblc.setCellValueFactory(new PropertyValueFactory<>("quartier"));
        townTblc.setCellValueFactory(new PropertyValueFactory<>("ville"));
        detailsTblc.setCellValueFactory(new PropertyValueFactory<>("details"));

        
        
    }

    @FXML
    private void handleLoadAddClientWindow(ActionEvent event) {
    }

    @FXML
    private void handleLoadPreviewCommandWindow(ActionEvent event) {
    }

    @FXML
    private void addProduct(ActionEvent event) {
        oblProductList.add(productService.printSpecific(productNameComb.getSelectionModel().getSelectedItem()));
        productsTblv.setItems(oblProductList);
    }
    
}
