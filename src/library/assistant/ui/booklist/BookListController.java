/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.booklist;

import alertMessage.AlertMaker;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.addbook.AddBookController;
import librarayassistantmanagement.MainController;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class BookListController implements Initializable {
    
    ObservableList<Book>list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableView<Book> tableView;
    @FXML
    private TableColumn<Book, String> titleCol;
    @FXML
    private TableColumn<Book, String> idCol;
    @FXML
    private TableColumn<Book, String> authorCol;
    @FXML
    private TableColumn<Book, String> publisherCol;
    @FXML
    private TableColumn<Book,Boolean> availabilityCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     initCol();
     loadData();
    }    

    private void initCol() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
       
    }

    private void loadData() {
        list.clear();
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM BOOK";
        ResultSet rs =  databaseHandler.execQuery(query);
        try {
            while(rs.next()){
                String titlex = rs.getString("title");
                String idx = rs.getString("id");
                String authorx = rs.getString("author");
                String publisherx = rs.getString("pulisher");
                boolean avialx = rs.getBoolean("isAvail");
                list.add(new Book(titlex, idx, authorx, publisherx, avialx));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookListController.class.getName()).log(Level.SEVERE, null, ex);
        }
              //  tableView.getItems().setAll(list);     
              tableView.setItems(list);
    }

   

    @FXML
    private void deleteBook(ActionEvent event) {
        Book selectBook = tableView.getSelectionModel().getSelectedItem();
        
        if(selectBook ==null){
            AlertMaker.showErrorAlert("No Book is Selected", "Please Select a Book to Delete!!");
        }
        if(DatabaseHandler.getInstance().checkIssue(selectBook))
        {
        AlertMaker.showErrorAlert("Can be deleted", "This book is already been issued");
        }
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Book Deletion");
       
        alert.setContentText("Are you sure to delete the book \n"+selectBook.getTitle());
        Optional<ButtonType>response = alert.showAndWait();
        if(response.get()==ButtonType.OK){
            boolean delete = DatabaseHandler.getInstance().deleteBook(selectBook);
            if(delete){
                AlertMaker.showInformationAlert("Book:- ",selectBook.getTitle()+" is deleted !!");
                list.remove(selectBook);
            }
            else{
                AlertMaker.showCancelledAlert("Fialed:-", selectBook.getTitle()+" could not be deleted!!!");
            }
        }else{
         AlertMaker.showErrorAlert("Book Deletion","Book deletion is cancelled!!!" );
        }
    }

    @FXML
    private void editBook(ActionEvent event){
        Book editbook = tableView.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/assistant/ui/addbook/addBook.fxml"));
            Parent parent = loader.load();
            
            AddBookController controller = loader.getController();
            controller.getDataForEdit(editbook);
            
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.getIcons().add(new Image("/icon/addbook.png"));
            stage.show();
            stage.setOnCloseRequest((e)->{
                refreshData(); 
            });
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void refreshData(){
        loadData();
    }
    
   public static class Book{
        private  final SimpleStringProperty title;
        private final SimpleStringProperty id;
        private final SimpleStringProperty author;
        private final SimpleStringProperty publisher;
        private final SimpleBooleanProperty availability;
        
        public Book(String title,String id,String author,String pub,boolean avial){
            this.title = new SimpleStringProperty(title);
            this.id = new SimpleStringProperty(id);
            this.author = new SimpleStringProperty(author);
            this.publisher = new SimpleStringProperty(pub);
            this.availability = new SimpleBooleanProperty(avial);
        }

        public String getTitle() {
            return title.get();
        }

        public String getId() {
            return id.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getPublisher() {
            return publisher.get();
        }

        public boolean getAvailability() {
            return availability.get();
        }
        
        
    }
}
