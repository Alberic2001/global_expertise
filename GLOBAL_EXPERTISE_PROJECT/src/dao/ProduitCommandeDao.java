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
import models.Produit;
import models.ProduitCommande;
import models.User;

/**
 *
 * @author Albéric
 */
public class ProduitCommandeDao implements IDao<ProduitCommande> {
    
    
    private DaoMysql daoMysql;
    private ProduitDao produitDao;
    private CommandeDao commandeDao;
    
    private String SQL_SELECT_ALL_PRODUITS_OF_COMMANDE = "SELECT * FROM `produit` INNER JOIN produit_commande ON produit.id_produit=produit_commande.id_produit INNER JOIN commande ON produit_commande.id_commande=commande.id_commande  INNER JOIN user ON commande.id_client=user.id_user WHERE commande.statut='NON_LIVRE'";
    private String SQL_SELECT_ALL_PRODUITS_OF_ONE_COMMANDE = SQL_SELECT_ALL_PRODUITS_OF_COMMANDE+" AND commande.id_commande=?";
    private String SQL_INSERT_PRODUIT_IN_COMMANDE = "INSERT INTO `produit_commande`(`id_produit`, `id_commande`) VALUES (?,?)";
    private String SQL_DELETE_PRODUIT_COMMANDE = "DELETE FROM `produit_commande` WHERE id_commande=?";
    
    public ProduitCommandeDao() {
        this.daoMysql = new DaoMysql();
        this.produitDao = new ProduitDao();
        this.commandeDao = new CommandeDao();
    }

    
    @Override
    public ProduitCommande add(ProduitCommande produitCommande) {
        try {
            daoMysql.getConnection();
            daoMysql.initPS(SQL_INSERT_PRODUIT_IN_COMMANDE);
            PreparedStatement ps =daoMysql.getPstm();
            ps.setInt(1, produitCommande.getIdProduit());
            ps.setInt(2, produitCommande.getIdCommande());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int idProduitCommande = rs.getInt(1);
                produitCommande.setIdCommande(idProduitCommande);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CommandeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return produitCommande;
    }

    @Override
    public List<ProduitCommande> selectAll() {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_PRODUITS_OF_COMMANDE);
        PreparedStatement ps =daoMysql.getPstm();
        List<ProduitCommande> produitCommandes = new ArrayList();
        try {
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ProduitCommande produitCommande = new ProduitCommande(rs.getInt("id_produitcommande"), 
                                    rs.getInt("id_produit"),
                                    rs.getInt("id_commande"));
                
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
                commandeDao.selectAllForOne(rs.getInt("id_client"));
                Produit produit = new Produit(rs.getInt("id_produit"),
                                rs.getString("code"),
                                rs.getString("libelle"),
                                rs.getDouble("prix"),
                                rs.getInt("id_categorie"));
                produitDao.selectAllForOne(rs.getInt("id_categorie"));
                
                produitCommandes.add(produitCommande);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProduitDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return produitCommandes;
    }
    
    public List<ProduitCommande> selectAllForOne(int idCommande) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ALL_PRODUITS_OF_ONE_COMMANDE);
        List<ProduitCommande> produitcommandes = new ArrayList();
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setInt(1, idCommande);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ProduitCommande produitCommande = new ProduitCommande(rs.getInt("id_produitcommande"), 
                                    rs.getInt("id_produit"),
                                    rs.getInt("id_commande"),
                                    new Client(rs.getString("num_client"),
                                            rs.getInt("id_client"),
                                            rs.getString("nom"),
                                            rs.getString("prenom"),
                                            rs.getString("email"),
                                            rs.getString("telephone"),
                                            User.Type.valueOf(rs.getString("type"))));
                
                /*
                Commande commande = new Commande(rs.getInt("id_commande"), 
                                    rs.getString("num_commande"), 
                                    Commande.Statut.valueOf(rs.getString("statut")));
                commandeDao.selectAllForOne(rs.getInt("id_client"));
                Produit produit = new Produit(rs.getInt("id_produit"),
                                rs.getString("code"),
                                rs.getString("libelle"),
                                rs.getDouble("prix"),
                                rs.getInt("id_categorie"));
                produitDao.selectAllForOne(rs.getInt("id_categorie"));
                */
                
                produitcommandes.add(produitCommande);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(AdresseDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return produitcommandes;
    }

    @Override
    public ProduitCommande update(ProduitCommande obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(int id) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_DELETE_PRODUIT_COMMANDE);
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
