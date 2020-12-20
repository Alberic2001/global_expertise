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
import models.Facture;
import models.User;

/**
 *
 * @author Albéric
 */
public class FactureDao implements IDao<Facture> {
    
    private DaoMysql daoMysql;
    private CommandeDao daoCommande;
    
    private String SQL_SELECT_ALL_FACTURES = "SELECT * FROM `facture` INNER JOIN commande ON facture.id_commande=commande.id_commande INNER JOIN user ON commande.id_client=user.id_user";
    private String SQL_SELECT_ALL_FACTURES_OF_ONE_CLIENT = SQL_SELECT_ALL_FACTURES+" AND user.id_user=?";
    private String SQL_INSERT_FACTURE = "INSERT INTO `facture`(`num_facture`, `date_facture`, `montant_facture`, `statut_facture`, `id_commande`) VALUES (?,?,?,?,?)";
    private String SQL_DELETE_FACTURE = "DELETE FROM `facture` WHERE id_commande=?";
    
    public FactureDao() {
        this.daoMysql = new DaoMysql();
        this.daoCommande = new CommandeDao();
    }
    @Override
    public Facture add(Facture facture) {
        try {
            daoMysql.getConnection();
            daoMysql.initPS(SQL_INSERT_FACTURE);
            PreparedStatement ps =daoMysql.getPstm();
            ps.setString(1, facture.getNumFacture());
            ps.setString(2, facture.getDateFacture());
            ps.setDouble(3, facture.getMontantTotal());
            ps.setString(4, facture.getStatut().toString());
            ps.setInt(5, facture.getCommande().getIdCommande());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int idFacture = rs.getInt(1);
                facture.setIdFacture(idFacture);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FactureDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return facture;
    }
    
    @Override
    public List<Facture> selectAll() {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_FACTURES);
        PreparedStatement ps =daoMysql.getPstm();
        List<Facture> factures = new ArrayList();
        try {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Facture facture = new Facture(rs.getInt("id_facture"),
                            rs.getString("num_facture"), 
                            rs.getString("date_facture"), 
                            rs.getDouble("montant_facture"), 
                            Facture.StatutFacture.valueOf(rs.getString("statut_facture")),
                            new Commande(rs.getInt("id_commande"), 
                                    rs.getString("num_commande"), 
                                    Commande.Statut.valueOf(rs.getString("statut")), 
                                    LocalDate.parse(rs.getString("date_commande"), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                    new Client(rs.getInt("id_client"), 
                                            rs.getString("nom"), 
                                            rs.getString("prenom"), 
                                            rs.getString("email"), 
                                            rs.getString("telephone"), 
                                            Client.Type.valueOf(rs.getString("statut")))));
                factures.add(facture);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FactureDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return factures;
    }
    
    public List<Facture> selectAllForOne(int idUser) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_FACTURES_OF_ONE_CLIENT);
        List<Facture> factures = new ArrayList();
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Facture facture = new Facture(rs.getInt("id_facture"),
                            rs.getString("num_facture"), 
                            rs.getString("date_facture"), 
                            rs.getDouble("montant_facture"), 
                            Facture.StatutFacture.valueOf(rs.getString("statut_facture")),
                            new Commande(rs.getInt("id_commande"), 
                                    rs.getString("num_commande"), 
                                    Commande.Statut.valueOf(rs.getString("statut")), 
                                    LocalDate.parse(rs.getString("date_commande"), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                                    new Client(rs.getInt("id_client"), 
                                            rs.getString("nom"), 
                                            rs.getString("prenom"), 
                                            rs.getString("email"), 
                                            rs.getString("telephone"), 
                                            Client.Type.valueOf(rs.getString("statut")))));
                factures.add(facture);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(AdresseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return factures;
    }

    @Override
    public Facture update(Facture obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(int id) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_DELETE_FACTURE);
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
