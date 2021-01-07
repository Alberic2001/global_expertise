/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.time.LocalDate;

/**
 *
 * @author Albéric
 */
public class Employe {
    private int idEmploye;
    private String nomComplet;
    private LocalDate dateEmbauche;

    private Service service;
    
    public Employe() {
    }

    public Employe(int idEmploye, String nomComplet, LocalDate dateEmbauche) {
        this.idEmploye = idEmploye;
        this.nomComplet = nomComplet;
        this.dateEmbauche = dateEmbauche;
    }

    public Employe(String nomComplet, LocalDate dateEmbauche) {
        this.nomComplet = nomComplet;
        this.dateEmbauche = dateEmbauche;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public void setNomComplet(String nomComplet) {
        this.nomComplet = nomComplet;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Employe{" + "idEmploye=" + idEmploye + ", nomComplet=" + nomComplet + ", dateEmbauche=" + dateEmbauche + ", service=" + service + '}';
    }
    
    
    
}
