package com.davec.imagefxdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GalleryController implements Initializable {

    @FXML
    public Button nextButton;
    @FXML
    public Button prevButton;
    @FXML
    public Button randButton;
    @FXML
    public Label gal_title;
    @FXML
    public Label gal_error;
    @FXML
    public ImageView img_holder;

    private ArrayList<String> imageList = new ArrayList<String>();

    private String[] pathnames;
    private int index;
    private String name;

    @FXML
    private void switchToNext() throws IOException {
        index = getIndexFromArrayList(++index);
        setImage(imageList.get(index));
    }

    @FXML
    private void switchToPrev() throws IOException {
        index = getIndexFromArrayList(--index);
        setImage(imageList.get(index));
    }

    @FXML
    private void switchToDetail(ActionEvent event) throws IOException {
        //App.setRoot("secondary");
        System.out.println("detail");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("detail.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        DetailController controller = loader.getController();
        controller.initData(gal_title.getText().trim(), index);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //scene = new Scene(loadFXML("primary"), 640, 680); 
        window.setScene(tableViewScene);
        window.setHeight(640);
        window.setWidth(680);
        window.show();

    }

    public int getIndexFromArrayList(int i) {
        int a;
        a = (i > 0 && i < imageList.size()) ? i : 0;
        gal_title.setText(imageList.get(a));
        System.out.println("a :" + a + " i " + i);
        return a;

    }

    public void setText(String msg) {
        System.out.println("msg :" + msg);
        msg = msg.trim();
        gal_title.setText(msg);
    }

    public void setImage(String name) {
        FileInputStream input;
        try {

            input = new FileInputStream("Res/images/" + name);
            Image image = new Image(input);
            img_holder.setImage(image);
            img_holder.setX(50);
            img_holder.setY(25);
            img_holder.setFitHeight(455);
            img_holder.setFitWidth(500);

        } catch (FileNotFoundException ex) {
            gal_error.setText("Ooooops something went wrong |");
        } catch (SecurityException ex) {
            gal_error.setText("Are you a Hacker ???????");
        }
    }

    public void initData(String s, int x) {
        System.out.println("init");
        // gal_error.setText(s);
        index = x;
        setImage(imageList.get(index));
        gal_title.setText(imageList.get(index));

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            File f = new File("Res/images/");
            pathnames = f.list();

        } catch (Exception e) {
            gal_error.setText("That is a problem working on it....");
        } finally {
            imageList.clear();
            for (String pathname : pathnames) {
                imageList.add(pathname);
            }
        }
        setImage(imageList.get(index));
        gal_title.setText(imageList.get(index));
        gal_error.setText("");
    }

}
