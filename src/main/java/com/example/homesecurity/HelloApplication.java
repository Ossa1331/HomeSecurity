package com.example.homesecurity;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.AuthenticationUtil;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Stage mainStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login-Screen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        stage.setTitle("Hello");
        stage.setScene(scene);
        stage.show();

        mainStage=stage;
    }

    public static Stage getMainStage(){
        return mainStage;
    };

    public static void main(String[] args) {
        launch();

    }
}