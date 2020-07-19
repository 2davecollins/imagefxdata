package com.davec.imagefxdata;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.commons.imaging.ImageReadException;

public class DetailController implements Initializable {

    @FXML
    public TextArea dl_data;
    @FXML
    public Label dl_title;
    @FXML
    public Label dl_error;
    @FXML
    public Button btnLogOff;

    public int index;
    private final ArrayList<String> dataList = new ArrayList<String>();
    private String[] pathnames;

    @FXML
    private void getImageData(String fileName) throws IOException {
        System.out.println(fileName);
        try {
            File myObj = new File("Res/text/"+fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                dl_data.appendText(data+"\n");
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            dl_error.setText("Ooops sorry");
        }

    }

    @FXML
    private void switchOff(ActionEvent event) throws IOException {
        //App.setRoot("login");
        Stage stage = (Stage) btnLogOff.getScene().getWindow();
        stage.close();
       // to end threads as well
        //System.exit(1);
    }

    @FXML
    private void switchBack(ActionEvent event) throws IOException {
        System.out.println("Switch back");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("gallery.fxml"));
        Parent tableViewParent = loader.load();
        Scene tableViewScene = new Scene(tableViewParent);
        GalleryController controller = loader.getController();
        controller.initData(dl_title.getText().trim(), index);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.setWidth(680);
        window.setHeight(640);
        window.show();
    }

    public void initData(String s, int x) {
        System.out.println("init");
        index = x;
        dl_title.setText(dataList.get(x));
        try {
            getImageData(dataList.get(x));
        } catch (IOException ex) {
            dl_error.setText("cant get info");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dl_error.setText("");
        index = 0;
        try {
            File f = new File("Res/text/");
            pathnames = f.list();

        } catch (Exception e) {
            System.out.println("ex" + e);
            dl_error.setText("Cant Find Data");
        } finally {
            dataList.clear();
            dataList.addAll(Arrays.asList(pathnames));
        }
    }

}
