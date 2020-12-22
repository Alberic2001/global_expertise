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
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import models.Commande;
import models.Produit;
import models.ProduitCommande;
import models.User;
import services.AddressService;
import services.BasicsService;
import services.commandes.CommandesService;
import services.stock.ProductService;

/**
 * FXML Controller class
 *
 * @author Albéric
 */
public class ListCommandsController implements Initializable {

    @FXML
    private TableView<Produit> productsTblv;
    @FXML
    private TableColumn<Commande, String> commandNumberTblc;
    @FXML
    private TableColumn<Commande, String> statusTblc;
    @FXML
    private TableColumn<Commande, String> clientNameTblc;
    @FXML
    private TableColumn<Commande, String> commandDateTblc;
    @FXML
    private TableColumn actionsTblc;
    @FXML
    private JFXComboBox<String> statusComb;
    @FXML
    private JFXTextField commandSearchTextField;
    @FXML
    private TableColumn<Produit, String> codeTblc;
    @FXML
    private TableColumn<Produit, String> productNameTblc;
    @FXML
    private TableColumn<Produit, String> quantityCommandedTblc;
    @FXML
    private TableColumn<Produit, String> quantityInStockTblc;
    @FXML
    private JFXButton modifyBtn;
    @FXML
    private TableView<Commande> commandsTblv;
    @FXML
    private TableColumn<Produit, String> totalPriceTblc;

    private static ListCommandsController ctrler;
    private ObservableList<Commande> oblCommandsList = FXCollections.observableArrayList();
    private ObservableList<Produit> oblProductsList = FXCollections.observableArrayList();
    private ObservableList<String> oblStatusList = FXCollections.observableArrayList();
    private BasicsService userService;
    private CommandesService commandService;
    private ProductService productService;
    private User user;
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ctrler = this;
        userService = new BasicsService();
        commandService = new CommandesService();
        productService = new ProductService();
        
        commandNumberTblc.setCellValueFactory(new PropertyValueFactory<>("numCommande"));
        commandDateTblc.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusTblc.setCellValueFactory(new PropertyValueFactory<>("statut"));

        codeTblc.setCellValueFactory(new PropertyValueFactory<>("code"));
        productNameTblc.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        quantityInStockTblc.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        
        oblCommandsList.addAll(commandService.list());
        oblStatusList.addAll(new ArrayList<>(Arrays.asList("TOUS", "NON_LIVRE", "LIVRE", "EN_COURS", "EN_ATTENTE")));
        
        FilteredList<Commande> filteredData = new FilteredList<>(oblCommandsList, b -> true);
        // Set the filter predicate whenever the filter change
        commandSearchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(commandFiltered -> {
                //if filter text is empty, display all persons
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare firstname and last name of every person with filter text
                String lowerCaseFilter = newValue.toLowerCase();
                if (commandFiltered.getNumCommande().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (commandFiltered.getStatut().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (commandFiltered.getClient().toString().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        
        SortedList<Commande> sortedData = new SortedList<>(filteredData);
        // Bind the sortedList comparator in a TableView comparator
        //  Otherwise sorting the tableview would have no effect
        sortedData.comparatorProperty().bind(commandsTblv.comparatorProperty());
        // Add sorted (and filtered data to the table)
        commandsTblv.setItems(sortedData);
        
        statusComb.setItems(oblStatusList);
        
        
        Callback<TableColumn<Commande, String>, TableCell<Commande, String>> cellFactory = (param) -> {
            return commandService.addCellFactory(oblCommandsList, commandsTblv, sortedData);
        };
        
        actionsTblc.setCellFactory(cellFactory);
        
        statusComb.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            ObservableList<Commande> oblNewCommandsList = FXCollections.observableArrayList();
            if (newValue.equals("NON_LIVRE")) {
                while(oblCommandsList.listIterator().hasNext() && oblCommandsList.listIterator().next().getStatut()==Commande.Statut.NON_LIVRE){
                    oblNewCommandsList.add(oblCommandsList.listIterator().next());
                }

            } else if (newValue.equals("LIVRE")) {
                while(oblCommandsList.listIterator().hasNext() && oblCommandsList.listIterator().next().getStatut()==Commande.Statut.LIVRE){
                    oblNewCommandsList.add(oblCommandsList.listIterator().next());
                }
            } else if (newValue.equals("EN_COURS")) {
                while(oblCommandsList.listIterator().hasNext() && oblCommandsList.listIterator().next().getStatut()==Commande.Statut.EN_COURS){
                    oblNewCommandsList.add(oblCommandsList.listIterator().next());
                }
            } else if (newValue.equals("EN_ATTENTE")) {
                while(oblCommandsList.listIterator().hasNext() && oblCommandsList.listIterator().next().getStatut()==Commande.Statut.EN_ATTENTE){
                    oblNewCommandsList.add(oblCommandsList.listIterator().next());
                }
            } else {
                oblNewCommandsList = oblCommandsList;
            }

            commandsTblv.setItems(sortedData);
        });
            
            commandsTblv.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            modifyBtn.setDisable(false);
            oblProductsList.clear();
            List<ProduitCommande> prodCom = commandService.getProduitCommandeDao().selectAllForOne(newValue.getIdCommande());
                while(prodCom.listIterator().hasNext()){
                    oblProductsList.add(productService.printSpecific(String.valueOf(prodCom.listIterator().next().getIdProduit())));
                }
                productsTblv.setItems(oblProductsList);
            });
    }    
}
