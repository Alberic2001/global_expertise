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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Client;
import models.Commande;

/**
 *
 * @author Albéric
 */
public class CommandeDao implements IDao<Commande> {

    
    private DaoMysql daoMysql;
    //statutCommande -> NON_LIVRE, LIVRE, EN_COURS, EN_ATTENTE
    private String statutCommande = "NON_LIVRE";
    
    private String SQL_SELECT_ALL_COMMANDES = "SELECT * FROM `commande` INNER JOIN user ON commande.id_client=user.id_user WHERE type='CLIENT'";
    private String SQL_SELECT_ALL_COMMANDES_OF_ONE_CLIENT = SQL_SELECT_ALL_COMMANDES+" AND user.id_user=?";
    private String SQL_INSERT_COMMANDE = "INSERT INTO `commande`(`num_commande`, `statut`, `id_client`) VALUES (?,?,?)";
    private String SQL_UPDATE_COMMANDE = "UPDATE `commande` SET `statut`=? where id_commande=?";
    private String SQL_DELETE_COMMANDE = "DELETE FROM `commande` WHERE id_commande=?";
    
    public CommandeDao() {
        this.daoMysql = new DaoMysql();
    }

    public String getStatutCommande() {
        return statutCommande;
    }

    public void setStatutCommande(String statutCommande) {
        this.statutCommande = statutCommande;
    }

    
    @Override
    public Commande add(Commande commande) {
        try {
            daoMysql.getConnection();
            daoMysql.initPS(SQL_INSERT_COMMANDE);
            PreparedStatement ps =daoMysql.getPstm();
            ps.setString(1, commande.getNumCommande());
            ps.setString(2, commande.getStatut().toString());
            ps.setDouble(3, commande.getClient().getId());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int idCommande = rs.getInt(1);
                commande.setIdCommande(idCommande);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return commande;
    }

    @Override
    public List<Commande> selectAll() {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_COMMANDES);
        PreparedStatement ps =daoMysql.getPstm();
        List<Commande> commandes = new ArrayList();
        try {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Commande commande = new Commande(rs.getInt("id_commande"), 
                                    rs.getString("num_commande"), 
                                    Commande.Statut.valueOf(rs.getString("statut")),
                                    LocalDate.parse(rs.getString("date_commande"), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                    new Client(rs.getInt("id_client"), 
                                            rs.getString("nom"), 
                                            rs.getString("prenom"), 
                                            rs.getString("email"), 
                                            rs.getString("telephone"), 
                                            Client.Type.valueOf(rs.getString("type"))));
                commandes.add(commande);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return commandes;
    }
    
    
    public List<Commande> selectAllForOne(int idClient) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_COMMANDES_OF_ONE_CLIENT);
        List<Commande> commandes = new ArrayList();
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setInt(1, idClient);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Commande commande = new Commande(rs.getInt("id_commande"), 
                                    rs.getString("num_commande"), 
                                    Commande.Statut.valueOf(rs.getString("statut")), 
                                    LocalDate.parse(rs.getString("date_commande"), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                    new Client(rs.getInt("id_client"), 
                                            rs.getString("nom"), 
                                            rs.getString("prenom"), 
                                            rs.getString("email"), 
                                            rs.getString("telephone"), 
                                            Client.Type.valueOf(rs.getString("statut"))));
                commandes.add(commande);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(AdresseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return commandes;
    }

    @Override
    public Commande update(Commande commande) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_UPDATE_COMMANDE);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setString(1, commande.getStatut().name());
            ps.setInt(2, commande.getIdCommande());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CommandeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return commande;
    }

    @Override
    public int delete(int id) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_DELETE_COMMANDE);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setInt(1, id);
            daoMysql.executeMaj();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return 0;
    }
    
    
}
