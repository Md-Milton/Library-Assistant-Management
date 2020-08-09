/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.addbook;

import alertMessage.AlertMaker;
import librarayassistantmanagement.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.booklist.BookListController.Book;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class AddBookController implements Initializable {

//    @FXML
//    private VBox publisher;
    @FXML
    private JFXTextField title;
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField author;
     @FXML
    private JFXTextField publisher;
    @FXML
    private JFXButton saveButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private AnchorPane rootPane;
    
    private boolean isEditable = Boolean.FALSE;
    DatabaseHandler databaseHandler;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       databaseHandler = databaseHandler.getInstance();
       checkData();
    }    

    @FXML
    private void addBook(ActionEvent event) {
        
        String bookId = id.getText();
        String bookTitle = title.getText();
        String bookAuthor = author.getText();
        String bookPublisher = publisher.getText();
        
        if(bookId.isEmpty()|| bookTitle.isEmpty()||bookAuthor.isEmpty()||bookPublisher.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("please enter in all fields");
            alert.showAndWait();
            return;
        }
        
        if(isEditable){
            isEditable();
            return;
        }
//        statement.execute("CREATE TABLE "+TABLE_NAME+"("
//                        +" id varchar(200) primary key,\n"
//                        +" title varchar(200),\n"
//                        +" author varchar(200),\n"
//                        +" pulisher varchar(200),\n"
//                        +" isAvail boolean default true"
//                        +")");
        
        String query = "INSERT INTO BOOK VALUES ("
                +"'"+bookId+"',"
                +"'"+bookTitle+"',"
                +"'"+bookAuthor+"',"
                +"'"+bookPublisher+"',"
                +""+true+""
                +")";
        System.out.println(query);
        if(databaseHandler.execAction(query)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Success!");
            alert.showAndWait();
        }
        else //error
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed!");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
       Stage stage = (Stage) rootPane.getScene().getWindow();
       stage.close();
    }

    private void checkData() {
        String  query = "SELECT title FROM BOOK";    
        ResultSet rs = databaseHandler.execQuery(query);
        try {
            while(rs.next()){
                String titlex = rs.getString("title");
                System.out.println(titlex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AddBookController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
    }

public void getDataForEdit(Book book){
    title.setText(book.getTitle());
    id.setText(book.getId());
    author.setText(book.getAuthor());
    publisher.setText(book.getPublisher());
    id.setEditable(false);
    isEditable = Boolean.TRUE;
}

public void isEditable(){
    Book book = new Book(title.getText(), id.getText(), author.getText(), publisher.getText(), true);
    if(databaseHandler.updateBook(book)){
     AlertMaker.showCofirmationAlert("Success", "Book Updated!!");
    }else{
        AlertMaker.showErrorAlert("Failed", "Can't Update Book");
    }
}
    
    
}
