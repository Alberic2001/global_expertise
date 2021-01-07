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
public class ServiceDao implements IDao<Service> {
    
    private final String SQL_SELECT_ALL_SERVICES = "SELECT * FROM `service`";
    private final String SQL_SELECT_ONE_SERVICE = "SELECT * FROM `service` WHERE `id_service`=?";
    private final String SQL_SELECT_LOGIN_AND_PASSWORD_EMPLOYE = "SELECT `nom`, `prenom`, `matricule`, `type`, `email` , `service` FROM `user` WHERE type LIKE 'EMPLOYE' AND `login`=? AND `password`=?";
    private final String SQL_INSERT_SERVICE = "INSERT INTO `service`( `libelle`) VALUES (?)";
    private final String SQL_UPDATE_EMPLOYE = "UPDATE `user` SET `nom`=?,`prenom`=?,`email`=?,`telephone`=?,`login`=?,`password`=?,`service`=? WHERE type LIKE 'EMPLOYE' AND `id_user` = ? ";
    private final String SQL_DELETE_USER = "DELETE FROM `user` WHERE id_user = ?";

    private DaoMysql daoMysql;

    public ServiceDao() {
        this.daoMysql = new DaoMysql();
    }

    
    @Override
    public Service add(Service service) {
        try {
            daoMysql.getConnection();
            daoMysql.initPS(SQL_INSERT_SERVICE);
            
            PreparedStatement ps =daoMysql.getPstm();
            ps.setString(1, service.getLibelle());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                service.setIdService(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ServiceDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return service;
    }

    @Override
    public List<Service> selectAll() {
        daoMysql.getConnection();
        List<Service> services = new ArrayList<>();
            daoMysql.initPS(SQL_SELECT_ALL_SERVICES);
        try {
            PreparedStatement ps =daoMysql.getPstm();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                     Service service = new Service();
                     service.setIdService(rs.getInt("id_service"));
                service.setLibelle(rs.getString("libelle"));
                
                services.add(service);
                    
                }
                
            
        } catch(SQLException ex) {
            Logger.getLogger(ServiceDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return services;
    }

    @Override
    public List<Service> selectAllForOne(int id) {
        daoMysql.getConnection();
        List<Service> services = new ArrayList<>();
        daoMysql.initPS(SQL_SELECT_ONE_SERVICE);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Service service = new Service();
                service.setIdService(rs.getInt("id_service"));
                service.setLibelle(rs.getString("libelle"));
                
                services.add(service);
            }
        } catch(SQLException ex) {
            Logger.getLogger(ServiceDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return services;  
    }

    @Override
    public Service update(Service obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
