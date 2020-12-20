/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views.command;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 *
 * @author Albéric
 */
public class EditCommandController implements Initializable  {

    @FXML
    private JFXComboBox<?> vendorComb;
    @FXML
    private JFXTextField commandNumberTextField;
    @FXML
    private JFXDatePicker datePicker;
    @FXML
    private JFXComboBox<?> productCodeComb;
    @FXML
    private JFXComboBox<?> categoryComb;
    @FXML
    private JFXComboBox<?> productNameComb;
    @FXML
    private JFXTextField quantityTextField;
    @FXML
    private JFXTextField unitPriceTextField;
    @FXML
    private JFXTextField totalPriceTextField;
    @FXML
    private JFXButton addProductBtn;
    @FXML
    private TableView<?> productsTblv;
    @FXML
    private TableColumn<?, ?> codeTblc;
    @FXML
    private TableColumn<?, ?> productNameTblc;
    @FXML
    private TableColumn<?, ?> priceTblc;
    @FXML
    private TableColumn<?, ?> quantityTblc;
    @FXML
    private TableColumn<?, ?> totalPriceTblc;
    @FXML
    private TableColumn<?, ?> categoryNameTblc;
    @FXML
    private TableColumn<?, ?> actionsTblc;
    @FXML
    private TableView<?> usersTblv;
    @FXML
    private TableColumn<?, ?> nameTblc;
    @FXML
    private TableColumn<?, ?> surnameTblc;
    @FXML
    private TableColumn<?, ?> emailTblc;
    @FXML
    private TableColumn<?, ?> numberTblc;
    @FXML
    private TableColumn<?, ?> typeTblc;
    @FXML
    private TableView<?> addressesTblv;
    @FXML
    private TableColumn<?, ?> roadTblc;
    @FXML
    private TableColumn<?, ?> districtTblc;
    @FXML
    private TableColumn<?, ?> townTblc;
    @FXML
    private TableColumn<?, ?> detailsTblc;
    @FXML
    private JFXButton addClientBtn;
    @FXML
    private Label errorlbl;
    @FXML
    private JFXButton registerCommand;
    @FXML
    private JFXTextField clientSearchTextField;
    @FXML
    private JFXButton previewBtn;
    @FXML
    private JFXButton printProFormaBill;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    private void handleLoadAddClientWindow(ActionEvent event) {
    }

    @FXML
    private void handleLoadPreviewCommandWindow(ActionEvent event) {
    }
    
}
