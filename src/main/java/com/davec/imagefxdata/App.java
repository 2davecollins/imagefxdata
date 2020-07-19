package com.davec.imagefxdata;


import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.layout.HBox;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    @Override
    public void start(Stage stage) throws IOException {

        stage.setTitle("Secure Code CA1");
        int i = 0;
       
        Thread t = new Thread(new ProcessStep(0,"folder"+i));
        t.start();
        scene = new Scene(loadFXML("login"), 680, 640);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        ArrayList<String> detail = new ArrayList<String>();
        launch();
    }

  
}
