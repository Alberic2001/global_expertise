/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Categorie;
import models.User;

/**
 *
 * @author Albéric
 */
public class Utils {
    
    public static String generateRandomString(int len) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
          +"lmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
    }
    
    public static String generateRandomInt(int len) {
		String chars = "0123456789";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
    }
    
    public void changeView(Control field, String view) throws IOException{
        //Cacher la fenetre Source
        field.getScene().getWindow().hide();
        //Afficher la fenetre de Cible
        Parent root = FXMLLoader.load(getClass().getResource("../views/"+view+".fxml"));
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setTitle("Transfert Argent");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    public String validateTextFields(TextField firstField){
        String errormessage = null;
        boolean field = firstField.getText() == null;
        if(field)
            errormessage = "Veuillez remplir les champs";
        else
            errormessage = "";
        return errormessage;
    }
    
    public String validateFields(TextField firstField, TextField secondField){
        return (firstField.getText().isEmpty() || secondField.getText().isEmpty()) ? "Veuillez remplir les champs" : "" ;
    }
    
    public static List<String> comboBoxCategoryListToString(List<Categorie> categories){
        ListIterator<Categorie> li = categories.listIterator();
        String categoryName = null;
        List<String> categoryNames = new ArrayList();
        while (li.hasNext()) {
            categoryName = li.next().getLibelle();
            categoryNames.add(categoryName);
        }
        return categoryNames;
    }
    
    public void refreshComboList(JFXComboBox<String> productCategoryNameComboBox, ObservableList<String> categories){
        productCategoryNameComboBox.setItems(categories);
    }
    
    
    public void assignValueToTableColumn(List<TableColumn<User, String>> tblcList, User user){
        //ListIterator<TableColumn<User, String>> li = tblcList.listIterator();
        //Field[] fields = user.getClass().getFields();
        for(Field f : user.getClass().getFields()){
            f.setAccessible(true);
        }
        /*
        while (li.hasNext()) {
            li.next().setCellValueFactory(new PropertyValueFactory<>(""));
        }*/
        for(int i = 1; i<=tblcList.size(); i++){
            tblcList.get(i).setCellValueFactory(new PropertyValueFactory<>(user.getClass().getFields()[i].getName()));
        }
    }
}
