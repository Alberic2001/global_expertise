/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.lang.reflect.Field;
import java.util.List;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Adresse;

/**
 *
 * @author Albéric
 */
public class AddressService implements IService<Adresse> {

    @Override
    public Adresse printSpecific(String value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Adresse create(Adresse obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Adresse update(Adresse obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Adresse obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Adresse> list() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
