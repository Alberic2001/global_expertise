/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.commandes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import dao.CategorieDao;
import dao.CommandeDao;
import dao.ProduitCommandeDao;
import dao.ProduitDao;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import models.Commande;
import services.IService;
import utils.Utils;
import views.security.ConnexionController;
import views.style.MailTemplate;

/**
 *
 * @author Albéric
 */
public class CommandesService implements IService<Commande> {
    ProduitCommandeDao produitCommandeDao;
    CommandeDao commandeDao;
    ProduitDao produitDao;
    CategorieDao categorieDao;
    Utils utils;
    
    //Depot depot;

    public CommandesService() {
        this.produitCommandeDao = new ProduitCommandeDao();
        this.commandeDao = new CommandeDao();
        this.produitDao = new ProduitDao();
        this.categorieDao = new CategorieDao();
        this.utils = new Utils();
    }

    @Override
    public Commande printSpecific(String value) {
        ListIterator<Commande> li = list().listIterator();
        Commande commande = null;
        while (li.hasNext()) {
            commande = li.next();
            if (commande.getNumCommande().equals(value))
                return commande;
        }
        return commande;
    }

    @Override
    public List<Commande> list() {
        return commandeDao.selectAll();
    }

    @Override
    public Commande create(Commande obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Commande update(Commande command) {
        return this.commandeDao.update(command);
    }

    @Override
    public void delete(Commande obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    public ProduitCommandeDao getProduitCommandeDao() {
        return produitCommandeDao;
    }

    public void setProduitCommandeDao(ProduitCommandeDao produitCommandeDao) {
        this.produitCommandeDao = produitCommandeDao;
    }

    public CommandeDao getCommandeDao() {
        return commandeDao;
    }

    public void setCommandeDao(CommandeDao commandeDao) {
        this.commandeDao = commandeDao;
    }

    public ProduitDao getProduitDao() {
        return produitDao;
    }

    public void setProduitDao(ProduitDao produitDao) {
        this.produitDao = produitDao;
    }

    public CategorieDao getCategorieDao() {
        return categorieDao;
    }

    public void setCategorieDao(CategorieDao categorieDao) {
        this.categorieDao = categorieDao;
    }

    public Utils getUtils() {
        return utils;
    }

    public void setUtils(Utils utils) {
        this.utils = utils;
    }

    @Override
    public void assignValueToTableColumn(List<TableColumn<Commande, String>> tblcList, Commande obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public TableCell<Commande, String> addCellFactory(ObservableList<Commande> oblCommandsList, 
            TableView<Commande> commandsTblv, 
            SortedList<Commande> sortedData) {
        
        //List<TableCell<Commande, String>> list = new ArrayList(Arrays.asList());
        final TableCell<Commande, String> cell = new TableCell<Commande, String>() {
                //Override updateItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    //Ensure that cell is created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        //We can create action button
                        final JFXButton deleteBtn = new JFXButton("Delete");
                        //attach listener on button
                        deleteBtn.setOnAction(event -> {
                            //delete the clicked person object and update
                            Commande command = getTableView().getItems().get(getIndex());
                            //Confirm
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.setContentText("En faisant cela, vous supprimerez la commande "
                                    + command.getNumCommande() + " " + command.getStatut() + " du client" +
                                    command.getClient().toString()
                                    +"\nEtes-vous sûr de vouloir faire cela" + " ?");
                            alert.setHeaderText("Suppression");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                delete(command);
                                oblCommandsList.remove(command);
                                commandsTblv.setItems(sortedData);
                            } else {
                                alert.close();
                            }
                        });
                        setGraphic(deleteBtn);
                        setText(null);
                    }
                }
            };

            return cell;
    }
    
    
    
    public TableCell<Commande, String> addStatusCellFactory(ObservableList<Commande> oblCommandsList, 
            TableView<Commande> commandsTblv, 
            SortedList<Commande> sortedData) {
        
        final TableCell<Commande, String> statusCell = new TableCell<Commande, String>() {
                //Override updateItem method
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        final JFXButton statusBtn = new JFXButton("Mettre en attente");
                        statusBtn.setOnAction(event -> {
                            Commande command = getTableView().getItems().get(getIndex());
                            //Confirm
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            Label errorLbl = new Label();
                            alert.initModality(Modality.APPLICATION_MODAL);
                            alert.setHeaderText("Mise en attente");
                            GridPane gridPane = new GridPane();
                            JFXPasswordField password = new JFXPasswordField();
                            password.setPromptText("Enter your email password");
                            gridPane.add(new Label("En faisant cela, vous mettrez la commande "
                                    + command.getNumCommande() + " " + command.getStatut() + " du client" +
                                    command.getClient().toString() + " en attente"
                                    +"\nEtes-vous sûr de vouloir faire cela" + " ?"), 0, 0);
                            gridPane.add(password, 0, 1);
                            gridPane.add(errorLbl, 0, 2);
                            alert.getDialogPane().setContent(gridPane);
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK) {
                                if(password.getText().isEmpty()){
                                    errorLbl.setText("Remplissez le champ");
                                } else {
                                    utils.sendEmail(ConnexionController.getConnexion().getConnectedUser().getEmail(), password.getText() ,command.getClient().getEmail(), "Mise en attente de la commande "+ command.getNumCommande(), MailTemplate.getMailContent());
                                    try {
                                        utils.notifificate("Mise en attente", "Votre commande a été mise en attente");
                                    } catch (FileNotFoundException ex) {
                                        Logger.getLogger(CommandesService.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    oblCommandsList.remove(command);
                                    command.setStatut(Commande.Statut.EN_ATTENTE);
                                    update(command);
                                    oblCommandsList.add(command);
                                    commandsTblv.setItems(sortedData);
                                    alert.close();
                                }
                            } else {
                                alert.close();
                            }
                        });
                        setGraphic(statusBtn);
                        setText(null);
                    }
                }
            };
            
            return statusCell;
    }
    
    

    @Override
    public List<String> comboBoxListToString(List<Commande> commands) {
        List<String> commandsNames = new ArrayList();
        while (commands.listIterator().hasNext()) {
            commandsNames.add(commands.listIterator().next().getNumCommande()+" "+commands.listIterator().next().getDate()+" "+commands.listIterator().next().getClient());
        }
        return commandsNames;
    }

    
    
    
    

}
