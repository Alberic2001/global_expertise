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
    
    
    public void delete(Categorie categorie, List<Produit> productsOfCategory){
        ListIterator<Produit> li = productsOfCategory.listIterator();
        while (li.hasNext()) {
            this.produitDao.delete(li.next().getIdProduit());
        }
            this.categorieDao.delete(categorie.getIdCategorie());
    }
    
    @Override
    public void delete(Categorie categorie){
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

    
}
