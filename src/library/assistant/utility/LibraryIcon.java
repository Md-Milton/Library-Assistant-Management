
package library.assistant.utility;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import librarayassistantmanagement.MainController;

/**
 *
 * @author milton
 */
public class LibraryIcon {
    private static final String IMAGE_LOCATION = "/icon/login.png";
    
    public static void setLoginIcon(Stage stage){
        stage.getIcons().add(new Image(IMAGE_LOCATION));
    }
     public static void setMainWindowIcon(Stage stage){
         stage.getIcons().add(new Image("/icon/library.jpg"));
     }
     
       public void load(URL location, String title, Stage parentStage,String imagelocation) {
        try {
            Parent parent = FXMLLoader.load(location);
            Stage  stage = null;
            if(parentStage!=null){
                stage = parentStage;
            }else{
                stage = new Stage(StageStyle.DECORATED);
            }
           
            stage.setScene(new Scene(parent));
            stage.getIcons().add(new Image(imagelocation));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
