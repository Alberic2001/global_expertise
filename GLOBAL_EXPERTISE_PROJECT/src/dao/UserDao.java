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
import models.Client;
import models.Employe;
import models.User;
import models.User.Type;

/**
 *
 * @author Albéric
 */
public class UserDao implements IDao<User> {
    private String typeOfSelect;
    
    private final String SQL_SELECT_ALL = "SELECT * FROM `user`";
    
    private final String SQL_SELECT_ALL_CLIENTS = "SELECT * FROM `user` WHERE type LIKE 'CLIENT'";
    private final String SQL_SELECT_ONE_CLIENT_BY_NUM_CLIENT = "SELECT * FROM `user` WHERE type LIKE 'CLIENT' AND `num_client`=?";
    private final String SQL_SELECT_ONE_USER_BY_ID_USER = "SELECT * FROM `user` WHERE type LIKE 'CLIENT' AND `id_user`=?";
    private final String SQL_INSERT_CLIENT = "INSERT INTO `user`(`nom`, `prenom`, `email`, `telephone`, `num_client`, `type`) VALUES (?,?,?,?,?,?)";
    private final String SQL_UPDATE_CLIENT = "UPDATE `user` SET `nom`=?,`prenom`=?,`email`=?,`telephone`=? WHERE type LIKE 'CLIENT' AND `id_user` = ?";

    private final String SQL_SELECT_ALL_EMPLOYES = "SELECT * FROM `user` WHERE type LIKE 'EMPLOYE'";
    private final String SQL_SELECT_ONE_EMPLOYE_BY_MATRICULE = "SELECT * FROM `user` WHERE type LIKE 'EMPLOYE' AND `matricule`=?";
    private final String SQL_SELECT_LOGIN_AND_PASSWORD_EMPLOYE = "SELECT `nom`, `prenom`, `matricule`, `type`, `email` , `service` FROM `user` WHERE type LIKE 'EMPLOYE' AND `login`=? AND `password`=?";
    private final String SQL_INSERT_EMPLOYE = "INSERT INTO `user`(`nom`, `prenom`, `email`, `telephone`, `matricule`, `type`, `login`, `password`, `service`) VALUES (?,?,?,?,?,?,?,?,?)";
    private final String SQL_UPDATE_EMPLOYE = "UPDATE `user` SET `nom`=?,`prenom`=?,`email`=?,`telephone`=?,`login`=?,`password`=?,`service`=? WHERE type LIKE 'EMPLOYE' AND `id_user` = ? ";
    
    private final String SQL_DELETE_USER = "DELETE FROM `user` WHERE id_user = ?";
    
    private DaoMysql daoMysql;

    public UserDao() {
        this.daoMysql = new DaoMysql();
    }

    public String getTypeOfSelect() {
        return typeOfSelect;
    }

    public void setTypeOfSelect(String typeOfSelect) {
        this.typeOfSelect = typeOfSelect;
    }
    
    @Override
    public User add(User user) {
        try {
            daoMysql.getConnection();
            if(user instanceof Client)
                daoMysql.initPS(SQL_INSERT_CLIENT);
            if(user instanceof Employe)
                daoMysql.initPS(SQL_INSERT_EMPLOYE);
            
            PreparedStatement ps =daoMysql.getPstm();
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getTelephone());
            if(user instanceof Client){
                ps.setString(5, ((Client)user).getNumClient());
                ps.setString(6, ((Client)user).getType().name());
            }
            if(user instanceof Employe){
                ps.setString(5, ((Employe)user).getMatricule());
                ps.setString(6, ((Employe)user).getType().name());
                ps.setString(7, ((Employe)user).getLogin());
                ps.setString(8, ((Employe)user).getPassword());
                ps.setString(9, ((Employe)user).getService());
            }
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                user.setId(id);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return user;
    }

    @Override
    public List<User> selectAll() {
        daoMysql.getConnection();
        List<User> users = new ArrayList<>();
        String type = this.getTypeOfSelect();
        if(type.equals("SQL_SELECT_ALL_EMPLOYES"))
            daoMysql.initPS(SQL_SELECT_ALL_EMPLOYES);
        if(type.equals("SQL_SELECT_ALL_CLIENTS"))
            daoMysql.initPS(SQL_SELECT_ALL_CLIENTS);
            
        try {
            PreparedStatement ps =daoMysql.getPstm();
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User user = null;
                
                if(rs.getString("type").compareTo("CLIENT")==0){
                     //user = Client.convertToClient(user);
                     user = new Client();
                    ((Client)user).setNumClient(rs.getString("num_client"));
                }
                if(rs.getString("type").compareTo("EMPLOYE")==0){
                    user = new Employe();
                    ((Employe)user).setMatricule(rs.getString("matricule"));
                    ((Employe)user).setLogin(rs.getString("login"));
                    ((Employe)user).setPassword(rs.getString("password"));
                    ((Employe)user).setService(rs.getString("service"));
                }
                user.setId(rs.getInt("id_user"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setTelephone(rs.getString("telephone"));
                user.setType(User.Type.valueOf(rs.getString("type")));
                
                users.add(user);
            }
        } catch(SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return users;    
    }

    
    public User selectUserByloginAndPassword(String login, String password) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_LOGIN_AND_PASSWORD_EMPLOYE);
        PreparedStatement ps =daoMysql.getPstm();
        User employe = null;
        try {
            ps.setString(1, login);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                employe = new Employe();
                ((Employe)employe).setMatricule(rs.getString("matricule"));
                ((Employe)employe).setService(rs.getString("service"));
                employe.setNom(rs.getString("nom"));
                employe.setPrenom(rs.getString("prenom"));
                employe.setEmail(rs.getString("email"));
                employe.setType(User.Type.valueOf(rs.getString("type")));
            }   
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return employe;
    }

    @Override
    public List<User> selectAllForOne(int id) {
        daoMysql.getConnection();
        List<User> users = new ArrayList<>();
        daoMysql.initPS(SQL_SELECT_ONE_USER_BY_ID_USER);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                User user = null;
                
                if(rs.getString("type").compareTo("CLIENT")==0){
                     //user = Client.convertToClient(user);
                     user = new Client();
                    ((Client)user).setNumClient(rs.getString("num_client"));
                }
                if(rs.getString("type").compareTo("EMPLOYE")==0){
                    user = new Employe();
                    ((Employe)user).setMatricule(rs.getString("matricule"));
                    ((Employe)user).setLogin(rs.getString("login"));
                    ((Employe)user).setPassword(rs.getString("password"));
                    ((Employe)user).setService(rs.getString("service"));
                }
                user.setId(rs.getInt("id_user"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setTelephone(rs.getString("telephone"));
                user.setType(User.Type.valueOf(rs.getString("type")));
                
                users.add(user);
            }
        } catch(SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return users;    
    }
    /*
    public User selectOneClientByNumClient(String numClient) {
        daoMysql.getConnection();
        User user = null;
        daoMysql.initPS(SQL_SELECT_ONE_CLIENT_BY_NUM_CLIENT);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setString(1, numClient);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                user = new Client();
                ((Client)user).setNumClient(rs.getString("num_client"));
                user.setId(rs.getInt("id_user"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setTelephone(rs.getString("telephone"));
                user.setType(User.Type.valueOf(rs.getString("type")));
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return user;
    }
    
    
    public User selectOneEmployeByMatricule(String matricule) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_SELECT_ONE_EMPLOYE_BY_MATRICULE);
        PreparedStatement ps =daoMysql.getPstm();
        User user = null;
        try {
            ps.setString(1, matricule);
            ResultSet rs = ps.executeQuery();
            
            user = new Employe();
            ((Employe)user).setMatricule(rs.getString("matricule"));
            ((Employe)user).setLogin(rs.getString("login"));
            ((Employe)user).setPassword(rs.getString("password"));
            ((Employe)user).setService(rs.getString("service"));
            user.setId(rs.getInt("id_user"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setTelephone(rs.getString("telephone"));
            user.setType(User.Type.valueOf(rs.getString("type")));
                
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return user;
    }
*/
    @Override
    public User update(User user) {
        daoMysql.getConnection();
        if(user.getType().equals(Type.CLIENT))
            daoMysql.initPS(SQL_UPDATE_CLIENT);
        else
            daoMysql.initPS(SQL_UPDATE_EMPLOYE);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setString(1, user.getNom());
            ps.setString(2, user.getPrenom());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getTelephone());
            if(user instanceof Client){
                ps.setInt(5, user.getId());
            } else {
                ps.setString(5, ((Employe)user).getLogin());
                ps.setString(6, ((Employe)user).getPassword());
                ps.setString(7, ((Employe)user).getService());
                ps.setInt(8, user.getId());
            }
            ps.executeUpdate();
            
        } catch(SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        
        return user;  
    }

    @Override
    public int delete(int id) {
        daoMysql.getConnection();
        daoMysql.initPS(SQL_DELETE_USER);
        PreparedStatement ps =daoMysql.getPstm();
        try {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            daoMysql.CloseConnection();
        }
        return 0;
    }
    
    
}
