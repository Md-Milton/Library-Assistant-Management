/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.settings;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import library.assistant.database.DatabaseHandler;

/**
 *
 * @author milton
 */
public class SettingsMain extends Application{

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                   DatabaseHandler.getInstance();
            }
        }).start();
        Preferences.initConfig();
        
    }
    
    public static void main(String[]args){
        launch(args);
    }
}
