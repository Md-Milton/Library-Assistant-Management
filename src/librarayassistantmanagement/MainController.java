/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarayassistantmanagement;

import alertMessage.AlertMaker;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import library.assistant.database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author milton
 */
public class MainController implements Initializable {

    @FXML
    private HBox book_info;
    @FXML
    private Text bookName;
    @FXML
    private Text bookAuthor;
    @FXML
    private Text bookStatus;
    @FXML
    private HBox memeberinfo;
    @FXML
    private TextField book_id_info;

    DatabaseHandler databaseHandler;
    
    @FXML
    private TextField memberID;
    @FXML
    private Text mName;
    @FXML
    private Text mContact;
    @FXML
    private Text mEmail;
    @FXML
    private JFXTextField bookID;
    
    
    private ListView<String> isssueDataList;

    Boolean flag = false;

    @FXML
    private MenuItem close;
    @FXML
    private StackPane stackPane;
    @FXML
    private MenuItem fullScreen;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXDrawer drawer;
    
    
    PieChart bookPieChart;
    PieChart memberPieChart;
    
    //renew submission
    @FXML
    private Text memberName;
    @FXML
    private Text memberEmail;
    @FXML
    private Text mobile;
    @FXML
    private Text bName;
    @FXML
    private Text bAuthor;
    @FXML
    private Text bPublisher;
    @FXML
    private Text issueDate;
    @FXML
    private Text nDays;
    @FXML
    private Text fine;
    
    @FXML
    private JFXButton renewBook;
    @FXML
    private JFXButton submission;
    @FXML
    private HBox submissionContainer;
    @FXML
    private StackPane bookInfoContainer;
    @FXML
    private StackPane memberInfoContainer;
    @FXML
    private Tab bookIssue;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXDepthManager.setDepth(book_info, 2);
        JFXDepthManager.setDepth(memeberinfo, 2);
        databaseHandler = DatabaseHandler.getInstance();
        initDrawer();
        initPieGraph();
        
    }

    //....................... load diff window from main window.....................................
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

    //....................... display book info by member id.....................................
    @FXML
    private void bookInfo(ActionEvent event) {
        
        enableDisableGraph(false);
        
        String id = book_id_info.getText();
        String query = "SELECT * FROM BOOK WHERE id = '" + id + "'";
        ResultSet rs = databaseHandler.execQuery(query);
        Boolean flag = false;

        try {
            while (rs.next()) {
                String bName = rs.getString("title");
                String bAuthor = rs.getString("author");
                Boolean bStatus = rs.getBoolean("isAvail");

                bookName.setText(bName);
                bookAuthor.setText(bAuthor);
                String status = (bStatus) ? "Available" : "Not Available";
                bookStatus.setText(status);
                flag = true;

            }
            if (!flag) {
                bookName.setText("No such book available");
                bookAuthor.setText("");
                bookStatus.setText(" ");
            }

        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//....................... display member info by member id.....................................
    @FXML
    private void loadMemberinfo(ActionEvent event) {
        enableDisableGraph(false);
        
        String id = memberID.getText();
        String query = "SELECT * FROM MEMBER WHERE id = '" + id + "'";
        ResultSet rs = databaseHandler.execQuery(query);
        Boolean flag = false;

        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String contact = rs.getString("mobile");
                String email = rs.getString("email");

                mName.setText(name);
                mContact.setText(contact);
                mEmail.setText(email);

                flag = true;
            }
            if (!flag) {
                mName.setText("No such member available");
                mContact.setText(" ");
                mEmail.setText(" ");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//....................... book  issuing.....................................

    @FXML
    private void issueOperation(ActionEvent event) {
        String bookId = book_id_info.getText();
        String memberId = memberID.getText();

        
        JFXButton yesButton = new JFXButton("YES");
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event1)->{
        String str = "INSERT INTO ISSUE(memberId,bookId) VALUES ("
                    + "'" + memberId + "',"
                    + "'" + bookId + "')";
            String str2 = "UPDATE BOOK SET isAvail = false WHERE id = '" + bookId + "'";

            System.out.println(str + " " + str2);
            if (databaseHandler.execAction(str) && databaseHandler.execAction(str2)) {
               
                JFXButton button = new JFXButton("Done!");
                AlertMaker.showMaterial(stackPane, Arrays.asList(button), "Book is issued successfully!!", null);
                refreshGraph();
                
            } else {
                JFXButton button = new JFXButton("okay.I'll check!");
                AlertMaker.showMaterial(stackPane, Arrays.asList(button), "Book is not issued successfully!! please check!!", null);
                
            }
            clearIssueEntries();
    });
        
       JFXButton noButton = new JFXButton("NO");
       noButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event1)->{
           JFXButton button = new JFXButton("That's okay!");
            AlertMaker.showMaterial(stackPane, Arrays.asList(button), "Book is issue operation cancelled!!", null);
            clearIssueEntries();
       });
           
       AlertMaker.showMaterial(stackPane, Arrays.asList(yesButton,noButton), "Confirm Book Issue", "Are you sure to issue the book ' " + bookName.getText() + " ' TO-> " + mName.getText() + " ?");
            
       
    }
//.......................display book info and member info for issue book.....................................

    @FXML
    private void loadboookinfo(ActionEvent event) {
        
        clearEntries();
        
        
        JFXDepthManager.setDepth(bookID, 3);
        ObservableList<String> issueData = FXCollections.observableArrayList();
        flag = false;
        try{
        String id = bookID.getText();
        
        String myquery = "SELECT ISSUE.bookId,ISSUE.memberId,ISSUE.issueTime,ISSUE.renew_count,\n"
                +"BOOK.title,BOOK.author,BOOK.pulisher,\n"
                +"MEMBER.name,MEMBER.mobile,MEMBER.email\n"
                +"FROM ISSUE\n"
                +"LEFT JOIN BOOK\n"
                +"ON ISSUE.bookId=BOOK.ID\n"
                +"LEFT JOIN MEMBER\n"
                +"ON  ISSUE.memberId=MEMBER.ID\n"
                +"WHERE ISSUE.bookId='"+id+"'";
        
        ResultSet resultSet = databaseHandler.execQuery(myquery);
        if(resultSet.next()){
            memberName.setText("Name: "+resultSet.getString("name"));
            memberEmail.setText("Email: "+resultSet.getString("email"));
            mobile.setText("Contact: "+resultSet.getString("mobile"));
            
            bName.setText("Name: "+resultSet.getString("title"));
            bAuthor.setText("Author: "+resultSet.getString("author"));
            bPublisher.setText("Publisher: "+resultSet.getString("pulisher"));
            
            Timestamp issueTime = resultSet.getTimestamp("issueTime");
            Date issuedate = new Date(issueTime.getTime());
            Date date = new Date(issueTime.getDate());
            issueDate.setText(issuedate.toString());
            
            Long timeElapsed = System.currentTimeMillis() - issueTime.getTime();
            Long daysElapsed = TimeUnit.DAYS.convert(timeElapsed, TimeUnit.MILLISECONDS);
            nDays.setText("Total days: "+daysElapsed.toString());
            fine.setText("No fine yet");
            
            flag = true;
            enableDisable(true);
            submissionContainer.setOpacity(1);
            
        }
        else{
            JFXButton button = new JFXButton("Okay.I'll check!!");
            AlertMaker.showMaterial(stackPane, Arrays.asList(button),"No such book is issued yet!!!",null);
            
        
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        
     /*   
        String query = "SELECT * FROM ISSUE WHERE bookId = '" + id + "'";
        ResultSet rs = databaseHandler.execQuery(query);

        try {
            while (rs.next()) {
                String IbookId = id;
                String ImemberId = rs.getString("memberId");
                Timestamp issueTime = rs.getTimestamp("issueTime");
                int renewCount = rs.getInt("renew_count");

                issueData.add("Issue Time: " + issueTime.toGMTString());
                issueData.add("Renew count: " + renewCount);

                issueData.add("BOOK INFORMATION");
                query = "SELECT * FROM BOOK WHERE id = '" + IbookId + "'";
                ResultSet resultSet = databaseHandler.execQuery(query);
                while (resultSet.next()) {
                    issueData.add("Book Id: " + resultSet.getString("id"));
                    issueData.add("Book Name: " + resultSet.getString("title"));
                    issueData.add("Book Author: " + resultSet.getString("author"));
                    issueData.add("Book Publisher: " + resultSet.getString("pulisher"));
                }

                issueData.add("MEMBER INFORMATION");
                query = "SELECT * FROM MEMBER WHERE id = '" + ImemberId + "'";
                resultSet = databaseHandler.execQuery(query);
                while (resultSet.next()) {
                    issueData.add("Memebr ID: " + resultSet.getString("id"));
                    issueData.add("Name: " + resultSet.getString("name"));
                    issueData.add("Mobile: " + resultSet.getString("mobile"));
                    issueData.add("Email: " + resultSet.getString("email"));
                }
                flag = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

        isssueDataList.getItems().setAll(issueData);
*/
    }

    //.......................book submission operation.....................................
    @FXML
    private void booksubmission(ActionEvent event) {
        if (!flag) {
            JFXButton submitButton = new JFXButton("Okay!");
            AlertMaker.showMaterial(stackPane, Arrays.asList(submitButton), "Please select a book to submit!!!","Can't Submit Null a Book ");
            return;
        }

        JFXButton yesButton = new JFXButton("YES");
        
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev)->{
              String id = bookID.getText();
            String str = "DELETE  FROM ISSUE WHERE bookId='" + id + "'";
            String str1 = "UPDATE BOOK SET isAvail = true WHERE id ='" + id + "'";
            if (databaseHandler.execAction(str) && databaseHandler.execAction(str1)) {
              JFXButton submitButton = new JFXButton("DONE");
              AlertMaker.showMaterial(stackPane, Arrays.asList(submitButton), "Book Has Been Submitted!!!", null);
             
                enableDisable(false);
                submissionContainer.setOpacity(0);
             
            } else {
              JFXButton submitButton = new JFXButton("Okay!I'll check");
              AlertMaker.showMaterial(stackPane, Arrays.asList(submitButton), "Book Has Been Not Submitted!!!", null);
              
            }
        });
        
        
        JFXButton noButton = new JFXButton("NO");
        noButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ev)->{
              JFXButton submitButton = new JFXButton("Okay!");
              AlertMaker.showMaterial(stackPane, Arrays.asList(submitButton), "Book Submittion Cancelled!!!", null);
        });
        
        AlertMaker.showMaterial(stackPane, Arrays.asList(yesButton,noButton), "Confirm Submission ", "Are you sure to submit the book ??");
       
    }

    //.......................renew book operation.....................................
    @FXML
    private void renewBook(ActionEvent event) {
        if (!flag) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Please select a book to renew!!");
            alert.showAndWait();
            return;
        }
        String id = bookID.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm renew");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure to renew the book??");
        Optional<ButtonType> response = alert.showAndWait();
        if (response.get() == ButtonType.OK) {
            String query = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP,renew_count = renew_count+1 WHERE bookId='" + id + "'";
            if (databaseHandler.execAction(query)) {
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Book has been renewed!!!");
                alert1.showAndWait();
                loadboookinfo(null);
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Success");
                alert1.setHeaderText(null);
                alert1.setContentText("Book has not been renewed!!!");
                alert1.showAndWait();
            }
        } else {
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Canceled");
            alert1.setContentText("Book Renew Cancelled!");
            alert1.showAndWait();
        }
    }

    //.......................handling menu bar.....................................
    @FXML
    private void closeFromMenu(ActionEvent event) {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void viewFullScreen(ActionEvent event) {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.setFullScreen(!stage.isFullScreen());
    }

    private void initDrawer() {
        try {
            VBox toolbar = FXMLLoader.load(getClass().getResource("/libraryassistant/toolbar/toolbar.fxml"));
            drawer.setSidePane(toolbar);
           
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        HamburgerSlideCloseTransition task = new HamburgerSlideCloseTransition(hamburger);
        task.setRate(-1);
        hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (Event event) -> {
//           task.setRate(task.getRate()*-1);
//           task.play();
            
            drawer.toggle();
        });
       drawer.setOnDrawerOpening(event->{
        task.setRate(task.getRate()*-1);
        task.play();
        drawer.toFront();
        });
       drawer.setOnDrawerClosed(event->{
             drawer.toBack();
             task.setRate(task.getRate()*-1);
             task.play();
       });
    }

    private void clearEntries() {
      
        memberName.setText("");
        memberEmail.setText("");
        mobile.setText("");
        
        bName.setText("");
        bAuthor.setText("");
        bPublisher.setText("");
        
        issueDate.setText("");
        nDays.setText("");
        fine.setText("");
        enableDisable(false);
        submissionContainer.setOpacity(0);
        
    }
    
    private void enableDisable(boolean enable){
        if(enable){
            renewBook.setDisable(false);
            submission.setDisable(false);
        }else{
            renewBook.setDisable(true);
            submission.setDisable(true);
        }
    }

    private void clearIssueEntries() {
      book_id_info.clear();
      memberID.clear();
      
      bookName.setText("");
      bookAuthor.setText("");
      bookStatus.setText("");
      
      mName.setText("");
      mContact.setText("");
      mEmail.setText("");
      enableDisableGraph(true);
    }

    private void initPieGraph() {
        bookPieChart = new PieChart(databaseHandler.getBookPieData());
        memberPieChart = new PieChart(databaseHandler.getMemberPIeData());
        bookInfoContainer.getChildren().add(bookPieChart);
        memberInfoContainer.getChildren().add(memberPieChart);
        bookIssue.setOnSelectionChanged((Event event) -> {
            clearIssueEntries();
            if(bookIssue.isSelected()){
                refreshGraph();
            }
        });
    }

    
    
    private void enableDisableGraph(Boolean status){
        if(status){
            bookPieChart.setOpacity(1);
            memberPieChart.setOpacity(1);
        }else{
            bookPieChart.setOpacity(0);
            memberPieChart.setOpacity(0);
        }
    }
    
    private void refreshGraph(){
        bookPieChart.setData(databaseHandler.getBookPieData());
        memberPieChart.setData(databaseHandler.getMemberPIeData());
    }
}
