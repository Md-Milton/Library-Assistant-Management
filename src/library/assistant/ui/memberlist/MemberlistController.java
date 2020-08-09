/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.memberlist;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.addmember.AddmemberController;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class MemberlistController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TableColumn<Member, String> idCol;
    @FXML
    private TableColumn<Member, String> nameCol;
    @FXML
    private TableColumn<Member, String> mobileCol;
    @FXML
    private TableColumn<Member, String> emailCol;

    ObservableList<Member>list = FXCollections.observableArrayList();
    @FXML
    private TableView<Member> tableView;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       initCol();
       loadMember();
    } 

    private void initCol() {
      idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
      nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
      mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobile"));
      emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    }

    private void loadMember() {
        list.clear();
        DatabaseHandler databaseHandler = DatabaseHandler.getInstance();
        String query = "SELECT * FROM MEMBER";
        ResultSet rs = databaseHandler.execQuery(query);
        
        try {
            while(rs.next()){
                String id = rs.getString("id");
                String name = rs.getString("name");
                String mobile = rs.getString("mobile");
                String email = rs.getString("email");
                
                list.add(new Member(id, name, mobile, email));
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(MemberlistController.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableView.getItems().setAll(list);
    }
    
    @FXML
    public void refreshMember(){
        loadMember();
    }
    
    @FXML
    public void editMember(ActionEvent event){
        Member editMember = tableView.getSelectionModel().getSelectedItem();
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/library/assistant/ui/addmember/addmember.fxml"));
            Parent parent = loader.load();
            
            AddmemberController controller = loader.getController();
            controller.getMemberDataForEdit(editMember);
            
            Stage stage  = new Stage();
            stage.setScene(new Scene(parent));
            stage.setTitle("Edit member");
            stage.getIcons().add(new Image("/icon/addmember.png"));
            stage.show();
             stage.setOnCloseRequest((e)->{
                refreshMember(); 
            });
            
        } catch (IOException ex) {
            Logger.getLogger(MemberlistController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void deleteMember(ActionEvent event){
        
    }
    
    
    public static class Member{
        private final SimpleStringProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty mobile;
        private final SimpleStringProperty email;
        
        public Member(String id,String name,String mobile,String email){
            this.id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.mobile = new SimpleStringProperty(mobile);
            this.email = new SimpleStringProperty(email);
        }

        public String getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public String getMobile() {
            return mobile.get();
        }

        public String getEmail() {
            return email.get();
        }
        
        
    }
    
}
