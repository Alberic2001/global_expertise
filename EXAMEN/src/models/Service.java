package models;

import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Albéric
 */
public class Service {
    private int idService;
    private String libelle;

    private List<Employe> employes = new ArrayList();
    
    public Service() {
    }

    public Service(String libelle) {
        this.libelle = libelle;
    }

    public Service(int idService, String libelle) {
        this.idService = idService;
        this.libelle = libelle;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }
    
    
}
