package com.example.homesecurity;

import classes.Change;
import classes.HeatSensor;
import enums.Locations;
import exceptions.EmptyFieldException;
import exceptions.LowerHigherThanUpperLimitException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.SaveHeatSensorThread;
import utils.DatabaseUtil;
import utils.FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InsertHeatSensorController {
    private static final Logger logger= LoggerFactory.getLogger(InsertHeatSensorController.class);

    @FXML
    private TextField heatSensorNameTextField;
    @FXML
    private TextField heatSensorLowerLimitTextField;
    @FXML
    private TextField heatSensorUpperLimitTextField;
    @FXML
    private TextField heatSensorModelTextField;
    @FXML
    private TextField heatSensorManufacturerTextField;
    @FXML
    private TextField heatSensorSerialNumberTextField;
    @FXML
    private DatePicker heatSensorManufacturingDateDatePicker;
    @FXML
    private ComboBox<String> heatSensorLocationComboBox;

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

        heatSensorLocationComboBox.setItems(observableTypeList);
        heatSensorLocationComboBox.setValue(observableTypeList.getFirst());

        heatSensorManufacturingDateDatePicker.setEditable(false);
    }

    public void saveNewHeatSensor(){
        String heatSensorName=heatSensorNameTextField.getText();
        String heatSensorModel=heatSensorModelTextField.getText();
        String lowerTemperatureLimitString=heatSensorLowerLimitTextField.getText();
        String upperTemperatureLimitString=heatSensorUpperLimitTextField.getText();
        String heatSensorManufacturer=heatSensorManufacturerTextField.getText();
        String heatSensorSerialNumber=heatSensorSerialNumberTextField.getText();
        LocalDate heatSensorManufacturingDate=heatSensorManufacturingDateDatePicker.getValue();

        try{

            saveNewHeatSensorHelper(heatSensorName, heatSensorModel, lowerTemperatureLimitString, upperTemperatureLimitString, heatSensorManufacturer, heatSensorSerialNumber, heatSensorManufacturingDate);

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
        catch(NumberFormatException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving object to database.");
            alert.setContentText("Invalid Number format");
            logger.error("Please fill in the valid number format.");
            alert.showAndWait();
        }

    }

    private void saveNewHeatSensorHelper(String heatSensorName, String heatSensorModel, String lowerTemperatureLimitString, String upperTemperatureLimitString,
                                         String heatSensorManufacturer, String heatSensorSerialNumber, LocalDate heatSensorManufacturingDate) throws EmptyFieldException {
        if(!heatSensorName.isEmpty()&&!heatSensorModel.isEmpty()&&!lowerTemperatureLimitString.isEmpty()
                &&!upperTemperatureLimitString.isEmpty()&&!heatSensorManufacturer.isEmpty()&&!heatSensorSerialNumber.isEmpty() && heatSensorManufacturingDate !=null){

            Float lowerTemperatureLimit=Float.parseFloat(lowerTemperatureLimitString);
            Float upperTemperatureLimit=Float.parseFloat(upperTemperatureLimitString);

            Locations deviceLocation=Locations.BACKYARD;

            switch(heatSensorLocationComboBox.getValue()) {
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

            HeatSensor sensor = (HeatSensor) HeatSensor.builder()
                    .deviceManufacturer(heatSensorManufacturer)
                    .sensorLowerLimit(lowerTemperatureLimit)
                    .sensorUpperLimit(upperTemperatureLimit)
                    .temperatureInC((float) 0)
                    .deviceType("Heat Sensor")
                    .deviceModel(heatSensorModel)
                    .deviceStatus(false)
                    .deviceSerialNumber(heatSensorSerialNumber)
                    .location(deviceLocation)
                    .deviceManufacturingDate(heatSensorManufacturingDate)
                    .deviceId(0)
                    .deviceName(heatSensorName)
                    .build();
            try{
                sensor.measure();
                sensor.setTemperatureInF(sensor.fahrenheitToCelsius(sensor.getTemperatureInC()));
                Change change=new Change(sensor.getDeviceName(),sensor.getDeviceType(),"Sensor: " + sensor.getDeviceName()+ " has been successfully saved", LocalDateTime.now());

                FileUtil.serializeHeatSensor(sensor);

                SaveHeatSensorThread saveHeatSensorThread=new SaveHeatSensorThread(sensor, change);

                AllDevicesController.observableDeviceList.add(sensor);

                Executor executor= Executors.newSingleThreadExecutor();
                executor.execute(saveHeatSensorThread);


                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information");
                alert.setHeaderText("Successful operation");
                alert.setContentText("Sensor: " + sensor.getDeviceName()+ " has been successfully saved");
                alert.showAndWait();
            }catch(LowerHigherThanUpperLimitException ex){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed operation");
                alert.setContentText("Lower limit is higher than the upper limit. Please try again.");
                alert.showAndWait();
            }

        }
        else{
            throw new EmptyFieldException();
        }
    }
}
