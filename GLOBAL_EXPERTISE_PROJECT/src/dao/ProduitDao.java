/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.interfaces.IDao;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Categorie;
import models.Produit;

/**
 *
 * @author Albéric
 */
public class ProduitDao implements IDao<Produit> {
    private DaoMysql daoMysql;
    
    private String SQL_SELECT_ALL_PRODUITS = "SELECT * FROM `produit` INNER JOIN `categorie` ON `produit`.id_categorie=`categorie`.id_categorie WHERE `produit`.id_categorie=`categorie`.id_categorie";
    private String SQL_SELECT_ALL_PRODUITS_OF_ONE_CATEGORIE = "SELECT * FROM `produit` INNER JOIN `categorie` ON `produit`.id_categorie=`categorie`.id_categorie WHERE `categorie`.id_categorie=? LIMIT 10";
    private String SQL_INSERT_PRODUIT = "INSERT INTO `produit`(`code`, `libelle`, `prix`, `quantite`, `id_categorie`) VALUES (?,?,?,?,?)";
    private String SQL_UPDATE_PRODUIT = "UPDATE `produit` SET `libelle`=?,`prix`=?,`quantite`=?,`id_categorie`=? WHERE id_produit=?";
    private final String SQL_DELETE_PRODUIT = "DELETE FROM `produit` WHERE id_produit=?";
    
    public ProduitDao() {
        this.daoMysql = new DaoMysql();
    }

    @Override
    public Produit add(Produit produit) {
        try {
            daoMysql.getConnection();
            daoMysql.initPS(SQL_INSERT_PRODUIT);
            PreparedStatement ps =daoMysql.getPstm();
            ps.setString(1, produit.getCode());
            ps.setString(2, produit.getLibelle());
            ps.setDouble(3, produit.getPrix());
            ps.setInt(4, produit.getQuantite());
            ps.setInt(5, produit.getCategorie().getIdCategorie());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int idProduit = rs.getInt(1);
                produit.setIdProduit(idProduit);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return produit;
    }

    @Override
    public List<Produit> selectAll() {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_PRODUITS);
        PreparedStatement ps =daoMysql.getPstm();
        List<Produit> produits = new ArrayList();
        try {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Produit produit = new Produit(rs.getInt("id_produit"),
                        rs.getString("code"),
                        rs.getString("libelle"),
                        rs.getDouble("prix"),
                        rs.getInt("quantite"),
                        new Categorie(rs.getInt("id_categorie"), rs.getString("num_categorie"), rs.getString("categorie.libelle"), rs.getString("description")));
                
                produits.add(produit);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return produits;
    }
    
    
    public List<Produit> selectAllForOne(int idCategorie) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_PRODUITS_OF_ONE_CATEGORIE);
        List<Produit> produits = new ArrayList();
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setInt(1, idCategorie);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Produit produit = new Produit(
                        rs.getInt("id_produit"),
                        rs.getString("code"),
                        rs.getString("libelle"),
                        rs.getDouble("prix"),
                        rs.getInt("quantite"),
                        new Categorie(rs.getInt("id_categorie"), rs.getString("num_categorie"), rs.getString("libelle"), rs.getString("description")));
                produits.add(produit);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return produits;
    }
    
    public Produit selectOne(int idProduit) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_PRODUITS_OF_ONE_CATEGORIE);
        PreparedStatement ps =daoMysql.getPstm();
        Produit produit = null;
        try {
            ps.setInt(1, idProduit);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                 produit = new Produit(rs.getInt("id_produit"),
                        rs.getString("code"),
                        rs.getString("libelle"),
                        rs.getDouble("prix"),
                        rs.getInt("id_categorie"),
                        new Categorie(rs.getInt("id_categorie"), rs.getString("num_categorie"), rs.getString("libelle"), rs.getString("description")));
            }   
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return produit;
    }

    @Override
    public Produit update(Produit produit) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_UPDATE_PRODUIT);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setString(1, produit.getLibelle());
            ps.setDouble(2, produit.getPrix());
            ps.setInt(3, produit.getQuantite());
            ps.setInt(4, produit.getCategorie().getIdCategorie());
            ps.setInt(5, produit.getIdProduit());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return produit;
    }

    @Override
    public int delete(int id) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_DELETE_PRODUIT);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return 0;
    }
    
    
}
