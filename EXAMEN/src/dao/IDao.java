/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;


/**
 *
 * @author Albéric
 */
public interface IDao<T> {
    public T add(T obj);
    public List<T> selectAll();
    public List<T> selectAllForOne(int id);
    public T update(T obj);
    public int delete(int id);
}
