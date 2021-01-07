/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Albéric
 */
public class DaoMysql {
    private Connection cnx;
    private PreparedStatement pstm;
    private int ok;
    private ResultSet rs;
    
    public void getConnection(){
        cnx = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/examen_java", "root", "");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void initPS(String sql){
            getConnection();
            try{
                if(sql.toLowerCase().startsWith("insert")){
                    pstm = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                } else {
                    pstm = cnx.prepareStatement(sql);
                }
            } catch(SQLException ex){
                Logger.getLogger(DaoMysql.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    public int executeMaj(){
        try {
            ok = pstm.executeUpdate();
        } catch(SQLException ex){
            Logger.getLogger(DaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ok;
    }
    
    
    public ResultSet executeSelect(){
        try{
            rs=pstm.executeQuery();
        } catch(SQLException ex){
            Logger.getLogger(DaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
    
    public PreparedStatement getPstm(){
        return this.pstm;
    }
    
    
    public void CloseConnection(){
        try{
            if(cnx!=null){
                cnx.close();
            }
        } catch(SQLException ex){
            Logger.getLogger(DaoMysql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
