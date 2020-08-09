/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libraryassistant.toolbar;

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
import javafx.scene.image.Image;
import javafx.stage.Stage;
import librarayassistantmanagement.MainController;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class ToolbarController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loadMember(ActionEvent event) {
        load("/library/assistant/ui/addmember/addmember.fxml", "Add New Member", "/icon/addmember.png");
    }


    @FXML
    private void loadMemberTable(ActionEvent event) {
        load("/library/assistant/ui/memberlist/memberlist.fxml", "Member List", "/icon/showmember.jpg");
    }
    
    @FXML
    private void loadAddBook(ActionEvent event) {
        load("/library/assistant/ui/addbook/addBook.fxml", "Add New Book", "/icon/addbook.png");
    }
    

    @FXML
    private void loadAddBookTable(ActionEvent event) {
         load("/library/assistant/ui/booklist/bookList.fxml", "Book List", "/icon/showbook.jpg");
    }

    @FXML
    private void loadSettings(ActionEvent event) {
        load("/library/assistant/ui/settings/settings.fxml", "Settings", "/icon/settings.png");

    }

    public void load(String location, String title, String imagelocation) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(location));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.getIcons().add(new Image(imagelocation));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
