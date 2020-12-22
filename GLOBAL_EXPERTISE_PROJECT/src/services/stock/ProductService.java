/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.stock;

import com.jfoenix.controls.JFXButton;
import dao.CategorieDao;
import dao.ProduitDao;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import models.Categorie;
import models.Produit;
import services.IService;
import utils.Utils;

/**
 *
 * @author Albéric
 */
public class ProductService implements IService<Produit> {
    ProduitDao produitDao;
    CategorieDao categorieDao;
    Utils utils;
    
    public ProductService() {
        this.produitDao = new ProduitDao();
        this.categorieDao = new CategorieDao();
        this.utils = new Utils();
    }

    @Override
    public Produit printSpecific(String value){
        ListIterator<Produit> li = list().listIterator();
        Produit produit = null;
        while (li.hasNext()) {
            produit = li.next();
            if (produit.getCode().equals(value) || produit.getLibelle().equals(value) || String.valueOf(produit.getIdProduit()).equals(value))
                return produit;
        }
        return produit;
    }
    
    @Override
    public Produit create(Produit produit){
        return this.produitDao.add(produit);
    }
    
    @Override
    public Produit update(Produit produit){
        return this.produitDao.update(produit);
    }
    
    @Override
    public void delete(Produit produit){
        this.produitDao.delete(produit.getIdProduit());
    }
    
    @Override
    public List<Produit> list(){
        return produitDao.selectAll();
    }

    
    
    public ProduitDao getProduitDao() {
        return produitDao;
    }

    public void setProduitDao(ProduitDao produitDao) {
        this.produitDao = produitDao;
    }

    public CategorieDao getCategorieDao() {
        return categorieDao;
    }

    public void setCategorieDao(CategorieDao categorieDao) {
        this.categorieDao = categorieDao;
    }

    public Utils getUtils() {
        return utils;
    }

    public void setUtils(Utils utils) {
        this.utils = utils;
    }

    @Override
    public void assignValueToTableColumn(List<TableColumn<Produit, String>> tblcList, Produit obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TableCell<Produit, String> addCellFactory(ObservableList<Produit> oblProductsList, 
            TableView<Produit> productsTblv,
            SortedList<Produit> sortedData) {
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
                        //We can create action button
                        final JFXButton deleteBtn = new JFXButton("Delete");
                        //attach listener on button
                        deleteBtn.setOnAction(event -> {
                            //delete the clicked person object and update
                            Produit prod = getTableView().getItems().get(getIndex());
                            //Confirm
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.setContentText("Voulez vous vraiment supprimez "
                                    + prod.getLibelle() + " ?");
                            alert.setHeaderText("Suppression");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                delete(prod);
                                oblProductsList.remove(prod);
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
    }

    @Override
    public List<String> comboBoxListToString(List<Produit> products) {
        List<String> productsNames = new ArrayList();
        while (products.listIterator().hasNext()) {
            productsNames.add(products.listIterator().next().getLibelle());
        }
        return productsNames;
    }

  
    
}
