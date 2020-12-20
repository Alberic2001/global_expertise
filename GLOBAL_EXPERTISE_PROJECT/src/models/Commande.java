/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import utils.Utils;

/**
 *
 * @author Albéric
 */
public class Commande {
    
    public static enum Statut 
    { 
        NON_LIVRE, LIVRE, EN_COURS, EN_ATTENTE; 
    } 
    
    
    private int idCommande;
    private String numCommande;
    // statut = "non livré", "livré", "en cours de préparation"
    private Statut statut = Statut.NON_LIVRE;
    private LocalDate date;
    
    // propriete navigationnelle
    //Many to One (Commande vers Client)
    private Client client;
    //One to Many (Commande vers Produit)
    private List<Produit> produits;

    
    public Commande() {
        this.produits = new ArrayList<>();
        this.numCommande = "Num_cmde_"+Utils.generateRandomString(10);
        this.statut = Statut.NON_LIVRE;
        this.date = LocalDate.now();
    }
    
    public Commande(String numCommande, Statut statut) {
        this.numCommande = numCommande;
        this.statut = statut;
        this.date = LocalDate.now();
    }

    public Commande(int idCommande, String numCommande, Statut statut, LocalDate date) {
        this.idCommande = idCommande;
        this.numCommande = numCommande;
        this.statut = statut;
        this.date = date;
    }

    public Commande(int idCommande, String numCommande, Statut statut, LocalDate date, Client client) {
        this.idCommande = idCommande;
        this.numCommande = numCommande;
        this.statut = statut;
        this.client = client;
        this.date = date;
    }
    
    

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }

    public String getNumCommande() {
        return numCommande;
    }

    public void setNumCommande(String numCommande) {
        this.numCommande = numCommande;
    }
    
    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
    
    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    
    @Override
    public String toString() {
        return "Commande{" + "idCommande=" + idCommande + ", numCommande=" + numCommande + ", statut=" + statut + ", client=" + client + ", produits=" + produits + '}';
    }
    
    
    
}
