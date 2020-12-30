/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import java.util.Random;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import models.Categorie;
import models.User;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Albéric
 */
public class Utils {
    
    public static String generateRandomString(int len) {
		String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk"
          +"lmnopqrstuvwxyz!@#$%&";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
    }
    
    public static String generateRandomInt(int len) {
		String chars = "0123456789";
		Random rnd = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(chars.charAt(rnd.nextInt(chars.length())));
		return sb.toString();
    }
    
    public void changeView(Control field, String view) throws IOException{
        //Cacher la fenetre Source
        field.getScene().getWindow().hide();
        //Afficher la fenetre de Cible
        Parent root = FXMLLoader.load(getClass().getResource("../views/"+view+".fxml"));
        Scene scene = new Scene(root);
        Stage stage=new Stage();
        stage.setTitle("Transfert Argent");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    
    public int loadWindow(Control field, String view) throws IOException{
        Stage stage = (Stage) field.getScene().getWindow(); 
        Parent root;
            root = FXMLLoader.load(getClass().getResource("../views/"+view+".fxml"));
            Scene scene = new Scene(root);
            stage=new Stage();
            stage.setTitle("Transfert Argent");
            stage.alwaysOnTopProperty();
            stage.centerOnScreen();
            stage.setScene(scene);
            stage.show();
        return 0;
    }
    
    public int closeWindow(Control field){
        Stage stage = (Stage) field.getScene().getWindow(); 
        stage.close();
        return 0;
    }
    
    public String validateTextFields(TextField firstField){
        String errormessage = null;
        boolean field = firstField.getText() == null;
        if(field)
            errormessage = "Veuillez remplir les champs";
        else
            errormessage = "";
        return errormessage;
    }
    
    public String validateFields(TextField firstField, TextField secondField){
        return (firstField.getText().isEmpty() || secondField.getText().isEmpty()) ? "Veuillez remplir les champs" : "" ;
    }
    
    public static List<String> comboBoxCategoryListToString(List<Categorie> categories){
        ListIterator<Categorie> li = categories.listIterator();
        String categoryName = null;
        List<String> categoryNames = new ArrayList();
        while (li.hasNext()) {
            categoryName = li.next().getLibelle();
            categoryNames.add(categoryName);
        }
        return categoryNames;
    }
    
    public void refreshComboList(JFXComboBox<String> productCategoryNameComboBox, ObservableList<String> categories){
        productCategoryNameComboBox.setItems(categories);
    }
    
    
    public void assignValueToTableColumn(List<TableColumn<User, String>> tblcList, User user){
        //ListIterator<TableColumn<User, String>> li = tblcList.listIterator();
        //Field[] fields = user.getClass().getFields();
        for(Field f : user.getClass().getFields()){
            f.setAccessible(true);
        }
        /*
        while (li.hasNext()) {
            li.next().setCellValueFactory(new PropertyValueFactory<>(""));
        }*/
        for(int i = 1; i<=tblcList.size(); i++){
            tblcList.get(i).setCellValueFactory(new PropertyValueFactory<>(user.getClass().getFields()[i].getName()));
        }
    }
    
    public void clearTextfields(List<JFXTextField> fields){
        while(fields.listIterator().hasNext()){
            fields.listIterator().next().clear();
        }
    }
    
    
    public void sendEmail(String senderMail, String senderEmailPassword, String toEmail, String subject, String body){
	try{
            System.out.println("SimpleEmail Start");
	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.user", senderMail); 
            props.put("mail.smtp.password", senderEmailPassword);
            props.put("mail.smtp.port", "587"); 
            props.put("mail.debug", "true");
            props.put("mail.smtp.auth", "true"); 
            props.put("mail.smtp.starttls.enable","true"); 
            props.put("mail.smtp.EnableSSL.enable","true");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            
            //create Authenticator object to pass in Session.getInstance argument
            Authenticator auth = new Authenticator() {
                //override the getPasswordAuthentication method
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(senderMail, senderEmailPassword);
                }
            };

	    Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");

            msg.setFrom(new InternetAddress(senderMail));
            msg.setReplyTo(InternetAddress.parse(senderMail, false));
            msg.setSubject(subject, "UTF-8");
            
            msg.setContent(body, "text/html; charset=utf-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            System.out.println("Message is ready");
            Transport.send(msg);
            System.out.println("EMail Sent Successfully!!");
	} catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void notifificate(String title, String text) throws FileNotFoundException{
        Notifications notificationBuilder = Notifications.create()
				.title(title)
				.text(text)
				.graphic(new ImageView(new Image(new FileInputStream("C:\\Users\\Albéric\\Desktop\\cours ism\\3eme année\\java avancé\\projets\\global_expertise\\GLOBAL_EXPERTISE_PROJECT\\src\\views\\images\\category-list.png"))))
				.hideAfter(Duration.seconds(40))
				.position(Pos.BOTTOM_RIGHT)
				.onAction(new EventHandler<ActionEvent>(){

                                    @Override
                                    public void handle(ActionEvent arg0) {
                                            System.out.println("Notifiation is Clicked");
                                    }
					
				});
        notificationBuilder.show();
    }
    
}
