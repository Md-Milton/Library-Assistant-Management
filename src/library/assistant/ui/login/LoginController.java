/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import library.assistant.ui.settings.Preferences;
import library.assistant.utility.LibraryIcon;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class LoginController implements Initializable {

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private AnchorPane rootPane;

    Preferences preferences;
    @FXML
    private Label label;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        preferences = Preferences.getPreferences();
    }    

    @FXML
    private void login(ActionEvent event) {
        
        String name = username.getText();
        String pass = DigestUtils.shaHex(password.getText());
        if(name.equals(preferences.getUsername())&& pass.equals(preferences.getPassword())){
           closeStage();
            loadMain();  
        }else{
            
            username.getStyleClass().add("wrong-credentials");
            password.getStyleClass().add("wrong-credentials");
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("Username/password is not correct!!!");
//            alert.showAndWait();
//            label.setText("Username/password is not correct!!");
//            label.setStyle("-fx-background-color: #8AB06B;-fx-text-fill:#e7e7e7;-fx-font-size:15px;");
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
        System.exit(0);
    }
    public void loadMain(){
        try {
                Parent root = FXMLLoader.load(getClass().getResource("/libraryassistant/main.fxml"));
                Stage mainstage = new Stage();
                mainstage.setScene(new Scene(root));
                mainstage.setTitle("Library Assistant System");
                LibraryIcon.setMainWindowIcon(mainstage);
                mainstage.show();
            } catch (IOException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
    }

    private void closeStage() {
        Stage stage = (Stage)username.getScene().getWindow();
        stage.close();
            
    }
}
