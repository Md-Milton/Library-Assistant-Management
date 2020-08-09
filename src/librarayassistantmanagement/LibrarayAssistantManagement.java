/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarayassistantmanagement;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.assistant.database.DatabaseHandler;
import library.assistant.utility.LibraryIcon;

/**
 *
 * @author milton
 */
public class LibrarayAssistantManagement extends Application{

    @Override
    public void start(Stage stage) throws Exception {
     
        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/ui/login/login.fxml"));
        stage.setScene(new Scene(root));
       
        LibraryIcon.setLoginIcon(stage);
        stage.show();
        new Thread(() -> {
            DatabaseHandler.getInstance();
        }).start();
        
    }
    public static void main(String[]args){
        launch(args);
    }
    
}
