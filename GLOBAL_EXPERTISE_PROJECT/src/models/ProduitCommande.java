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
public class ProduitCommande {
    private int idProduitCommande;
    private int idProduit;
    private int idCommande;

    public ProduitCommande() {
    }

    public ProduitCommande(int idProduit, int idCommande) {
        this.idProduit = idProduit;
        this.idCommande = idCommande;
    }

    public ProduitCommande(int idProduitCommande, int idProduit, int idCommande) {
        this.idProduitCommande = idProduitCommande;
        this.idProduit = idProduit;
        this.idCommande = idCommande;
    }

    public int getIdProduitCommande() {
        return idProduitCommande;
    }

    public void setIdProduitCommande(int idProduitCommande) {
        this.idProduitCommande = idProduitCommande;
    }

    
    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public int getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(int idCommande) {
        this.idCommande = idCommande;
    }
    
    
}
