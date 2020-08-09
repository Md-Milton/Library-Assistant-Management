
package library.assistant.ui.settings;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class SettingsController implements Initializable {

    @FXML
    private JFXTextField nDays;
    @FXML
    private JFXTextField fineperday;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private AnchorPane rootPane;
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getDefaultValues();
    }    

    private void getDefaultValues() {
       Preferences preferences = Preferences.getPreferences(); 
       nDays.setText(String.valueOf(preferences.getnDays()));
       fineperday.setText(String.valueOf(preferences.getFineperday()));
       username.setText(preferences.getUsername());
       password.setText(preferences.getPassword());
    }

    @FXML
    private void save(ActionEvent event) {
        int days = Integer.parseInt(nDays.getText());
        float fine = Float.parseFloat(fineperday.getText());
        String uname = username.getText();
        String pass = password.getText();
        
        Preferences preferences = Preferences.getPreferences();
        preferences.setnDays(days);
        preferences.setFineperday(fine);
        preferences.setUsername(uname);
        preferences.setPassword(pass);
        
        Preferences.updateConfigFile(preferences);
        
    }

    @FXML
    private void cancel(ActionEvent event) {
        Stage stage = (Stage)rootPane.getScene().getWindow();
        stage.close();
    }
    
}
