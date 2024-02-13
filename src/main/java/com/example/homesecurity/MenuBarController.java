package com.example.homesecurity;

import exceptions.NoPrivilegeException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class MenuBarController {

    private static final Logger logger= LoggerFactory.getLogger(MenuBarController.class);

    public void showAllDevicesScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("all-devices.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            HelloApplication.getMainStage().setTitle("Device Control");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }catch(IOException e)
        {
            throw new RuntimeException();
        }
    }
    public void showInsertCameraScreen() {
        try{
            if(LoginScreenController.currentUser.getAdministrator()){
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Insert-Camera.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    HelloApplication.getMainStage().setTitle("Insert Camera");
                    HelloApplication.getMainStage().setScene(scene);
                    HelloApplication.getMainStage().show();
                }catch(IOException e)
                {
                    throw new RuntimeException();
                }
            }
            else{
                throw new NoPrivilegeException();
            }
        }catch (NoPrivilegeException ex){

            logger.error("No privileges to access Insert Camera",ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No privileges for this account");
            alert.setContentText("Please log in as an administrator");
            alert.showAndWait();

        }

    }
    public void showInsertGlassBreakScreen() {
        try{
            if(LoginScreenController.currentUser.getAdministrator()){
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Insert-GlassBreak.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    HelloApplication.getMainStage().setTitle("Insert Glass Break Sensor");
                    HelloApplication.getMainStage().setScene(scene);
                    HelloApplication.getMainStage().show();
                }catch(IOException e)
                {
                    throw new RuntimeException();
                }
            }
            else{
                throw new NoPrivilegeException();
            }
        }catch(NoPrivilegeException ex){

            logger.error("No privileges to access Insert Glass Break Sensor Screen",ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No privileges for this account");
            alert.setContentText("Please log in as an administrator");
            alert.showAndWait();

        }
    }
    public void showCO2Screen() {
        try{
            if(LoginScreenController.currentUser.getAdministrator()){
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Insert-CO2.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    HelloApplication.getMainStage().setTitle("Insert CO2 Sensor");
                    HelloApplication.getMainStage().setScene(scene);
                    HelloApplication.getMainStage().show();
                }catch(IOException e)
                {
                    throw new RuntimeException();
                }
            }
            else{
              throw new NoPrivilegeException();
            }
        }catch(NoPrivilegeException ex){

            logger.error("No privileges to access Insert CO2 Sensor Screen",ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No privileges for this account");
            alert.setContentText("Please log in as an administrator");
            alert.showAndWait();

        }

    }
    public void showMotionScreen() {
        try{
            if(LoginScreenController.currentUser.getAdministrator()){
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Insert-Motion.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    HelloApplication.getMainStage().setTitle("Insert Motion Sensor");
                    HelloApplication.getMainStage().setScene(scene);
                    HelloApplication.getMainStage().show();
                }catch(IOException e)
                {
                    throw new RuntimeException();
                }
            }
            else{
                throw new NoPrivilegeException();
            }
        } catch(NoPrivilegeException ex){

            logger.error("No privileges to access Insert Motion Sensor Screen", ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No privileges for this account");
            alert.setContentText("Please log in as an administrator");
            alert.showAndWait();

        }


    }
    public void showHeatScreen() {
        try{
            if(LoginScreenController.currentUser.getAdministrator()){
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Insert-Heat.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    HelloApplication.getMainStage().setTitle("Insert Heat Sensor");
                    HelloApplication.getMainStage().setScene(scene);
                    HelloApplication.getMainStage().show();
                }catch(IOException e)
                {
                    throw new RuntimeException();
                }
            }
            else{
                throw new NoPrivilegeException();
            }
        }catch(NoPrivilegeException ex){

            logger.error("No privileges to access Insert Heat Sensor Screen",ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No privileges for this account");
            alert.setContentText("Please log in as an administrator");
            alert.showAndWait();

        }

    }
    public void showSmokeScreen() {
        try{
            if(LoginScreenController.currentUser.getAdministrator()){
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Insert-Smoke.fxml"));
                try {
                    Scene scene = new Scene(fxmlLoader.load(), 900, 600);
                    HelloApplication.getMainStage().setTitle("Insert Smoke Sensor");
                    HelloApplication.getMainStage().setScene(scene);
                    HelloApplication.getMainStage().show();
                }catch(IOException e)
                {
                    throw new RuntimeException();
                }
            }
            else{
                throw new NoPrivilegeException();
            }
        }catch(NoPrivilegeException ex){

            logger.error("No privileges to access Insert Smoke Sensor Screen",ex);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No privileges for this account");
            alert.setContentText("Please log in as an administrator");
            alert.showAndWait();

        }

    }
    public void showChangeLogScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Change-Log.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            HelloApplication.getMainStage().setTitle("Change Log");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }catch(IOException e)
        {
            throw new RuntimeException();
        }
    }
    public void showMeasureScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Measure-Results.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            HelloApplication.getMainStage().setTitle("Measure Results");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }catch(IOException e)
        {
            throw new RuntimeException();
        }
    }
    public void showEventScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Event-Log.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            HelloApplication.getMainStage().setTitle("Event Log");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }catch(IOException e)
        {
            throw new RuntimeException();
        }
    }
    public void showLogInScreen() {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login-Screen.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), 900, 600);
            HelloApplication.getMainStage().setTitle("Please Log In");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }catch(IOException e)
        {
            throw new RuntimeException();
        }
    }
}
