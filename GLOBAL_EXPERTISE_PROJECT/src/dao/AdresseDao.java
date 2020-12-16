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
import models.Adresse;
import models.Client;

/**
 *
 * @author Albéric
 */
public class AdresseDao implements IDao<Adresse> {
    
    private final String SQL_SELECT_ALL_ADRESSES = "SELECT * FROM adresse INNER JOIN user ON adresse.id_client = user.id_user WHERE user.type='CLIENT'";
    private final String SQL_SELECT_ALL_ADRESSES_OF_A_CLIENT = SQL_SELECT_ALL_ADRESSES+" AND adresse.id_client=?";
    private final String SQL_INSERT_ADRESSE = "INSERT INTO `adresse`(`rue`, `quartier`, `ville`, `details`, `id_client`) VALUES (?,?,?,?,?)";
    private final String SQL_UPDATE_ADRESSE = "UPDATE `adresse` SET `rue`=?,`quartier`=?,`ville`=?,`details`=? WHERE id_adresse=?";
    private final String SQL_DELETE_ADRESSE = "DELETE FROM `adresse` WHERE id_adresse=?";
    private DaoMysql daoMysql;

    public AdresseDao() {
        this.daoMysql = new DaoMysql();
    }
    
    
    
    @Override
    public Adresse add(Adresse adresse) {
        try {
            daoMysql.getConnection();
            daoMysql.initPS(SQL_INSERT_ADRESSE);
            PreparedStatement ps =daoMysql.getPstm();
            ps.setString(1, adresse.getRue());
            ps.setString(2, adresse.getQuartier());
            ps.setString(3, adresse.getVille());
            ps.setString(4, adresse.getDetails());
            ps.setInt(5, adresse.getClient().getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int idAdresse = rs.getInt(1);
                adresse.setIdAdresse(idAdresse);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return adresse;
    }

    @Override
    public List<Adresse> selectAll() {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_ADRESSES);
        PreparedStatement ps =daoMysql.getPstm();
        List<Adresse> adresses = new ArrayList();
        try {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Adresse adresse = new Adresse();
                adresse.setIdAdresse(rs.getInt("id_adresse"));
                adresse.setRue(rs.getString("rue"));
                adresse.setQuartier(rs.getString("quartier"));
                adresse.setVille(rs.getString("ville"));
                adresse.setDetails(rs.getString("details"));
                
                Client client = new Client(rs.getString("num_client"),
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("telephone"),
                            Client.Type.valueOf(rs.getString("type")));
                
                adresse.setClient(client);
                
                adresses.add(adresse);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AdresseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return adresses;
    }

    public List<Adresse> selectAllForOne(int id) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_ADRESSES_OF_A_CLIENT);
        PreparedStatement ps =daoMysql.getPstm();
        List<Adresse> adresses = new ArrayList();
        try {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Adresse adresse = new Adresse();
                adresse.setIdAdresse(rs.getInt("id_adresse"));
                adresse.setRue(rs.getString("rue"));
                adresse.setQuartier(rs.getString("quartier"));
                adresse.setVille(rs.getString("ville"));
                adresse.setDetails(rs.getString("details"));
                Client client = new Client(rs.getString("num_client"),
                            rs.getInt("id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("telephone"),
                            Client.Type.valueOf(rs.getString("type")));
                
                adresse.setClient(client);
                
                adresses.add(adresse);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(AdresseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return adresses;
        
    }

    @Override
    public Adresse update(Adresse adresse) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_UPDATE_ADRESSE);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setString(1, adresse.getRue());
            ps.setString(2, adresse.getQuartier());
            ps.setString(3, adresse.getVille());
            ps.setString(4, adresse.getDetails());
            ps.setInt(5, adresse.getIdAdresse());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdresseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return adresse;
    }
    
    @Override
    public int delete(int id) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_DELETE_ADRESSE);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(AdresseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return 0;
    }
    
}
