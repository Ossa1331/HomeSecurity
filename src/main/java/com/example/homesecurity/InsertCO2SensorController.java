package com.example.homesecurity;

import classes.CO2Sensor;
import classes.Change;
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
import threads.SaveCO2SensorThread;
import utils.DatabaseUtil;
import utils.FileUtil;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class InsertCO2SensorController {
    private static final Logger logger= LoggerFactory.getLogger(InsertCO2SensorController.class);

    @FXML
    private TextField co2SensorNameTextField;
    @FXML
    private TextField co2SensorModelTextField;
    @FXML
    private TextField co2SensorManufacturerTextField;
    @FXML
    private TextField co2SensorSerialNumberTextField;
    @FXML
    private DatePicker co2SensorManufacturingDateDatePicker;
    @FXML
    private ComboBox<String> co2SensorLocationComboBox;


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

        co2SensorLocationComboBox.setItems(observableTypeList);
        co2SensorLocationComboBox.setValue(observableTypeList.getFirst());

        co2SensorManufacturingDateDatePicker.setEditable(false);
    }

    public void saveNewCO2Sensor(){
        String co2SensorName=co2SensorNameTextField.getText();
        String co2SensorModel=co2SensorModelTextField.getText();
        String co2SensorManufacturer=co2SensorManufacturerTextField.getText();
        String co2SensorSerialNumber=co2SensorSerialNumberTextField.getText();
        LocalDate co2SensorManufacturingDate=co2SensorManufacturingDateDatePicker.getValue();

        try{

            saveNewCO2SensorHelper(co2SensorName, co2SensorModel, co2SensorManufacturer, co2SensorSerialNumber, co2SensorManufacturingDate);

        }catch(EmptyFieldException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            String message="Some fields were left empty";
            alert.setTitle("Error");
            alert.setHeaderText("Error saving object to database.");
            alert.setContentText("Please fill all the fields");
            logger.error(message,ex);
            alert.showAndWait();
        }



    }

    private void saveNewCO2SensorHelper(String co2SensorName, String co2SensorModel, String co2SensorManufacturer, String co2SensorSerialNumber, LocalDate co2SensorManufacturingDate) throws EmptyFieldException{
        if(!co2SensorName.isEmpty()&&!co2SensorModel.isEmpty()&& !co2SensorManufacturer.isEmpty()&& !co2SensorSerialNumber.isEmpty()&& co2SensorManufacturingDate !=null){
            Locations deviceLocation=Locations.BACKYARD;

            switch(co2SensorLocationComboBox.getValue()) {
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

            CO2Sensor sensor = (CO2Sensor) CO2Sensor.builder()
                    .deviceManufacturer(co2SensorManufacturer)
                    .deviceType("CO2 Sensor")
                    .deviceModel(co2SensorModel)
                    .deviceStatus(false)
                    .deviceSerialNumber(co2SensorSerialNumber)
                    .location(deviceLocation)
                    .deviceManufacturingDate(co2SensorManufacturingDate)
                    .deviceId(0)
                    .deviceName(co2SensorName)
                    .build();

            FileUtil.serializeCO2Sensor(sensor);
            Change change=new Change(sensor.getDeviceName(),sensor.getDeviceType(),"Sensor: " + sensor.getDeviceName()+ " has been successfully saved", LocalDateTime.now());
            SaveCO2SensorThread saveCO2SensorThread=new SaveCO2SensorThread(sensor, change);

            Executor executor= Executors.newSingleThreadExecutor();
            executor.execute(saveCO2SensorThread);

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
