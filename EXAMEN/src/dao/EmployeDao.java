/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Employe;
import models.Service;

/**
 *
 * @author Albéric
 */
public class EmployeDao implements IDao<Employe> {
    
    private final String SQL_SELECT_ALL_EMPLOYES = "SELECT * FROM `employe` INNER JOIN service on employe.id_service=service.id_service";
    private final String SQL_SELECT_EMPLOYES_OF_SERVICE = "SELECT * FROM `employe` INNER JOIN service on employe.id_service=service.id_service WHERE `id_service`=?";
    private final String SQL_SELECT_LOGIN_AND_PASSWORD_EMPLOYE = "SELECT `nom`, `prenom`, `matricule`, `type`, `email` , `service` FROM `user` WHERE type LIKE 'EMPLOYE' AND `login`=? AND `password`=?";
    private final String SQL_INSERT_EMPLOYE = "INSERT INTO `employe`(`nom_complet`, `date_embauche`, `id_service`) VALUES (?,?,?)";
    private final String SQL_UPDATE_EMPLOYE = "UPDATE `user` SET `nom`=?,`prenom`=?,`email`=?,`telephone`=?,`login`=?,`password`=?,`service`=? WHERE type LIKE 'EMPLOYE' AND `id_user` = ? ";
    private final String SQL_DELETE_USER = "DELETE FROM `user` WHERE id_user = ?";
    
    private DaoMysql daoMysql;

    public EmployeDao() {
        this.daoMysql = new DaoMysql();
    }

    @Override
    public Employe add(Employe employe) {
        try {
            daoMysql.getConnection();
            daoMysql.initPS(SQL_INSERT_EMPLOYE);
            
            PreparedStatement ps =daoMysql.getPstm();
            ps.setString(1, employe.getNomComplet());
            ps.setString(2, employe.getDateEmbauche().toString());
            ps.setInt(3, employe.getService().getIdService());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                employe.setIdEmploye(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return employe;
    }

    @Override
    public List<Employe> selectAll() {
        daoMysql.getConnection();
        List<Employe> employes = new ArrayList<>();
            daoMysql.initPS(SQL_SELECT_ALL_EMPLOYES);
        try {
            PreparedStatement ps =daoMysql.getPstm();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                     Employe employe = new Employe();
                     employe.setIdEmploye(rs.getInt("id_employe"));
                employe.setNomComplet(rs.getString("nom_complet"));
                employe.setDateEmbauche(LocalDate.parse(rs.getString("date_commande"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                employe.setService(new Service(rs.getInt("id_service"), rs.getString("libelle")));
                
                employes.add(employe);
                    
                }
                
            
        } catch(SQLException ex) {
            Logger.getLogger(EmployeDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return employes; 
    }

    @Override
    public List<Employe> selectAllForOne(int id) {
       daoMysql.getConnection();
        List<Employe> employes = new ArrayList<>();
        daoMysql.initPS(SQL_SELECT_EMPLOYES_OF_SERVICE);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Employe employe = new Employe();
                employe.setIdEmploye(rs.getInt("id_employe"));
                employe.setNomComplet(rs.getString("nom_complet"));
                employe.setDateEmbauche(LocalDate.parse(rs.getString("date_commande"), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                employe.setService(new Service(rs.getInt("id_service"), rs.getString("libelle")));
                
                employes.add(employe);
            }
        } catch(SQLException ex) {
            Logger.getLogger(ServiceDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return employes;  
    }

    @Override
    public Employe update(Employe obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
