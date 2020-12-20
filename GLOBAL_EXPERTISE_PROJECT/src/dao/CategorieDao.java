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

/**
 *
 * @author Albéric
 */
public class CategorieDao implements IDao<Categorie> {
    
    private String typeOfSelect;
    
    private final String SQL_SELECT_ALL_CATEGORIES = "SELECT * FROM `categorie`";
    private final String SQL_SELECT_ONE_CATEGORIE = "SELECT * FROM `categorie` WHERE `id_categorie`=?;";
    private final String SQL_INSERT_CATEGORIE = "INSERT INTO `categorie`(`num_categorie`, `libelle`, `description`) VALUES (?,?,?)";
    private final String SQL_UPDATE_CATEGORIE = "UPDATE `categorie` SET `libelle`=?,`description`=? where id_categorie=?";
    private final String SQL_DELETE_CATEGORIE = "DELETE FROM `categorie` WHERE id_categorie=?";
    
    private DaoMysql daoMysql;

    public CategorieDao() {
        this.daoMysql = new DaoMysql();
    }
    
    
    @Override
    public Categorie add(Categorie categorie) {
        try {
            daoMysql.getConnection();
            daoMysql.initPS(SQL_INSERT_CATEGORIE);
            PreparedStatement ps =daoMysql.getPstm();
            ps.setString(1, categorie.getNumCategorie());
            ps.setString(2, categorie.getLibelle());
            ps.setString(3, categorie.getDescription());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int idCategorie = rs.getInt(1);
                categorie.setIdCategorie(idCategorie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return categorie;
    }

    @Override
    public List<Categorie> selectAll() {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_CATEGORIES);
        PreparedStatement ps =daoMysql.getPstm();
        List<Categorie> categories = new ArrayList();
        try {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Categorie categorie = new Categorie(rs.getInt("id_categorie"), rs.getString("num_categorie"), rs.getString("libelle"), rs.getString("description"));
                categories.add(categorie);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategorieDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return categories;
    }
    
    @Override
    public List<Categorie> selectAllForOne(int id) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ONE_CATEGORIE);
        PreparedStatement ps =daoMysql.getPstm();
        List<Categorie> categories = new ArrayList();
        try {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Categorie categorie = new Categorie(rs.getInt("id_categorie"), rs.getString("num_categorie"), rs.getString("libelle"), rs.getString("description"));
                categories.add(categorie);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(CategorieDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return categories;
    }

    @Override
    public Categorie update(Categorie categorie) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_UPDATE_CATEGORIE);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setString(1, categorie.getLibelle());
            ps.setString(2, categorie.getDescription());
            ps.setInt(3, categorie.getIdCategorie());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CategorieDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return categorie;
    }

    @Override
    public int delete(int id) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_DELETE_CATEGORIE);
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