/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;
import javafx.scene.control.TableColumn;
import models.User;

/**
 *
 * @author Albéric
 */
public interface IService<T> {
    T printSpecific(String value);
    T create(T obj);
    T update(T obj);
    void delete(T obj);
    List<T> list();
    void assignValueToTableColumn(List<TableColumn<T, String>> tblcList, T obj);
}
