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
public class Facture {
    
    public static enum StatutFacture{
        PAYE, NON_PAYE;
    }
    
    private int idFacture;
    private String numFacture;
    private String dateFacture;
    private double montantTotal;
    //statut = "payé", "non payé"
    private StatutFacture statut;
    private static int nbreFacture;
    
    // Propriete navigationnelle
    // One to One
    private Commande commande;

    public Facture() {
        this.numFacture = "Num_fact_"+Utils.generateRandomString(10);
    }

    public Facture(String dateFacture, double montantTotal, StatutFacture statut) {
        this.numFacture = "Num_fact_"+Utils.generateRandomString(10);
        this.dateFacture = dateFacture;
        this.montantTotal = montantTotal;
        this.statut = statut;
    }

    public Facture(String dateFacture, double montantTotal, StatutFacture statut, Commande commande) {
        this.numFacture = "Num_fact_"+Utils.generateRandomString(10);
        this.dateFacture = dateFacture;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.commande = commande;
    }

    public Facture(int idFacture, String numFacture, String dateFacture, double montantTotal, StatutFacture statut, Commande commande) {
        this.idFacture = idFacture;
        this.numFacture = numFacture;
        this.dateFacture = dateFacture;
        this.montantTotal = montantTotal;
        this.statut = statut;
        this.commande = commande;
    }
    
    
    public int getIdFacture() {
        return idFacture;
    }

    public void setIdFacture(int idFacture) {
        this.idFacture = idFacture;
    }

    public String getNumFacture() {
        return numFacture;
    }

    public void setNumFacture(String numFacture) {
        this.numFacture = numFacture;
    }

    public String getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(String dateFacture) {
        this.dateFacture = dateFacture;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public StatutFacture getStatut() {
        return statut;
    }

    public void setStatut(StatutFacture statut) {
        this.statut = statut;
    }

    public static int getNbreFacture() {
        return nbreFacture;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    @Override
    public String toString() {
        return "Facture{" + "idFacture=" + idFacture + ", numFacture=" + numFacture + ", datefacture=" + dateFacture + ", montantTotal=" + montantTotal + ", statut=" + statut + ", commande=" + commande.toString() + '}';
    }
    
    
    
}
