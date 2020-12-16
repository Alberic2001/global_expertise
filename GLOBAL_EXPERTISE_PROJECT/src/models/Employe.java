/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import utils.Utils;

/**
 *
 * @author Albéric
 */
public class Employe extends User {
    
    
    private String login;
    private String password;
    private String matricule;
    private String service;

    public Employe() {
        this.type = Type.EMPLOYE;
        this.matricule = "mat_"+Utils.generateRandomInt(10);
    }
    
    public Employe(int id, String nom, String prenom, String email, String telephone, Type type) {
        super(id, nom, prenom, email, telephone, type);
    }

    public Employe(String login, String password, String service, String nom, String prenom, String email, String telephone, Type type) {
        super(nom, prenom, email, telephone, type);
        this.login = login;
        this.password = password;
        this.matricule = "mat_"+Utils.generateRandomInt(10);
        this.service = service;
    }

    public Employe(String login, String password, String matricule, String service, int id, String nom, String prenom, String email, String telephone, Type type) {
        super(id, nom, prenom, email, telephone, type);
        this.login = login;
        this.password = password;
        this.matricule = matricule;
        this.service = service;
    }
    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatricule(){
        return matricule;
    }
    
    public void setMatricule(String matricule){
        this.matricule = matricule;
    }
    
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Employe{" + "login=" + login + ", matricule=" + matricule + ", service=" + service + '}'+ super.toString();
    }

    public static Employe convertToEmploye(User u) {
        return new Employe(u.id, u.nom, u.prenom, u.email, u.telephone, u.type);
    }
}
