/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.commandes;

import dao.CategorieDao;
import dao.CommandeDao;
import dao.ProduitCommandeDao;
import dao.ProduitDao;
import java.util.List;
import java.util.ListIterator;
import javafx.scene.control.TableColumn;
import models.Commande;
import services.BasicsService;
import services.IService;
import utils.Utils;

/**
 *
 * @author Albéric
 */
public class CommandesService implements IService<Commande> {
    ProduitCommandeDao produitCommandeDao;
    CommandeDao commandeDao;
    ProduitDao produitDao;
    CategorieDao categorieDao;
    Utils utils;
    
    //Depot depot;

    public CommandesService() {
        this.produitCommandeDao = new ProduitCommandeDao();
        this.commandeDao = new CommandeDao();
        this.produitDao = new ProduitDao();
        this.categorieDao = new CategorieDao();
        this.utils = new Utils();
    }

    @Override
    public Commande printSpecific(String value) {
        ListIterator<Commande> li = list().listIterator();
        Commande commande = null;
        while (li.hasNext()) {
            commande = li.next();
            if (commande.getNumCommande().equals(value))
                return commande;
        }
        return commande;
    }

    @Override
    public List<Commande> list() {
        return commandeDao.selectAll();
    }

    @Override
    public Commande create(Commande obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Commande update(Commande obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Commande obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public ProduitCommandeDao getProduitCommandeDao() {
        return produitCommandeDao;
    }

    public void setProduitCommandeDao(ProduitCommandeDao produitCommandeDao) {
        this.produitCommandeDao = produitCommandeDao;
    }

    public CommandeDao getCommandeDao() {
        return commandeDao;
    }

    public void setCommandeDao(CommandeDao commandeDao) {
        this.commandeDao = commandeDao;
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
    public void assignValueToTableColumn(List<TableColumn<Commande, String>> tblcList, Commande obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    

}
