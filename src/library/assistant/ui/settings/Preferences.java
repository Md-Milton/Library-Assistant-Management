/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.ui.settings;

import alertMessage.AlertMaker;
import com.google.gson.Gson;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author milton
 */
public class Preferences {
    
    private static final String file_config = "config.txt";
    private int nDays;
    private float fineperday;
    private String username;
    private String password;
    
    
    public Preferences(){
        nDays = 14;
        fineperday = 3;
        username = "admin";
        password = "admin";
       setPassword(password);
    }

    public int getnDays() {
        return nDays;
    }

    public void setnDays(int nDays) {
        this.nDays = nDays;
    }

    public float getFineperday() {
        return fineperday;
    }

    public void setFineperday(float fineperday) {
        this.fineperday = fineperday;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = DigestUtils.shaHex(password);
        //this.password = password;
    }
    
    
    
    //initial onfiguration file and set data in json format
    public static void initConfig() {
        Writer writer = null;
        try {
            Preferences preference = new Preferences();
            Gson gson = new Gson();
            writer = new FileWriter(file_config);
            gson.toJson(preference, writer);
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

           
    //update cofiguration file
        public static void updateConfigFile(Preferences preference) {
        Writer writer = null;
        try {
            Gson gson = new Gson();
            writer = new FileWriter(file_config);
            gson.toJson(preference, writer);

            AlertMaker.showInformationAlert("Success", "Settings updated");
        } catch (IOException ex) {
            Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            AlertMaker.showErrorAlert("Failed", "Cant save configuration file");
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(Preferences.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
        //get data from configuration file in json format
    public static Preferences getPreferences() {
        Gson gson = new Gson();
        Preferences preferences = new Preferences();
        try {
            preferences = gson.fromJson(new FileReader(file_config), Preferences.class);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Preferences.class.getName()).info("Config file is missing. Creating new one with default config");
            initConfig();
        }
        return preferences;
    }

}


