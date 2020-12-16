/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.stock;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ListIterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import models.Categorie;
import models.Produit;
import services.IComboBoxList;
import services.stock.CategoryService;
import services.stock.ProductService;
import utils.Utils;

/**
 * FXML Controller class
 *
 * @author Albéric
 */
public class CreatecategoryController implements Initializable {

    @FXML
    private JFXTextField categoryNumberTextField;
    @FXML
    private JFXButton categoryNumberSearchButton;
    @FXML
    private JFXButton categoryEditButton;
    @FXML
    private JFXTextField categoryNameTextField;
    @FXML
    private JFXTextArea categoryDescriptionTextArea;
    @FXML
    private JFXButton updateCategoryButton;
    @FXML
    private JFXButton createCategoryButton;
    @FXML
    private JFXTextField productCodeTextField;
    @FXML
    private JFXButton productCodeSearchButton;
    @FXML
    private JFXButton productEditButton;
    @FXML
    private JFXTextField productNameTextField;
    @FXML
    private JFXTextField productUnitPriceTextField;
    @FXML
    private JFXButton updateProductButton;
    @FXML
    private JFXButton createProductButton;

    
    private CategoryService categoryService;
    private ProductService productService;
    private Categorie existingCategory;
    private Categorie categoryToAdd;
    private Categorie categoryToUpdate;
    private Produit existingProduct;
    private Produit productToAdd;
    private Produit productToUpdate;
    
    ObservableList<String> categoryList;
    @FXML
    private JFXTextField productQuantityAvailableTextField;
    @FXML
    private JFXComboBox<String> productCategoryNameComboBox;
    @FXML
    private GridPane categoryGridPane;
    @FXML
    private GridPane productGridPane;
    @FXML
    private Label categoryCreationSuccessOrFailureLabel;
    @FXML
    private Label productCreationSuccessOrFailureLabel;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryService = new CategoryService();
        productService = new ProductService();
        existingCategory = null;
        categoryToAdd = null;
        categoryToUpdate = null;
        existingProduct = null;
        productToAdd = null;
        productToUpdate = null;
        productCategoryNameComboBox.setEditable(false);
        
        categoryList = FXCollections.observableArrayList(Utils.comboBoxCategoryListToString(categoryService.list()));
        productCategoryNameComboBox.setItems(categoryList);
        
    }    

    @FXML
    private void validateCategoryNumberSearchTextField(ActionEvent event) {
        categoryCreationSuccessOrFailureLabel.setText("");
        if(categoryNumberTextField.getText().isEmpty()){
            categoryCreationSuccessOrFailureLabel.setText("Remplissez le champ");
        } else {
            categoryCreationSuccessOrFailureLabel.setText("");
                existingCategory = categoryService.printSpecific(categoryNumberTextField.getText());
            if(existingCategory!=null){
                fillFieldsWhenCategoryExists(existingCategory);
                categoryGridPane.setDisable(true);
                createCategoryButton.setDisable(true);
                categoryCreationSuccessOrFailureLabel.setText("");
            } else {
                categoryNumberTextField.clear();
                clearAllCategoryTextFields();
                categoryGridPane.setDisable(false);
                categoryCreationSuccessOrFailureLabel.setText("La categorie n'existe pas");
                createCategoryButton.setDisable(false);
            }
        }
    }
    
    @FXML
    private void validateProductNumberSearchTextField(ActionEvent event) {
        productCreationSuccessOrFailureLabel.setText("");
        if(productCodeTextField.getText().isEmpty()){
           productCreationSuccessOrFailureLabel.setText("Remplissez le champ");
        } else {
            productCreationSuccessOrFailureLabel.setText("");
                existingProduct = productService.printSpecific(productCodeTextField.getText());
            if(existingProduct!=null){
                fillFieldsWhenProductExists(existingProduct);
                productGridPane.setDisable(true);
                createProductButton.setDisable(true);
                productCreationSuccessOrFailureLabel.setText("");
            } else {
                productCodeTextField.clear();
                clearAllProductsTextFields();
                productGridPane.setDisable(false);
                productCreationSuccessOrFailureLabel.setText("Le produit n'existe pas");
                createProductButton.setDisable(false);
            }
        }
    }

    @FXML
    private void enableCategoryGridPaneAndUpdateButton(ActionEvent event) {
        categoryGridPane.setDisable(false);
        updateCategoryButton.setDisable(false);
        createCategoryButton.setDisable(true);
    }
    
    @FXML
    private void enableProductGridPaneAndUpdateButton(ActionEvent event) {
        productGridPane.setDisable(false);
        updateProductButton.setDisable(false);
        createProductButton.setDisable(true);
    }

    @FXML
    private void updateCategory(ActionEvent event) {
        createCategoryButton.setDisable(false);
        
        if(validateCategoryTextFields() == false){
            categoryToUpdate = categoryService.printSpecific(categoryNumberTextField.getText());
            System.out.println(categoryToUpdate.toString());
                categoryToUpdate = new Categorie(categoryToUpdate.getIdCategorie(), categoryToUpdate.getNumCategorie(), categoryNameTextField.getText(), categoryDescriptionTextArea.getText());
            categoryToUpdate = categoryService.update(categoryToUpdate);
            if(categoryToUpdate!=null){
                categoryCreationSuccessOrFailureLabel.setTextFill(Color.GREEN);
                categoryCreationSuccessOrFailureLabel.setText("Modification réussie");
                
                categoryList = FXCollections.observableArrayList(Utils.comboBoxCategoryListToString(categoryService.list()));
                categoryService.getUtils().refreshComboList(productCategoryNameComboBox, categoryList);
            } else {
                categoryCreationSuccessOrFailureLabel.setTextFill(Color.RED);
                categoryCreationSuccessOrFailureLabel.setText("Modification non réussie, veuillez réessayer");
            }
            clearAllCategoryTextFields();
        } else {
            categoryCreationSuccessOrFailureLabel.setTextFill(Color.RED);
            categoryCreationSuccessOrFailureLabel.setText("Veuillez remplir tous les champs");
        }
    }

    @FXML
    private void createCategory(ActionEvent event) {
        if(validateCategoryTextFields() == false){
            categoryToAdd = new Categorie(categoryNameTextField.getText(), categoryDescriptionTextArea.getText());
            categoryToAdd = categoryService.create(categoryToAdd);
            if(categoryToAdd!=null && categoryToAdd.getIdCategorie()>0){
                System.out.println(categoryToAdd);
                categoryCreationSuccessOrFailureLabel.setTextFill(Color.GREEN);
                categoryCreationSuccessOrFailureLabel.setText("Création réussie");
                categoryList = FXCollections.observableArrayList(Utils.comboBoxCategoryListToString(categoryService.list()));
                categoryService.getUtils().refreshComboList(productCategoryNameComboBox, categoryList);
            } else {
                categoryCreationSuccessOrFailureLabel.setTextFill(Color.RED);
                categoryCreationSuccessOrFailureLabel.setText("Création non réussie, veuillez réessayer");
            }
            clearAllCategoryTextFields();
        } else {
            categoryCreationSuccessOrFailureLabel.setTextFill(Color.RED);
            categoryCreationSuccessOrFailureLabel.setText("Veuillez remplir tous les champs");
        }
    }

    @FXML
    private void updateProduct(ActionEvent event) {
        createProductButton.setDisable(false);
        
        if(validateProductTextFields() == true){
            productToUpdate = productService.printSpecific(productCodeTextField.getText());
            System.out.println(productToUpdate.toString());
            productToUpdate = new Produit(productToUpdate.getIdProduit(), productToUpdate.getCode(), productNameTextField.getText(), Double.valueOf(productUnitPriceTextField.getText()), Integer.valueOf(productQuantityAvailableTextField.getText()), categoryService.printSpecific(productCategoryNameComboBox.getSelectionModel().getSelectedItem()));
            System.out.println(productToUpdate.toString());
            productToUpdate = productService.update(productToUpdate);
            System.out.println(productToUpdate.toString());
            if(productToUpdate!=null){
                productCreationSuccessOrFailureLabel.setTextFill(Color.GREEN);
                productCreationSuccessOrFailureLabel.setText("Modification réussie");
                
                categoryList = FXCollections.observableArrayList(Utils.comboBoxCategoryListToString(categoryService.list()));
                categoryService.getUtils().refreshComboList(productCategoryNameComboBox, categoryList);
            } else {
                productCreationSuccessOrFailureLabel.setTextFill(Color.RED);
                productCreationSuccessOrFailureLabel.setText("Modification non réussie, veuillez réessayer");
            }
            clearAllCategoryTextFields();
        } else {
            productCreationSuccessOrFailureLabel.setTextFill(Color.RED);
            productCreationSuccessOrFailureLabel.setText("Veuillez remplir tous les champs");
        }
    }

    @FXML
    private void createProduct(ActionEvent event) {
        if(validateProductTextFields() == false){
            categoryToAdd = categoryService.printSpecific(productCategoryNameComboBox.getSelectionModel().getSelectedItem());
            System.out.println(categoryToAdd);
            productToAdd = new Produit(productNameTextField.getText(), Double.parseDouble(productUnitPriceTextField.getText()), Integer.parseInt(productQuantityAvailableTextField.getText()), categoryToAdd);
            if(productToAdd!=null && productToAdd.getIdProduit()>0){
                System.out.println(productToAdd.toString());
                productCreationSuccessOrFailureLabel.setTextFill(Color.GREEN);
                productCreationSuccessOrFailureLabel.setText("Création réussie");
                
                categoryList = FXCollections.observableArrayList(Utils.comboBoxCategoryListToString(categoryService.list()));
                categoryService.getUtils().refreshComboList(productCategoryNameComboBox, categoryList);
            } else {
                productCreationSuccessOrFailureLabel.setTextFill(Color.RED);
                productCreationSuccessOrFailureLabel.setText("Création non réussie, veuillez réessayer");
            }
            clearAllCategoryTextFields();
        } else {
            productCreationSuccessOrFailureLabel.setTextFill(Color.RED);
            productCreationSuccessOrFailureLabel.setText("Veuillez remplir tous les champs");
        }
    }
    
    
    public void fillFieldsWhenCategoryExists(Categorie categorie){
        categoryNameTextField.setText(categorie.getLibelle());
        categoryDescriptionTextArea.setText(categorie.getDescription());
    }
    
    public void fillFieldsWhenProductExists(Produit produit){
        productNameTextField.setText(produit.getLibelle());
        productUnitPriceTextField.setText(Double.toString(produit.getPrix()));
        productQuantityAvailableTextField.setText(String.valueOf(produit.getQuantite()));
        //productCategoryNameComboBox.getItems().clear();
        //productCategoryNameComboBox.getItems().add(produit.getCategorie().getLibelle());
        productCategoryNameComboBox.getSelectionModel().select(produit.getCategorie().getLibelle());
    }
    
    public void clearAllCategoryTextFields(){
        categoryNameTextField.clear();
        categoryDescriptionTextArea.clear();
    }
    
    public void clearAllProductsTextFields(){
        productUnitPriceTextField.clear();
        categoryDescriptionTextArea.clear();
        productQuantityAvailableTextField.clear();
        productCategoryNameComboBox.getSelectionModel().clearSelection();
    }
    
    public boolean validateCategoryTextFields(){
        if(categoryNameTextField.getText().isEmpty() 
           || categoryDescriptionTextArea.getText().isEmpty() == true){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validateProductTextFields(){
        if(productNameTextField.getText().isEmpty() 
           || categoryDescriptionTextArea.getText().isEmpty()
           || productQuantityAvailableTextField.getText().isEmpty() 
           || productCategoryNameComboBox.getSelectionModel().getSelectedItem().toString().isEmpty() == true){
            return true;
        } else {
            return false;
        }
    }
    
    public void refreshComboList(ObservableList<String> categories){
        productCategoryNameComboBox.setItems(categories);
    }
    
}
