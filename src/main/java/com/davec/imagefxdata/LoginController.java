package com.davec.imagefxdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import javafx.scene.text.Text;

public class LoginController implements Initializable {

    @FXML
    public Button btnLogin;
    @FXML
    public TextField passcode;
    @FXML
    public Label ps_title;
    @FXML
    private String password;

    @FXML
    private void switchToGallery() throws IOException {
        System.out.println("Button Clicked");      
        String ps1 = passcode.getText().trim();
        boolean passwd = checkPassword(ps1);
        System.out.println(" password " + passwd);
        if (passwd) {
            App.setRoot("gallery");

        } else {
            ps_title.setText("Incorrect Passcode try again");
            passcode.setText("");
        }

    }

    

    private boolean checkPassword(String codeSubmit) throws IOException {

        BufferedReader br = null;
        boolean access = false;
        try {
            br = new BufferedReader(new FileReader("Res/passcode.txt"));
            String currentLine;
            //check if submitted password  is in file
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.trim().equalsIgnoreCase(codeSubmit.trim())) {
                    access = true;
                    currentLine = null;
                   // br.reset();
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            ps_title.setText("File Not Found");

        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            ps_title.setText("I/O exception");
        } finally {
            if(br != null){
                 br.close();                
            }           
        }
        return access;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        passcode.setAlignment(Pos.CENTER);

    }
}
