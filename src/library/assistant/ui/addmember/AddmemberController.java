/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.addmember;

import alertMessage.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.assistant.database.DatabaseHandler;
import library.assistant.ui.memberlist.MemberlistController;
import library.assistant.ui.memberlist.MemberlistController.Member;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class AddmemberController implements Initializable {

    DatabaseHandler databaseHandler;
    
    
    @FXML
    private JFXTextField id;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    
    @FXML
    private JFXButton save;
    @FXML
    private JFXButton cancel;
    @FXML
    private AnchorPane rootPane;
    private boolean isMemberEditable = false;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        databaseHandler = databaseHandler.getInstance();
    }    

    @FXML
    private void addMember(ActionEvent event) {
    String mId = id.getText();
    String mName = name.getText();
    String mMobile = mobile.getText();
    String mEmail = email.getText();
    
    Boolean flag = mId.isEmpty()||mName.isEmpty()||mMobile.isEmpty()||mEmail.isEmpty();
    if(flag){
        
        JFXDialogLayout dialogLayout = new JFXDialogLayout();
        
//        Alert alert =new Alert(Alert.AlertType.WARNING);
//        alert.setHeaderText(null);
//        alert.setContentText("Please enter in all fields");
//        alert.showAndWait();
        return;
    }
    
    if(isMemberEditable){
        isMemberEditable();
        return;
    }
    
        String query = "INSERT INTO MEMBER VALUES ("
                +"'"+mId+"',"
                +"'"+mName+"',"
                +"'"+mMobile+"',"
                +"'"+mEmail+"'"
                +")";
        
        System.out.println(query);
        if(databaseHandler.execAction(query)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Saved");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel(ActionEvent event) {
    
        Stage stage = (Stage)rootPane.getScene().getWindow();
        stage.close();
    }

    public void getMemberDataForEdit(Member member) {
        id.setText(member.getId());
        name.setText(member.getName());
        mobile.setText(member.getMobile());
        email.setText(member.getEmail());
        id.setEditable(false);
        isMemberEditable = Boolean.TRUE;

    }

    private void isMemberEditable() {
        Member member = new Member(id.getText(), name.getText(), mobile.getText(), email.getText());
        if(databaseHandler.updateMember(member))
        AlertMaker.showCofirmationAlert("Success","Memeber updated successfully!!" );
        else
        AlertMaker.showErrorAlert("Failed", "Member not updated!!");
        
        
    }
    
    
    
    
}
