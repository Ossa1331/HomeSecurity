package com.example.homesecurity;

import classes.*;
import exceptions.EmptyFieldException;
import exceptions.NoPrivilegeException;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.*;
import utils.DatabaseUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class AllDevicesController {


    private static final Logger logger= LoggerFactory.getLogger(AllDevicesController.class);

    @FXML
    private TextField deviceNameTextField;
    @FXML
    private ComboBox<String> deviceTypeComboBox;

    @FXML
    private TableView<Device> deviceTableView;
    @FXML
    private TableColumn<Device, String> deviceNameTableColumn;
    @FXML
    private TableColumn<Device, String> deviceStatusTableColumn;
    @FXML
    private TableColumn<Device, String> deviceModelTableColumn;
    @FXML
    private TableColumn<Device, String> deviceLocationTableColumn;
    @FXML
    private TableColumn<Device, String> deviceManufacturerTableColumn;
    @FXML
    private TableColumn<Device, String> deviceDateAddedtableColumn;
    @FXML
    private TableColumn<Device, String> deviceTypeTableColumn;
    @FXML
    private TableColumn<Device, String> deviceManufacturingDateTableColumn;


    public static List<Device> deviceList=DatabaseUtil.getAllDevices();

    private static final Executor executor= Executors.newSingleThreadExecutor();

    public static ObservableList<Device> observableDeviceList= FXCollections.observableArrayList(deviceList);


    public void initialize(){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter formatter2= DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        List<String> typeList=new ArrayList<>();
        typeList.add("Camera");
        typeList.add("CO2 Sensor");
        typeList.add("Glass Break Sensor");
        typeList.add("Heat Sensor");
        typeList.add("Motion Sensor");
        typeList.add("Smoke Sensor");

        ObservableList<String> observableTypeList=FXCollections.observableList(typeList);

        deviceTypeComboBox.setItems(observableTypeList);
        deviceTypeComboBox.setValue(observableTypeList.getFirst());

        deviceNameTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDeviceName()));

        deviceStatusTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().deviceStatusToString()));

        deviceModelTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getModel()));

        deviceLocationTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLocation().getLocation()));

        deviceManufacturerTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDeviceManufacturer()));

        deviceManufacturingDateTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDeviceManufacturingDate().format(formatter)));

        deviceDateAddedtableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDeviceAdded().format(formatter2)));

        deviceTypeTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDeviceType()));


        deviceTableView.setItems(observableDeviceList);

    }

    public void clearFilters(){
        List<Device> deviceList=DatabaseUtil.getAllDevices();

        ObservableList<Device> observableDeviceList= FXCollections.observableArrayList(deviceList);
        deviceTableView.setItems(observableDeviceList);
    }

    public void applyFilter(){
        String deviceType= deviceTypeComboBox.getValue();

        switch(deviceType){
            case("Camera"):{
                String deviceName=deviceNameTextField.getText();
                List<Camera> allCameras= DatabaseUtil.getAllCameras();
                List<Device> allCamerasCasted = new ArrayList<>(allCameras);

                List<Device> filteredCameras=allCamerasCasted.parallelStream()
                        .filter(c->c.getDeviceName().contains(deviceName))
                        .collect(Collectors.toList());

                ObservableList<Device> observableCameraList=FXCollections.observableArrayList(filteredCameras);

                deviceTableView.setItems(observableCameraList);
                break;
            }
            case("CO2 Sensor"):{
                String deviceName=deviceNameTextField.getText();
                List<CO2Sensor> allCO2Sensors= DatabaseUtil.getAllCO2Sensors();
                List<Device> allCO2SensorsCasted = new ArrayList<>(allCO2Sensors);

                List<Device> filteredCO2Sensors=allCO2SensorsCasted.parallelStream()
                        .filter(c->c.getDeviceName().contains(deviceName))
                        .collect(Collectors.toList());

                ObservableList<Device> observableCO2SensorList=FXCollections.observableArrayList(filteredCO2Sensors);

                deviceTableView.setItems(observableCO2SensorList);
                break;
            }
            case("Glass Break Sensor"):{
                String deviceName=deviceNameTextField.getText();

                List<GlassBreakSensor> allGlassBreakSensors= DatabaseUtil.getAllGlassBreakSensors();
                List<Device> allGlassBreakSensorsCasted = new ArrayList<>(allGlassBreakSensors);

                List<Device> filteredGlassBreakSensors=allGlassBreakSensorsCasted.parallelStream()
                        .filter(c->c.getDeviceName().contains(deviceName))
                        .collect(Collectors.toList());

                ObservableList<Device> observableGlassBreakSensorList=FXCollections.observableArrayList(filteredGlassBreakSensors);

                deviceTableView.setItems(observableGlassBreakSensorList);
                break;
            }
            case("Heat Sensor"):{
                String deviceName=deviceNameTextField.getText();
                List<HeatSensor> allHeatSensors= DatabaseUtil.getAllHeatSensors();
                List<Device> allHeatSensorsCasted = new ArrayList<>(allHeatSensors);

                List<Device> filteredHeatSensors=allHeatSensorsCasted.parallelStream()
                        .filter(c->c.getDeviceName().contains(deviceName))
                        .collect(Collectors.toList());

                ObservableList<Device> observableHeatSensorList=FXCollections.observableArrayList(filteredHeatSensors);

                deviceTableView.setItems(observableHeatSensorList);
                break;
            }
            case("Motion Sensor"):{
                String deviceName=deviceNameTextField.getText();

                List<MotionSensor> allMotionSensors= DatabaseUtil.getAllMotionSensors();
                List<Device> allMotionSensorsCasted = new ArrayList<>(allMotionSensors);

                List<Device> filteredMotionSensors=allMotionSensorsCasted.parallelStream()
                        .filter(c->c.getDeviceName().contains(deviceName))
                        .collect(Collectors.toList());

                ObservableList<Device> observableMotionSensorList=FXCollections.observableArrayList(filteredMotionSensors);

                deviceTableView.setItems(observableMotionSensorList);
                break;
            }
            case("Smoke Sensor"):{
                String deviceName=deviceNameTextField.getText();

                List<SmokeSensor> allSmokeSensors= DatabaseUtil.getAllSmokeSensors();
                List<Device> allSmokeSensorsCasted = new ArrayList<>(allSmokeSensors);

                List<Device> filteredSmokeSensors=allSmokeSensorsCasted.parallelStream()
                        .filter(c->c.getDeviceName().contains(deviceName))
                        .collect(Collectors.toList());

                ObservableList<Device> observableSmokeSensorList=FXCollections.observableArrayList(filteredSmokeSensors);

                deviceTableView.setItems(observableSmokeSensorList);
                break;
            }
        }
        logger.info("Successfully applied a filter on All Devices Screen");
    }

    public void deleteDevice(){
        try{
            if(LoginScreenController.currentUser.getAdministrator()){
                try {
                    Device device=getClickedDevice();

                    observableDeviceList.remove(device);
                    Change change=new Change(device.getDeviceName(), device.getDeviceType(), "Device: " + device.getDeviceName() + " has successfully been deleted.", LocalDateTime.now());
                    DeleteDeviceThread thread=new DeleteDeviceThread(device,change);
                    executor.execute(thread);

                }
                catch(NullPointerException ex){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to retrieve the device");
                    alert.setContentText("Please click the device and try again.");
                    alert.showAndWait();
                    logger.error("The device on the main screen has not been clicked");
                }
            } else{
                throw new NoPrivilegeException();
            }
        } catch(NoPrivilegeException ex){

            logger.error("No privileges to disconnect sensors");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No privileges for this account");
            alert.setContentText("Please log in as an administrator");
            alert.showAndWait();

        }
    }

    public void updateDevice(){
        try{
            if(LoginScreenController.currentUser.getAdministrator()){
                try {
                    Device device=getClickedDevice();

                    switch(device.getDeviceType()){
                        case("Camera"):{
                            TextInputDialog dialog= new TextInputDialog();
                            dialog.setTitle("Rename Camera");
                            dialog.setHeaderText("Enter The new name for the camera: ");
                            dialog.setContentText("New Name: ");
                            String result;

                            try{
                                result=dialog.showAndWait().get();

                                try{
                                    if(!result.isEmpty()){
                                        Change change=new Change(device.getDeviceName(), device.getDeviceType(), "Camera: " + device.getDeviceName() + " has successfully been updated.", LocalDateTime.now());
                                        device.setDeviceName(result);
                                        UpdateNameCamera thread=new UpdateNameCamera((Camera) device, result, change);
                                        executor.execute(thread);
                                    }
                                    else{
                                        throw new EmptyFieldException();
                                    }
                                }catch( EmptyFieldException ex){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error");
                                    alert.setHeaderText("Text field is empty");
                                    alert.setContentText("Please fill out the empty text field to update the name");
                                    alert.showAndWait();
                                }
                            }
                            catch(Exception ignored){

                            }

                            break;
                        }
                        case("Glass Break Sensor"):{
                            TextInputDialog dialog= new TextInputDialog();
                            dialog.setTitle("Rename Glass Break Sensor");
                            dialog.setHeaderText("Enter The new name for the Glass Break Sensor: ");
                            dialog.setContentText("New Name: ");

                            String result;
                            try{
                                result=dialog.showAndWait().get();

                                try{
                                    if(!result.isEmpty()){
                                        Change change=new Change(device.getDeviceName(), device.getDeviceType(), "Glass Break Sensor: " + device.getDeviceName() + " has successfully been updated.", LocalDateTime.now());
                                        device.setDeviceName(result);
                                        UpdateNameGlassBreakSensor thread=new UpdateNameGlassBreakSensor((GlassBreakSensor) device, result, change);
                                        executor.execute(thread);
                                    }
                                    else{
                                        throw new EmptyFieldException();
                                    }
                                }catch( EmptyFieldException ex){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error");
                                    alert.setHeaderText("Text field is empty");
                                    alert.setContentText("Please fill out the empty text field to update the name");
                                    alert.showAndWait();
                                }
                            }
                            catch(Exception ignored){

                            }
                            break;
                        }
                        case("CO2 Sensor"):{
                            TextInputDialog dialog= new TextInputDialog();
                            dialog.setTitle("Rename CO2 Sensor");
                            dialog.setHeaderText("Enter The new name for the CO2 Sensor: ");
                            dialog.setContentText("New Name: ");
                            String result;

                            try{
                                result=dialog.showAndWait().get();
                                try{
                                    if(!result.isEmpty()){
                                        Change change=new Change(device.getDeviceName(), device.getDeviceType(), "CO2 Sensor: " + device.getDeviceName() + " has successfully been updated.", LocalDateTime.now());
                                        device.setDeviceName(result);
                                        UpdateNameCO2Sensor thread=new UpdateNameCO2Sensor((CO2Sensor) device, result, change);
                                        executor.execute(thread);

                                    }
                                    else{
                                        throw new EmptyFieldException();
                                    }
                                }catch( EmptyFieldException ex){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error");
                                    alert.setHeaderText("Text field is empty");
                                    alert.setContentText("Please fill out the empty text field to update the name");
                                    alert.showAndWait();
                                }
                                break;
                            }
                            catch(Exception ignored){

                            }

                        }
                        case("Heat Sensor"):{
                            TextInputDialog dialog= new TextInputDialog();
                            dialog.setTitle("Rename Heat Sensor");
                            dialog.setHeaderText("Enter The new name for the Heat Sensor: ");
                            dialog.setContentText("New Name: ");

                            String result;
                            try{
                                result=dialog.showAndWait().get();

                                try{
                                    if(!result.isEmpty()){
                                        Change change=new Change(device.getDeviceName(), device.getDeviceType(), "Heat Sensor: " + device.getDeviceName() + " has successfully been updated.", LocalDateTime.now());
                                        device.setDeviceName(result);
                                        UpdateNameHeatSensor thread=new UpdateNameHeatSensor((HeatSensor) device, result, change);
                                        executor.execute(thread);

                                    }
                                    else{
                                        throw new EmptyFieldException();
                                    }
                                }catch( EmptyFieldException ex){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error");
                                    alert.setHeaderText("Text field is empty");
                                    alert.setContentText("Please fill out the empty text field to update the name");
                                    alert.showAndWait();
                                }
                            }
                            catch(Exception ignored){

                            }
                            break;
                        }
                        case("Smoke Sensor"):{
                            TextInputDialog dialog= new TextInputDialog();
                            dialog.setTitle("Rename Smoke Sensor");
                            dialog.setHeaderText("Enter The new name for the Smoke Sensor: ");
                            dialog.setContentText("New Name: ");

                            String result;
                            try{
                                result=dialog.showAndWait().get();

                                try{
                                    if(!result.isEmpty()){
                                        Change change=new Change(device.getDeviceName(), device.getDeviceType(), "Smoke Sensor: " + device.getDeviceName() + " has successfully been updated.", LocalDateTime.now());
                                        device.setDeviceName(result);
                                        UpdateNameSmokeSensor thread=new UpdateNameSmokeSensor((SmokeSensor) device, result, change);
                                        executor.execute(thread);

                                    }
                                    else{
                                        throw new EmptyFieldException();
                                    }
                                }catch( EmptyFieldException ex){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error");
                                    alert.setHeaderText("Text field is empty");
                                    alert.setContentText("Please fill out the empty text field to update the name");
                                    alert.showAndWait();
                                }
                                break;
                            }
                            catch(Exception ignored){

                            }
                        }
                        case("Motion Sensor"):{
                            TextInputDialog dialog= new TextInputDialog();
                            dialog.setTitle("Rename Motion Sensor");
                            dialog.setHeaderText("Enter The new name for the Motion Sensor: ");
                            dialog.setContentText("New Name: ");

                            String result;
                            try{
                                result=dialog.showAndWait().get();
                                try{
                                    if(!result.isEmpty()){
                                        Change change=new Change(device.getDeviceName(), device.getDeviceType(), "Motion Sensor: " + device.getDeviceName() + " has successfully been updated.", LocalDateTime.now());
                                        device.setDeviceName(result);
                                        UpdateNameMotionSensor thread=new UpdateNameMotionSensor((MotionSensor) device, result, change);
                                        executor.execute(thread);

                                    }
                                    else{
                                        throw new EmptyFieldException();
                                    }
                                }catch( EmptyFieldException ex){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error");
                                    alert.setHeaderText("Text field is empty");
                                    alert.setContentText("Please fill out the empty text field to update the name");
                                    alert.showAndWait();
                                }
                            }
                            catch(Exception ignored){

                            }
                            break;
                        }
                    }
                }
                catch(NullPointerException ex){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Failed to retrieve the device");
                    alert.setContentText("Please click the device and try again.");
                    alert.showAndWait();
                }
            } else{
                throw new NoPrivilegeException();
            }
        } catch(NoPrivilegeException ex){

            logger.error("No privileges to access Insert Heat Sensor Screen");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No privileges for this account");
            alert.setContentText("Please log in as an administrator");
            alert.showAndWait();

        }
    }
    public void searchByName(){
        List<Device> deviceList=DatabaseUtil.getAllDevices();

        String deviceName=deviceNameTextField.getText();

        List<Device> filteredDevices=deviceList.parallelStream()
                .filter(c->c.getDeviceName().contains(deviceName))
                .collect(Collectors.toList());

        ObservableList<Device> observableDeviceList=FXCollections.observableArrayList(filteredDevices);

        deviceTableView.setItems(observableDeviceList);
    }
    public Device getClickedDevice(){

        TableView.TableViewSelectionModel<Device> selectionModel = deviceTableView.getSelectionModel();

        return selectionModel.getSelectedItem();
    }
    public void deviceControl(){
        try{
            deviceControlHelper();
        } catch(NoPrivilegeException ex){
            logger.error("No privileges to access change device status");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error setting status");
            alert.setHeaderText("No privileges for this account");
            alert.setContentText("Please log in as an administrator");
            alert.showAndWait();
        }


    }
    private void deviceControlHelper() throws NoPrivilegeException {
        if(LoginScreenController.currentUser.getAdministrator()){
            try {
                Device selectedDevice = getClickedDevice();

                Change change= new Change(selectedDevice.getDeviceName(), selectedDevice.getDeviceType(), "Device's ("+selectedDevice.getDeviceName()+ ") status has been changed.", LocalDateTime.now());
                DatabaseUtil.updateDevice(selectedDevice);
                DatabaseUtil.saveChange(change);

                observableDeviceList.remove(selectedDevice);
                selectedDevice.setDeviceStatus(!selectedDevice.getDeviceStatus());
                observableDeviceList.add(selectedDevice);

            }
            catch(NullPointerException ex){

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to retrieve the device");
                alert.setContentText("Please click the device and try again.");
                alert.showAndWait();

            }
        }
        else{
            throw new NoPrivilegeException();
        }
    }

}
