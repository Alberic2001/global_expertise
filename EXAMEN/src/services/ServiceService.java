/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.EmployeDao;
import dao.ServiceDao;
import interfaces.IEmploye;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import models.Employe;
import models.Service;

/**
 *
 * @author Albéric
 */
public class ServiceService implements IEmploye {

    private ServiceDao serviceDao;
    private EmployeDao employeDao;
    
    public ServiceService() {
        serviceDao = new ServiceDao();
        employeDao = new EmployeDao();
    }
    
    public Employe affecterEmployerInService(Employe employe, Service service){
        service.getEmployes().add(employe);
        employe.setService(service);
        employeDao.add(employe);
        return employe;
    }
    
    public Service add(Service service){
        return serviceDao.add(service);
    }

    public List<Service> list(){
        return serviceDao.selectAll();
    }
    
    public Service printSpecific(String value){
        ListIterator<Service> li = list().listIterator();
        Service service = null;
        while (li.hasNext()) {
            service = li.next();
            if (service.getLibelle().equals(value))
                return service;
        }
        return service;
    }
    
        public static List<String> comboBoxCategoryListToString(List<Service> services){
        ListIterator<Service> li = services.listIterator();
        String serviceName = null;
        List<String> serviceNames = new ArrayList();
        while (li.hasNext()) {
            serviceName = li.next().getLibelle();
            serviceNames.add(serviceName);
        }
        return serviceNames;
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
