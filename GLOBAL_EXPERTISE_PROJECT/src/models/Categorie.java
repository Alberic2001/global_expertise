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
public class Categorie {

    private int idCategorie;
    private String numCategorie;
    private String libelle;
    private String description;
    
    
    private static int nbreCategorie;
    
    public Categorie() {
        numCategorie = "num_cat_"+Utils.generateRandomInt(13);
    }

    public Categorie(String libelle) {
        numCategorie = "num_cat_"+Utils.generateRandomInt(13);
        this.libelle = libelle;
    }

    public Categorie(int idCategorie, String libelle) {
        nbreCategorie = ++nbreCategorie;
        this.idCategorie = idCategorie;
        this.libelle = libelle;
    }

    public Categorie(String libelle, String description) {
        numCategorie = "num_cat_"+Utils.generateRandomInt(13);
        this.libelle = libelle;
        this.description = description;
    }

    public Categorie(int idCategorie, String numCategorie, String libelle, String description) {
        this.idCategorie = idCategorie;
        this.numCategorie = numCategorie;
        this.libelle = libelle;
        this.description = description;
    }

    
    
    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public static int getNbreCategorie() {
        return nbreCategorie;
    }

    public String getNumCategorie() {
        return numCategorie;
    }

    public void setNumCategorie(String numCategorie) {
        this.numCategorie = numCategorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Categorie{" + "idCategorie=" + idCategorie + ", numCategorie=" + numCategorie + ", libelle=" + libelle + ", description=" + description + '}';
    }
    
    
    
}
