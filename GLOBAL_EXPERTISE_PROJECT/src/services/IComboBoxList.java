/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javafx.collections.ObservableList;

/**
 *
 * @author Albéric
 * @param <T>
 */
@FunctionalInterface
public interface IComboBoxList<T> {
        public void printComboListContent(ObservableList<T> list);
}
