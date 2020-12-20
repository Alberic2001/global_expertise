/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.stock;

import com.jfoenix.controls.JFXButton;
import dao.CategorieDao;
import dao.ProduitDao;
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
public class CategoryService implements IService<Categorie> {
    ProduitDao produitDao;
    CategorieDao categorieDao;
    Utils utils;
    public CategoryService() {
        this.produitDao = new ProduitDao();
        this.categorieDao = new CategorieDao();
        this.utils = new Utils();
    }
    
    @Override
    public Categorie printSpecific(String value){
        ListIterator<Categorie> li = list().listIterator();
        Categorie categorie = null;
        while (li.hasNext()) {
            categorie = li.next();
            if (categorie.getNumCategorie().equals(value) || categorie.getLibelle().equals(value))
                return categorie;
        }
        return categorie;
    }
    
    @Override
    public Categorie create(Categorie categorie){
        return this.categorieDao.add(categorie);
    }
    
    @Override
    public Categorie update(Categorie categorie){
        return this.categorieDao.update(categorie);
    }
    
    @Override
    public void delete(Categorie categorie){
        ListIterator<Produit> li = this.getProduitDao().selectAllForOne(categorie.getIdCategorie()).listIterator();
        while (li.hasNext()) {
            this.produitDao.delete(li.next().getIdProduit());
        }
            this.categorieDao.delete(categorie.getIdCategorie());
    }
    
    @Override
    public List<Categorie> list(){
        return categorieDao.selectAll();
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
    public void assignValueToTableColumn(List<TableColumn<Categorie, String>> tblcList, Categorie obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TableCell<Categorie, String> addCellFactory(ObservableList<Categorie> oblCategoriesList, 
            TableView<Categorie> categoriesTblv, 
            SortedList<Categorie> sortedData) {
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
                            alert.setContentText("En faisant cela, vous supprimerez tous les produits associés à "
                                    + cat.getLibelle() + "\nEtes-vous sûr de vouloir faire cela" + " ?");
                            alert.setHeaderText("Suppression");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                delete(cat);
                                oblCategoriesList.remove(cat);
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

    }

    
}
