/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.stock;

import dao.CategorieDao;
import dao.ProduitDao;
import java.util.List;
import java.util.ListIterator;
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
            if (produit.getCode().equals(value) || produit.getLibelle().equals(value))
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

  
    
}
