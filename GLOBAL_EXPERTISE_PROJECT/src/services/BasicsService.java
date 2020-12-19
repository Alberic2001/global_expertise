/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.AdresseDao;
import dao.CategorieDao;
import dao.CommandeDao;
import dao.FactureDao;
import dao.ProduitCommandeDao;
import dao.ProduitDao;
import dao.UserDao;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Adresse;
import models.Client;
import models.Commande;
import models.Employe;
import models.Facture;
import models.ProduitCommande;
import models.User;
import utils.Utils;
import views.security.ConnexionController;

/**
 *
 * @author Albéric
 */
public class BasicsService {
    UserDao userDao;
    AdresseDao adresseDao;
    CommandeDao commandeDao;
    ProduitCommandeDao produitCommandeDao;
    FactureDao factureDao;
    Utils utils;

    public BasicsService() {
        this.userDao = new UserDao();
        this.adresseDao = new AdresseDao();
        this.commandeDao = new CommandeDao();
        this.produitCommandeDao = new ProduitCommandeDao();
        this.factureDao = new FactureDao();
        this.utils = new Utils();
    }

    
    public User connect(String login,String password){
        return userDao.selectUserByloginAndPassword(login, password);
    }
    
    public void disconnectFirst(Control field, String view) throws IOException{
        utils.changeView(field, view);
    }
    
    public int disconnect(Control field, String view){
        Stage stage = (Stage) field.getScene().getWindow(); 
        stage.close();
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("../views/security/connexion.fxml"));
            Scene scene = new Scene(root);
            stage=new Stage();
            stage.setTitle("Transfert Argent");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(ConnexionController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public User printSpecificUser(String value){
        ListIterator<User> li = listUsers().listIterator();
        User user = null;
        while (li.hasNext()) {
            user = li.next();
            if(user instanceof Client){
                if (((Client)user).getNumClient().equals(value))
                    return user;
            } else {
                if (((Employe)user).getMatricule().equals(value))
                    return user;
            }
        }
        user = null;
        return user;
    }
    
    public User createUser(User user, Adresse adresse){
        user = this.userDao.add(user);
        if(user instanceof Client){
            adresse.setClient((Client)user);
            this.adresseDao.add(adresse);
        }
        return user;
    }
    
    public User updateUser(User user, Adresse adresse){
        user = this.userDao.update(user);
        if(user instanceof Client){
            adresse.setClient((Client)user);
            this.adresseDao.update(adresse);
        }
        return user;
    }
    
    public void deleteUser(User user){
        //ListIterator<Adresse> li = adresses.listIterator();
        ListIterator<Adresse> adresses = this.getAdresseDao().selectAllForOne(user.getId()).listIterator();
        ListIterator<Commande> commandes = this.getCommandeDao().selectAllForOne(user.getId()).listIterator();
        ListIterator<ProduitCommande> produitcommandes = this.getProduitCommandeDao().selectAllForOne(user.getId()).listIterator();
        ListIterator<Facture> factures = this.getFactureDao().selectAllForOne(user.getId()).listIterator();
        if(user instanceof Client){
            while(adresses.hasNext()){
                this.adresseDao.delete(adresses.next().getIdAdresse());
            }
            while(produitcommandes.hasNext()){
                this.produitCommandeDao.delete(produitcommandes.next().getIdCommande());
            }
            while(commandes.hasNext()){
                this.commandeDao.delete(commandes.next().getIdCommande());
            }
            while(factures.hasNext()){
                this.factureDao.delete(factures.next().getCommande().getIdCommande());
            }
        }
        this.userDao.delete(user.getId());
    }
    
    public List<User> listUsers(){
        return this.getUserDao().selectAll();
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
        for(int i = 1; i<tblcList.size(); i++){
            tblcList.get(i).setCellValueFactory(new PropertyValueFactory<>(user.getClass().getFields()[i].getName()));
        }
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

    public CommandeDao getCommandeDao() {
        return commandeDao;
    }

    public void setCommandeDao(CommandeDao commandeDao) {
        this.commandeDao = commandeDao;
    }

    public ProduitCommandeDao getProduitCommandeDao() {
        return produitCommandeDao;
    }

    public void setProduitCommandeDao(ProduitCommandeDao produitCommandeDao) {
        this.produitCommandeDao = produitCommandeDao;
    }

    public FactureDao getFactureDao() {
        return factureDao;
    }

    public void setFactureDao(FactureDao factureDao) {
        this.factureDao = factureDao;
    }
    
}
