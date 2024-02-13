package com.example.homesecurity;

import classes.Change;
import classes.MotionSensor;
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
import threads.SaveMotionSensorThread;
import utils.DatabaseUtil;
import utils.FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InsertMotionSensorController {
    private static final Logger logger= LoggerFactory.getLogger(InsertMotionSensorController.class);

    @FXML
    private TextField motionSensorNameTextField;
    @FXML
    private TextField motionSensorModelTextField;
    @FXML
    private TextField motionSensorManufacturerTextField;
    @FXML
    private TextField motionSensorSerialNumberTextField;
    @FXML
    private DatePicker motionSensorManufacturingDateDatePicker;
    @FXML
    private ComboBox<String> motionSensorLocationComboBox;

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

        motionSensorLocationComboBox.setItems(observableTypeList);
        motionSensorLocationComboBox.setValue(observableTypeList.getFirst());

        motionSensorManufacturingDateDatePicker.setEditable(false);
    }

    public void saveNewMotionSensor(){
        String motionSensorName=motionSensorNameTextField.getText();
        String motionSensorModel=motionSensorModelTextField.getText();
        String motionSensorManufacturer=motionSensorManufacturerTextField.getText();
        String motionSensorSerialNumber=motionSensorSerialNumberTextField.getText();
        LocalDate motionSensorManufacturingDate=motionSensorManufacturingDateDatePicker.getValue();


        try{

            saveNewMotionSensorHelper(motionSensorName, motionSensorModel, motionSensorManufacturer, motionSensorSerialNumber, motionSensorManufacturingDate);

        }
        catch(EmptyFieldException ex){

            Alert alert = new Alert(Alert.AlertType.ERROR);
            String message="Some fields were left empty";
            alert.setTitle("Error");
            alert.setHeaderText("Error saving object to database.");
            alert.setContentText("Please fill all the fields");
            logger.error(message,ex);
            alert.showAndWait();

        }


    }

    private void saveNewMotionSensorHelper(String motionSensorName, String motionSensorModel, String motionSensorManufacturer, String motionSensorSerialNumber, LocalDate motionSensorManufacturingDate) {
        if(!motionSensorName.isEmpty()&& !motionSensorModel.isEmpty()
                &&!motionSensorManufacturer.isEmpty()&&!motionSensorSerialNumber.isEmpty()&& motionSensorManufacturingDate !=null){

            Locations deviceLocation=Locations.BACKYARD;

            switch(motionSensorLocationComboBox.getValue()) {
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

            MotionSensor sensor = (MotionSensor) MotionSensor.builder()
                    .deviceManufacturer(motionSensorManufacturer)
                    .deviceType("Motion Sensor")
                    .deviceModel(motionSensorModel)
                    .deviceStatus(false)
                    .deviceSerialNumber(motionSensorSerialNumber)
                    .location(deviceLocation)
                    .deviceManufacturingDate(motionSensorManufacturingDate)
                    .deviceId(0)
                    .deviceName(motionSensorName)
                    .build();

            sensor.setAnomalyDetected(false);
            Change change=new Change(sensor.getDeviceName(),sensor.getDeviceType(),"Sensor: " + sensor.getDeviceName()+ " has been successfully saved", LocalDateTime.now());

            SaveMotionSensorThread saveMotionSensorThread=new SaveMotionSensorThread(sensor, change);

            Executor executor= Executors.newSingleThreadExecutor();
            executor.execute(saveMotionSensorThread);

            FileUtil.serializeMotionSensor(sensor);

            AllDevicesController.observableDeviceList.add(sensor);

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information");
            alert.setHeaderText("Successful operation");
            alert.setContentText("Sensor: " + sensor.getDeviceName()+ " has been successfully saved");
            alert.showAndWait();

        }
        else{
            throw new EmptyFieldException();
        }
    }

}
