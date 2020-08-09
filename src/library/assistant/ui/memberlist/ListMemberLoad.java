/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.memberlist;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author milton
 */
public class ListMemberLoad extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("memberlist.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        
        stage.setTitle("Member List");
        stage.show();
    }
    public static void main(String []args){
        launch(args);
    }
}
