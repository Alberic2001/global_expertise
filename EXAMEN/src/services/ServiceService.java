/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.ServiceDao;
import interfaces.IEmploye;
import java.util.List;
import models.Employe;
import models.Service;

/**
 *
 * @author Albéric
 */
public class ServiceService implements IEmploye {

    private ServiceDao serviceDao;
    
    public ServiceService() {
        serviceDao = new ServiceDao();
    }
    
    public void affecterEmployerInService(Employe employe, Service service){
        service.getEmployes().add(employe);
    }
    
    public Service add(Service service){
        return serviceDao.add(service);
    }

    public List<Service> list(){
        return serviceDao.selectAll();
    }
    
    
    @Override
    public void affiche() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void compare() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
