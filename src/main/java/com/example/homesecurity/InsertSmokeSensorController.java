package com.example.homesecurity;

import classes.Change;
import classes.SmokeSensor;
import enums.Locations;
import exceptions.EmptyFieldException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.SaveSmokeSensorThread;
import utils.DatabaseUtil;
import utils.FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InsertSmokeSensorController {
    private static final Logger logger= LoggerFactory.getLogger(InsertSmokeSensorController.class);

    @FXML
    private TextField smokeSensorNameTextField;
    @FXML
    private TextField smokeSensorModelTextField;
    @FXML
    private TextField smokeSensorManufacturerTextField;
    @FXML
    private TextField smokeSensorSerialNumberTextField;
    @FXML
    private DatePicker smokeSensorManufacturingDateDatePicker;
    @FXML
    private ComboBox<String> smokeSensorLocationComboBox;


    public void initialize(){
        List<String> locationList=new ArrayList<>();
        locationList.add("Dining room");
        locationList.add("Bathroom");
        locationList.add("Kitchen");
        locationList.add("Backyard");
        locationList.add("Terrace");
        locationList.add("Courtyard");
        locationList.add("Basement");
        locationList.add("Stairway");
        locationList.add("Utility room");
        locationList.add("Hallway");

        ObservableList<String> observableTypeList= FXCollections.observableList(locationList);

        smokeSensorLocationComboBox.setItems(observableTypeList);
        smokeSensorLocationComboBox.setValue(observableTypeList.getFirst());

        smokeSensorManufacturingDateDatePicker.setEditable(false);
    }

    public void saveNewSmokeSensor(){
        String smokeSensorName=smokeSensorNameTextField.getText();
        String smokeSensorModel=smokeSensorModelTextField.getText();
        String smokeSensorManufacturer=smokeSensorManufacturerTextField.getText();
        String smokeSensorSerialNumber=smokeSensorSerialNumberTextField.getText();
        LocalDate smokeSensorManufacturingDate=smokeSensorManufacturingDateDatePicker.getValue();

        try{
            if(!smokeSensorName.isEmpty()&&!smokeSensorModel.isEmpty()&&!smokeSensorManufacturer.isEmpty()&&!smokeSensorSerialNumber.isEmpty()&&smokeSensorManufacturingDate!=null){
                Locations deviceLocation=Locations.BACKYARD;

                switch(smokeSensorLocationComboBox.getValue()) {
                    case ("Bathroom"): {
                        deviceLocation = Locations.BATHROOM;
                        break;
                    }
                    case ("Bedroom"): {
                        deviceLocation = Locations.BEDROOM;
                        break;
                    }
                    case ("Dining room"): {
                        deviceLocation = Locations.DINING_ROOM;
                        break;
                    }
                    case ("Courtyard"): {
                        deviceLocation = Locations.COURTYARD;
                        break;
                    }
                    case ("Terrace"): {
                        deviceLocation = Locations.TERRACE;
                        break;
                    }
                    case ("Hallway"): {
                        deviceLocation = Locations.HALLWAY;
                        break;
                    }
                    case ("Kitchen"): {
                        deviceLocation = Locations.KITCHEN;
                        break;
                    }
                    case ("Basement"): {
                        deviceLocation = Locations.BASEMENT;
                        break;
                    }
                    case ("Stairway"): {
                        deviceLocation = Locations.STAIRWAY;
                        break;
                    }
                    case ("Utility room"): {
                        deviceLocation = Locations.UTILITY_ROOM;
                        break;
                    }
                }

                SmokeSensor sensor = (SmokeSensor) SmokeSensor.builder()
                        .deviceManufacturer(smokeSensorManufacturer)
                        .deviceType("Smoke Sensor")
                        .deviceModel(smokeSensorModel)
                        .deviceStatus(false)
                        .deviceSerialNumber(smokeSensorSerialNumber)
                        .location(deviceLocation)
                        .deviceManufacturingDate(smokeSensorManufacturingDate)
                        .deviceId(0)
                        .deviceName(smokeSensorName)
                        .build();

                sensor.measure();
                Change change=new Change(sensor.getDeviceName(),sensor.getDeviceType(),"Sensor: " + sensor.getDeviceName()+ " has been successfully saved", LocalDateTime.now());

                SaveSmokeSensorThread saveSmokeSensorThread=new SaveSmokeSensorThread(sensor, change);

                Executor executor= Executors.newSingleThreadExecutor();
                executor.execute(saveSmokeSensorThread);

                FileUtil.serializeSmokeSensor(sensor);

                AllDevicesController.observableDeviceList.add(sensor);

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information");
                alert.setHeaderText("Successful operation");
                alert.setContentText("Sensor: " + sensor.getDeviceName()+ " has been successfully saved");
                logger.info("Smoke sensor has been successfully connected");
                alert.showAndWait();

            }
            else{
                throw new EmptyFieldException();
            }
        } catch(EmptyFieldException ex){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving object to database.");
            alert.setContentText("Please fill all the fields");
            logger.error("Some fields were left empty while filling out the form");
            alert.showAndWait();


        }

    }
}
