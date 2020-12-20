/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.command;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Albéric
 */
public class ListCommandsController implements Initializable {

    @FXML
    private TableView<?> productsTblv;
    @FXML
    private TableColumn<?, ?> commandNumberTblc;
    @FXML
    private TableColumn<?, ?> statusTblc;
    @FXML
    private TableColumn<?, ?> clientNameTblc;
    @FXML
    private TableColumn<?, ?> commandDateTblc;
    @FXML
    private TableColumn<?, ?> actionsTblc;
    @FXML
    private JFXComboBox<?> statusComb;
    @FXML
    private JFXTextField commandSearchTextField;
    @FXML
    private TableView<?> productsTblv1;
    @FXML
    private TableColumn<?, ?> codeTblc;
    @FXML
    private TableColumn<?, ?> productNameTblc;
    @FXML
    private TableColumn<?, ?> totalPriceTblc1;
    @FXML
    private TableColumn<?, ?> quantityCommandedTblc;
    @FXML
    private TableColumn<?, ?> quantityInStockTblc;
    @FXML
    private TableColumn<?, ?> productActionsTblc;
    @FXML
    private JFXButton modifyBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
