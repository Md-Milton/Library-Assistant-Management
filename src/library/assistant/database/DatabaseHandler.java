/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javax.swing.JOptionPane;
import library.assistant.ui.booklist.BookListController.Book;
import library.assistant.ui.memberlist.MemberlistController.Member;

/**
 *
 * @author milton.
 */
public class DatabaseHandler {

    private static DatabaseHandler handler = null;

    private static final String DB_URl = "jdbc:derby:library;create=true";
    private static Connection connection = null;
    private static Statement statement = null;

    private DatabaseHandler() {
        createConnectin();
        setUpBooktable();
        memberTable();
        issueTable();
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    public void createConnectin() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            connection = DriverManager.getConnection(DB_URl);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUpBooktable() {
        String TABLE_NAME = "BOOK";
        try {
            statement = connection.createStatement();
            DatabaseMetaData dbData = connection.getMetaData();
            ResultSet tables = dbData.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table: " + TABLE_NAME + " already exists ready for go now!");
            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + " id varchar(200) primary key,\n"
                        + " title varchar(200),\n"
                        + " author varchar(200),\n"
                        + " pulisher varchar(200),\n"
                        + " isAvail boolean default true"
                        + ")");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage() + "......setUp data base");
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
        } catch (Exception e) {
            System.out.println("Exception  at exceQuery : data handler " + e.getLocalizedMessage());
            return null;
        }

        return result;
    }

    public boolean execAction(String query) {
        try {
            statement = connection.createStatement();
            statement.execute(query);
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error " + e.getMessage(), "error occurred", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at exceAction " + e.getLocalizedMessage());
            return false;
        }
    }

    public void memberTable() {
        String TABLE_NAME = "MEMBER";
        try {
            statement = connection.createStatement();
            DatabaseMetaData tables = connection.getMetaData();
            ResultSet result = tables.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (result.next()) {
                System.out.println("Table: " + TABLE_NAME + " already exists ready for go now!");
            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + " id varchar(200) primary key,\n"
                        + " name varchar(200),\n"
                        + " mobile varchar(200),\n"
                        + " email varchar(200)"
                        + ")");
            }

        } catch (Exception e) {
            System.err.println(e.getMessage() + ".......setup member table");
        }
    }

    public void issueTable() {
        String TABLE_NAME = "ISSUE";
        try {
            statement = connection.createStatement();
            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, TABLE_NAME.toUpperCase(), null);
            if (tables.next()) {
                System.out.println("Table: " + TABLE_NAME + " already exists ready for go ");
            } else {
                statement.execute("CREATE TABLE " + TABLE_NAME + "("
                        + "bookId varchar(200) primary key,\n"
                        + "memberId varchar(200),\n"
                        + "issueTime timestamp default CURRENT_TIMESTAMP,\n"
                        + "renew_count integer default 0,\n"
                        + "FOREIGN KEY (bookId) REFERENCES BOOK(id),\n"
                        + "FOREIGN KEY (memberId) REFERENCES MEMBER(id)"
                        + ")");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean deleteBook(Book book) {

        try {
            String deleteQuery = "DELETE FROM BOOK WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, book.getId());
            int result = preparedStatement.executeUpdate();
            if (result == 1) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean checkIssue(Book book) {

        try {
            String query = "SELECT COUNT(*) FROM ISSUE WHERE bookId=?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getId());
            ResultSet rs = preparedStatement.executeQuery(query);
            while (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateBook(Book book) {
        try {
            String query = "UPDATE BOOK SET TITLE=?, AUTHOR=?, pulisher=? WHERE ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setString(3, book.getPublisher());
            preparedStatement.setString(4, book.getId());

            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateMember(Member member) {

        try {
            String query = "UPDATE MEMBER SET NAME = ?,MOBILE = ?,EMAIL = ? WHERE ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getMobile());
            preparedStatement.setString(3, member.getEmail());
            preparedStatement.setString(4, member.getId());
            
           int result = preparedStatement.executeUpdate();
           if(result>0){
               return true;
           }
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public ObservableList<PieChart.Data>getBookPieData(){
        ObservableList<PieChart.Data>data = FXCollections.observableArrayList();
        try{
            String query1 = "SELECT COUNT(*)FROM BOOK";
            String query2 = "SELECT COUNT(*)FROM ISSUE";
            ResultSet result = execQuery(query1);
            if(result.next()){
                int count = result.getInt(1);
                data.add(new PieChart.Data("Total Books ( "+count+" ) ", count));
            }
            result = execQuery(query2);
            if(result.next()){
                int count = result.getInt(1);
                data.add(new PieChart.Data("Issued Books( "+count+" ) ", count));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
    
    public  ObservableList<PieChart.Data>getMemberPIeData(){
        ObservableList<PieChart.Data>data = FXCollections.observableArrayList();
        try{
            String query1 = "SELECT COUNT(*)FROM MEMBER";
            String query2 = "SELECT COUNT(DISTINCT memberId)FROM ISSUE";
            ResultSet rs = execQuery(query1);
            if(rs.next()){
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Total Members ( "+count+" ) ", count));
            }
            rs = execQuery(query2);
            if(rs.next()){
                int count = rs.getInt(1);
                data.add(new PieChart.Data("Members,issued books ( "+count+" ) ", count));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return data;
    }
}
