package com.example.homesecurity;

import classes.Camera;
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
import threads.SaveCameraThread;
import utils.DatabaseUtil;
import utils.FileUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class InsertCameraController {
    private static final Logger logger= LoggerFactory.getLogger(InsertCameraController.class);

    @FXML
    private TextField cameraNameTextField;
    @FXML
    private TextField cameraModelTextField;
    @FXML
    private TextField cameraManufacturerTextField;
    @FXML
    private TextField cameraSerialNumberTextField;
    @FXML
    private DatePicker cameraManufacturingDateDatePicker;
    @FXML
    private ComboBox<String> cameraLocationComboBox;

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

        ObservableList<String> observableLocationList= FXCollections.observableList(locationList);

        cameraLocationComboBox.setItems(observableLocationList);
        cameraLocationComboBox.setValue(observableLocationList.getFirst());

        cameraManufacturingDateDatePicker.setEditable(false);
    }

    public void saveNewCamera() {
        String cameraName = cameraNameTextField.getText();
        String cameraModel = cameraModelTextField.getText();
        String cameraManufacturer = cameraManufacturerTextField.getText();
        String cameraSerialNumber = cameraSerialNumberTextField.getText();
        LocalDate cameraManufacturingDate=cameraManufacturingDateDatePicker.getValue();

        try {
            saveNewCameraHelper(cameraName, cameraModel, cameraManufacturer, cameraSerialNumber, cameraManufacturingDate);
        } catch (EmptyFieldException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Error saving object to database.");
                    alert.setContentText("Please fill all the fields");
                    logger.error("Some fields were left empty");
                    alert.showAndWait();

        }
    }

    private void saveNewCameraHelper(String cameraName, String cameraModel, String cameraManufacturer, String cameraSerialNumber, LocalDate cameraManufacturingDate) throws EmptyFieldException {
        if (!cameraName.isEmpty() && !cameraModel.isEmpty() && !cameraManufacturer.isEmpty()
                && !cameraSerialNumber.isEmpty() && cameraManufacturingDate !=null) {
            Locations deviceLocation = Locations.BACKYARD;
            switch (cameraLocationComboBox.getValue()) {
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

                    Camera camera = (Camera) Camera.builder()
                            .deviceManufacturer(cameraManufacturer)
                            .deviceType("Camera")
                            .deviceModel(cameraModel)
                            .deviceStatus(false)
                            .deviceSerialNumber(cameraSerialNumber)
                            .location(deviceLocation)
                            .deviceManufacturingDate(cameraManufacturingDate)
                            .deviceId(0)
                            .deviceName(cameraName)
                            .build();

                    Change change = new Change(camera.getDeviceName(), camera.getDeviceType(), "Camera: " + camera.getDeviceName() + " has been successfully saved", LocalDateTime.now());
                    SaveCameraThread saveCameraThread = new SaveCameraThread(camera, change);

                    AllDevicesController.observableDeviceList.add(camera);
                    FileUtil.serializeCamera(camera);

                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(saveCameraThread);

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information");
                    alert.setHeaderText("Successful operation");
                    alert.setContentText("Camera: " + camera.getDeviceName() + " has been successfully saved");
                    alert.showAndWait();

                } else {
                    throw new EmptyFieldException();
                }
    }


}