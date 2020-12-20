/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.stock;

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
import javafx.stage.Modality;
import javafx.util.Callback;
import models.Adresse;
import models.Categorie;
import models.Produit;
import models.User;
import services.AddressService;
import services.BasicsService;
import services.stock.CategoryService;
import services.stock.ProductService;

/**
 * FXML Controller class
 *
 * @author Albéric
 */
public class ListCategoriesController implements Initializable {

    @FXML
    private TableView<Categorie> categoriesTblv;
    @FXML
    private TableColumn<Categorie, String> numberTblc;
    @FXML
    private TableColumn<Categorie, String> nameTblc;
    @FXML
    private TableColumn<Categorie, String> descriptionTblc;
    @FXML
    private TableColumn<Categorie, String> categoriesActionsTblc;
    @FXML
    private JFXComboBox<String> categoriesFilterComb;
    @FXML
    private JFXTextField categoriesSearchTextField;
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
    private TableColumn<Produit, String> productActionsTblc;
    @FXML
    private JFXButton addCategoryProductBtn;
    
    private ObservableList<Categorie> oblCategoriesList = FXCollections.observableArrayList();
    private ObservableList<Produit> oblProductsList = FXCollections.observableArrayList();
    private ObservableList<String> oblTypeList = FXCollections.observableArrayList();
    private BasicsService userService;
    private CategoryService categoryService;
    private ProductService productService;
    private Categorie category;
    private Produit product;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userService = new BasicsService();
        categoryService = new CategoryService();
        productService = new ProductService();
        category = null;

        numberTblc.setCellValueFactory(new PropertyValueFactory<>("numCategorie"));
        nameTblc.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        descriptionTblc.setCellValueFactory(new PropertyValueFactory<>("description"));
        
        codeTblc.setCellValueFactory(new PropertyValueFactory<>("code"));
        productNameTblc.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        priceTblc.setCellValueFactory(new PropertyValueFactory<>("prix"));
        quantityTblc.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        
        
        oblCategoriesList.addAll(categoryService.getCategorieDao().selectAll());
        
        FilteredList<Categorie> filteredData = new FilteredList<>(oblCategoriesList, b -> true);
        categoriesSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(categoryFiltered -> {
                //if filter text is empty, display all persons
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare firstname and last name of every person with filter text
                String lowerCaseFilter = newValue.toLowerCase();
                if (categoryFiltered.getLibelle().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (categoryFiltered.getNumCategorie().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (categoryFiltered.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        
        // Wrap the filteredList in a sortedList
        SortedList<Categorie> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(categoriesTblv.comparatorProperty());
        categoriesTblv.setItems(sortedData);

        
        Callback<TableColumn<Categorie, String>, TableCell<Categorie, String>> cellFactory = (param) -> {
            // The tablecell gonna contain buttons
            final TableCell<Categorie, String> cell = new TableCell<Categorie, String>() {
                //Override updateItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    //Ensure that cell is created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        //We can create action button
                        final JFXButton deleteBtn = new JFXButton("Delete");
                        //attach listener on button
                        deleteBtn.setOnAction(event -> {
                            //delete the clicked person object and update
                            Categorie cat = getTableView().getItems().get(getIndex());
                            //Confirm
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.setContentText("En faisant cela, vous supprimerez tous les produits associés à la catégorie.\n"
                                    + "Etes-vous sûr de vouloir supprimer la categorie " + cat.getLibelle() + " ?");
                            alert.setHeaderText("Suppression");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                categoryService.delete(cat);
                                oblCategoriesList.clear();
                                oblCategoriesList.addAll(categoryService.getCategorieDao().selectAll());
                                categoriesTblv.setItems(sortedData);
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
        categoriesActionsTblc.setCellFactory(cellFactory);
        
        categoriesTblv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            addCategoryProductBtn.setDisable(false);
            oblProductsList.clear();
            oblProductsList.addAll(productService.getProduitDao().selectAllForOne(newValue.getIdCategorie()));
            productsTblv.setItems(oblProductsList);
            category = newValue;
        });
        categoriesTblv.setItems(sortedData);
    }    

    @FXML
    private void handleLoadCreateCategoryWindow(ActionEvent event) throws IOException {
        categoryService.getUtils().loadWindow(categoriesTblv, "stock/createcategory");
        addCategoryProductBtn.setDisable(true);
    }
    
}
