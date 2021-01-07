/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.EmployeDao;
import interfaces.IEmploye;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import models.Employe;

/**
 *
 * @author Albéric
 */
public class EmployeService implements IEmploye {
    private EmployeDao dao;
    
    public EmployeService() {
        dao = new EmployeDao();
    }
    
    
    public List<Employe> list(){
        return dao.selectAll();
    }
    

    @Override
    public void affiche() {
        ListIterator<Employe> li = list().listIterator();
        List<Employe> employesAtAge65 = new ArrayList();
        Employe emp = null;
        while (li.hasNext()) {
            emp = li.next();
            if (anciennete(emp)>=65)
                employesAtAge65.add(emp);
        }
    }
    
    public List<Employe> affichage() {
        ListIterator<Employe> li = list().listIterator();
        List<Employe> employesAtAge65 = new ArrayList();
        Employe emp = null;
        while (li.hasNext()) {
            emp = li.next();
            if (anciennete(emp)>=65)
                employesAtAge65.add(emp);
        }
        return employesAtAge65;
    }

    @Override
    public void compare() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int anciennete(Employe emp) {
        return LocalDate.now().getYear() - emp.getDateEmbauche().getYear();
    }
    
}
