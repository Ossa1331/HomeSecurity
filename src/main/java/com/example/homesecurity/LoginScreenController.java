package com.example.homesecurity;

import classes.User;
import exceptions.EmptyFieldException;
import exceptions.WrongCredentialsException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.AuthenticationUtil;

import java.io.IOException;

public class LoginScreenController {
    public static Boolean userIdentified=false;
    public static User currentUser;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private CheckBox isAdministratorCheckBox;


    public void loginUser(){
        String username=usernameTextField.getText();
        String password=passwordPasswordField.getText();


        try{
            if(!username.isEmpty()&&!password.isEmpty()){
                try{
                    if(AuthenticationUtil.authenticate(username,password)){
                        Boolean isAdmin=AuthenticationUtil.isAdmin(username);
                        User user=new User(username,password,isAdmin);
                        userIdentified=true;
                        currentUser=user;
                        showAllDevicesScreen();
                    }
                    else{
                        throw new WrongCredentialsException();
                    }

                }
                catch(WrongCredentialsException ex){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Unsuccessful login attempt");
                    alert.setContentText("Please try again");
                    alert.showAndWait();
                }
            }
            else{
                throw new EmptyFieldException();
            }
        }catch(EmptyFieldException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unsuccessful login attempt");
            alert.setContentText("Please fill in all of the fields.");
            alert.showAndWait();
        }

    }

    public void registerUser(){
        String username=usernameTextField.getText();
        String password=passwordPasswordField.getText();

        Boolean isAdmin=isAdministratorCheckBox.isSelected();

        try{
            if(!username.isEmpty()&&!password.isEmpty()){
                AuthenticationUtil.addUser(username,password,isAdmin);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Register Successful");
                alert.setHeaderText("Register Successful");
                alert.setContentText("You have been registered");
                alert.showAndWait();
            }
            else{
                throw new EmptyFieldException();
            }
        } catch(EmptyFieldException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Unsuccessful register attempt");
            alert.setContentText("Please fill in all of the fields.");
            alert.showAndWait();
        }
    }

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
}
