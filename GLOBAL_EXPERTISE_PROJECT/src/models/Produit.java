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
public class Produit {
    
    private int idProduit;
    private String code;
    private String libelle;
    private Double prix;
    private int quantite;
    
    // Propriete navigationnelle
    private Categorie categorie;
    
    public Produit() {
    }

    public Produit(String libelle, Double prix, int quantite) {
        this.code = "code_"+Utils.generateRandomInt(13);
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
    }
    
    public Produit(String libelle, Double prix, int quantite, Categorie categorie) {
        this.code = "code_"+Utils.generateRandomInt(13);
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
        this.categorie = categorie;
    }

    public Produit(int idProduit, String code, String libelle, Double prix, int quantite) {
        this.idProduit = idProduit;
        this.code = code;
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
    }

    public Produit(int idProduit, String code, String libelle, Double prix, int quantite, Categorie categorie) {
        this.idProduit = idProduit;
        this.code = code;
        this.libelle = libelle;
        this.prix = prix;
        this.quantite = quantite;
        this.categorie = categorie;
    }

    
    
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    
    
    @Override
    public String toString() {
        return "Produit{" + "idProduit=" + idProduit + ", code=" + code + ", libelle=" + libelle + ", prix=" + prix + ", quantite=" + quantite  + categorie.getLibelle() + '}';
    }
    
    
    
}
