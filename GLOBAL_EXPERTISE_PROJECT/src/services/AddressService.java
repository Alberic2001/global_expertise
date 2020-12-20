/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import com.jfoenix.controls.JFXButton;
import dao.AdresseDao;
import dao.UserDao;
import java.lang.reflect.Field;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import models.Adresse;
import models.Produit;
import utils.Utils;

/**
 *
 * @author Albéric
 */
public class AddressService implements IService<Adresse> {

    UserDao userDao;
    AdresseDao adresseDao;
    Utils utils;

    public AddressService() {
        this.userDao = new UserDao();
        this.adresseDao = new AdresseDao();
        this.utils = new Utils();
    }
    
    @Override
    public Adresse printSpecific(String value) {
        ListIterator<Adresse> li = list().listIterator();
        Adresse adresse = null;
        while (li.hasNext()) {
            adresse = li.next();
            if (adresse.getRue().toLowerCase().equals(value) || adresse.getDetails().toLowerCase().equals(value))
                return adresse;
        }
        return adresse;
    }

    @Override
    public Adresse create(Adresse adresse) {
        return this.adresseDao.add(adresse);
    }

    @Override
    public Adresse update(Adresse adresse) {
        return this.adresseDao.update(adresse);
    }

    @Override
    public void delete(Adresse adresse) {
            this.adresseDao.delete(adresse.getIdAdresse());
    }

    @Override
    public List<Adresse> list() {
        return adresseDao.selectAll();
    }

    @Override
    public void assignValueToTableColumn(List<TableColumn<Adresse, String>> tblcList, Adresse address) {
        //ListIterator<TableColumn<User, String>> li = tblcList.listIterator();
        //Field[] fields = user.getClass().getFields();
        for(Field f : address.getClass().getFields()){
            f.setAccessible(true);
        }
        /*
        while (li.hasNext()) {
            li.next().setCellValueFactory(new PropertyValueFactory<>(""));
        }*/
        for(int i = 1; i<=tblcList.size(); i++){
            tblcList.get(i).setCellValueFactory(new PropertyValueFactory<>(address.getClass().getFields()[i].getName()));
        }
    }

    @Override
    public TableCell<Adresse, String> addCellFactory(ObservableList<Adresse> oblAddressesList, 
            TableView<Adresse> addressesTblv, 
            SortedList<Adresse> sortedData) {
        
        final TableCell<Adresse, String> cell = new TableCell<Adresse, String>() {
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
                            Adresse address = getTableView().getItems().get(getIndex());
                            //Confirm
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.setContentText("En faisant cela, vous supprimerez l'adresse "
                                    + address.getRue() + " " + address.getQuartier() + " " +
                                    address.getVille() + " " + address.getDetails() + " " 
                                    +"\nEtes-vous sûr de vouloir faire cela" + " ?");
                            alert.setHeaderText("Suppression");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                delete(address);
                                oblAddressesList.remove(address);
                                addressesTblv.setItems(sortedData);
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

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public AdresseDao getAdresseDao() {
        return adresseDao;
    }

    public void setAdresseDao(AdresseDao adresseDao) {
        this.adresseDao = adresseDao;
    }

    public Utils getUtils() {
        return utils;
    }

    public void setUtils(Utils utils) {
        this.utils = utils;
    }

    @Override
    public List<String> comboBoxListToString(List<Adresse> addresses) {
        List<String> addressNames = new ArrayList();
        while (addresses.listIterator().hasNext()) {
            addressNames.add(addresses.listIterator().next().getRue()+" "+addresses.listIterator().next().getQuartier()+" "+addresses.listIterator().next().getVille()+" "+addresses.listIterator().next().getDetails());
        }
        return addressNames;
    }
    
    
    
    
    
    
}
