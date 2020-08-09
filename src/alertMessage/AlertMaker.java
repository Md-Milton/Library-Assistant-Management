
package alertMessage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 *
 * @author milton
 */
public class AlertMaker {
        public static void showInformationAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        //styleAlert(alert);
        alert.showAndWait();
    }


    public static void showErrorAlert(String title, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public static void showCofirmationAlert(String title,String contentText){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(title);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showCancelledAlert(String title,String contextText){
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(contextText);
        alert.showAndWait();
    }
    
    public static void showMaterial(StackPane stackPane,List<JFXButton>controlsButton,String header,String body){
           BoxBlur blur = new BoxBlur(3,3,3);
           
            JFXDialogLayout dialogLayout = new JFXDialogLayout();
            JFXDialog dialog = new JFXDialog(stackPane,dialogLayout,JFXDialog.DialogTransition.TOP);
           
            
            controlsButton.forEach(controls->{
                
                controls.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event1)->{
                dialog.close();
                });
            
            });
            
            dialogLayout.setActions(controlsButton);
            dialogLayout.setHeading(new Label(header));
            dialogLayout.setBody(new Label(body));
            dialog.show();
            
            dialog.setOnDialogClosed((dialogevent) -> {
               // stackPane.setEffect(null);
            });
            
            //stackPane.setEffect(blur);
    }
    
    public static void showMaterial(AnchorPane anchorPane,List<JFXButton>controlButton,String header,String body){
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
      //  JFXDialog dialog = new JFXDialog(anchorPane, dialogLayout, JFXDialog.DialogTransition.TOP);
        
    } 
}
