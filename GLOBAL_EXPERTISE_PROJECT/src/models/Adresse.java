/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Albéric
 */
public class Adresse {
    private int idAdresse;
    private String details;
    private String rue;
    private String quartier;
    private String ville;

    //Propriete navigationnelle
    private Client client;
    
    public Adresse() {
    }

    public Adresse(String details, String rue, String quartier, String ville) {
        this.details = details;
        this.rue = rue;
        this.quartier = quartier;
        this.ville = ville;
    }
    
    public Adresse(String details, String rue, String quartier, String ville, Client client) {
        this.details = details;
        this.rue = rue;
        this.quartier = quartier;
        this.ville = ville;
        this.client = client;
    }

    public Adresse(int idAdresse, String details, String rue, String quartier, String ville) {
        this.idAdresse = idAdresse;
        this.details = details;
        this.rue = rue;
        this.quartier = quartier;
        this.ville = ville;
    }

    public int getIdAdresse() {
        return idAdresse;
    }

    public void setIdAdresse(int idAdresse) {
        this.idAdresse = idAdresse;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getQuartier() {
        return quartier;
    }

    public void setQuartier(String quartier) {
        this.quartier = quartier;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Adresse{" + "idAdresse=" + idAdresse + ", details=" + details + ", rue=" + rue + ", quartier=" + quartier + ", ville=" + ville + ", client=" + client.toString() + '}';
    }
 
}
