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
public class Client extends User {
    
    private String numClient;
    
    public Client(){
    }

    public Client(int id, String nom, String prenom, String email, String telephone, Type type) {
        super(id, nom, prenom, email, telephone, type);
    }
    
    
    public Client(String nom, String prenom, String email, String telephone, Type type) {
        super(nom, prenom, email, telephone, type);
        this.numClient = "num_client_"+Utils.generateRandomString(10);
    }

    public Client(String numClient, int id, String nom, String prenom, String email, String telephone, Type type) {
        super(id, nom, prenom, email, telephone, type);
        this.numClient = numClient;
    }

    public Client(String numClient, String nom, String prenom, String email, String telephone, Type type) {
        super(nom, prenom, email, telephone, type);
        this.numClient = numClient;
    }

    
    
    public String getNumClient() {
        return numClient;
    }

    public void setNumClient(String numClient) {
        this.numClient = numClient;
    }

    @Override
    public String toString() {
        return "Client{" + "numClient=" + numClient + '}' + super.toString();
    }
    
    
    public static Client convertToClient(User u) {
        return new Client(u.id, u.nom, u.prenom, u.email, u.telephone, u.type);
    }
    
}
