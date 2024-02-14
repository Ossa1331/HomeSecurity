package com.example.homesecurity;

import classes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import utils.DatabaseUtil;
import utils.MultiObjectWrapper;

import java.util.ArrayList;
import java.util.List;

public class MeasurableController {

    @FXML
    private ComboBox<String> deviceTypeComboBox;
    @FXML
    private TableView<MultiObjectWrapper> deviceTableView;
    @FXML
    private TableColumn<MultiObjectWrapper,String> deviceNameTableColumn;
    @FXML
    private TableColumn<MultiObjectWrapper, String> deviceTypeTableColumn;
    @FXML
    private TableColumn<MultiObjectWrapper, String> deviceResultTableColumn;

    public void initialize(){
        List<String> typeList=new ArrayList<>();
        typeList.add("Camera");
        typeList.add("CO2 Sensor");
        typeList.add("Glass Break Sensor");
        typeList.add("Heat Sensor");
        typeList.add("Motion Sensor");
        typeList.add("Smoke Sensor");

        ObservableList<String> observableTypeList= FXCollections.observableList(typeList);

        deviceTypeComboBox.setItems(observableTypeList);
        deviceTypeComboBox.setValue(observableTypeList.getFirst());

        List<MultiObjectWrapper> allDevices = getAllDevices();


        deviceNameTableColumn.setCellValueFactory(cellData -> {
            Object object = cellData.getValue().object();
            return switch (object) {
                case Camera camera -> Bindings.createStringBinding(camera::getDeviceName);
                case CO2Sensor co2Sensor -> Bindings.createStringBinding(co2Sensor::getDeviceName);
                case GlassBreakSensor glassBreakSensor ->
                        Bindings.createStringBinding((glassBreakSensor)::getDeviceName);
                case HeatSensor heatSensor -> Bindings.createStringBinding(heatSensor::getDeviceName);
                case MotionSensor motionSensor ->
                        Bindings.createStringBinding(motionSensor::getDeviceName);
                case SmokeSensor smokeSensor -> Bindings.createStringBinding(smokeSensor::getDeviceName);
                default -> throw new IllegalStateException("Unexpected value: " + object);
            };
        });
        deviceTypeTableColumn.setCellValueFactory(cellData -> {
            Object object = cellData.getValue().object();
            return switch (object) {
                case Camera camera -> Bindings.createStringBinding(camera::getDeviceType);
                case CO2Sensor co2Sensor -> Bindings.createStringBinding(co2Sensor::getDeviceType);
                case GlassBreakSensor glassBreakSensor ->
                        Bindings.createStringBinding(glassBreakSensor::getDeviceType);
                case HeatSensor heatSensor -> Bindings.createStringBinding(heatSensor::getDeviceType);
                case MotionSensor motionSensor ->
                        Bindings.createStringBinding(motionSensor::getDeviceType);
                case SmokeSensor smokeSensor -> Bindings.createStringBinding(smokeSensor::getDeviceType);
                default -> throw new IllegalStateException("Unexpected value: " + object);
            };
        });
        deviceResultTableColumn.setCellValueFactory(cellData -> {
            Object object = cellData.getValue().object();
            switch (object) {
                case Camera camera -> {
                    if (camera.getDeviceStatus()) {
                        return Bindings.createStringBinding(() -> camera.getHumanIdentified().toString());
                    } else {
                        return Bindings.createStringBinding(() -> "This device is currently disabled");
                    }
                }
                case CO2Sensor co2Sensor -> {
                    if (co2Sensor.getDeviceStatus()) {
                        return Bindings.createStringBinding(() -> co2Sensor.getCurrentCO2().toString());
                    } else {
                        return Bindings.createStringBinding(() -> "This device is currently disabled");
                    }
                }
                case GlassBreakSensor glassBreakSensor -> {
                    if (glassBreakSensor.getDeviceStatus()) {
                        return Bindings.createStringBinding(() -> glassBreakSensor.getGlassBreak().toString());
                    } else {
                        return Bindings.createStringBinding(() -> "This device is currently disabled");
                    }
                }
                case HeatSensor heatSensor -> {
                    if (heatSensor.getDeviceStatus()) {
                        return Bindings.createStringBinding(() -> heatSensor.getTemperatureInC().toString() + "C");
                    } else {
                        return Bindings.createStringBinding(() -> "This device is currently disabled");
                    }
                }
                case MotionSensor motionSensor -> {
                    if (motionSensor.getDeviceStatus()) {
                        return Bindings.createStringBinding(() -> motionSensor.isAnomalyDetected().toString());
                    } else {
                        return Bindings.createStringBinding(() -> "This device is currently disabled");
                    }
                }
                case SmokeSensor smokeSensor -> {
                    if ((smokeSensor.getDeviceStatus())) {
                        return Bindings.createStringBinding(() -> smokeSensor.getCurrentObscuration().toString());
                    } else {
                        return Bindings.createStringBinding(() -> "This device is currently disabled");
                    }
                }
                default -> throw new IllegalStateException("Unexpected value: " + object);
            }
        });

        deviceTableView.setItems(FXCollections.observableList(allDevices));

        startMeasuring();

    }

    private static List<MultiObjectWrapper> getAllDevices() {
        List<Camera> allCameras=DatabaseUtil.getAllCameras();
        List<CO2Sensor> allCO2Sensors=DatabaseUtil.getAllCO2Sensors();
        List<GlassBreakSensor> allGlassBreakSensors=DatabaseUtil.getAllGlassBreakSensors();
        List<HeatSensor>allHeatSensors= DatabaseUtil.getAllHeatSensors();
        List<MotionSensor> allMotionSensors=DatabaseUtil.getAllMotionSensors();
        List<SmokeSensor> allSmokeSensors=DatabaseUtil.getAllSmokeSensors();
        List<MultiObjectWrapper> allDevices=new ArrayList<>();

        for(Camera camera:allCameras){
            MultiObjectWrapper wrapper=new MultiObjectWrapper(camera);
            allDevices.add(wrapper);
        }
        for(CO2Sensor co2Sensor:allCO2Sensors){
            MultiObjectWrapper wrapper=new MultiObjectWrapper(co2Sensor);
            allDevices.add(wrapper);
        }
        for(GlassBreakSensor glassBreakSensor:allGlassBreakSensors){
            MultiObjectWrapper wrapper=new MultiObjectWrapper(glassBreakSensor);
            allDevices.add(wrapper);
        }
        for(HeatSensor heatSensor:allHeatSensors){
            MultiObjectWrapper wrapper=new MultiObjectWrapper(heatSensor);
            allDevices.add(wrapper);
        }
        for(MotionSensor motionSensor:allMotionSensors){
            MultiObjectWrapper wrapper=new MultiObjectWrapper(motionSensor);
            allDevices.add(wrapper);
        }
        for(SmokeSensor smokeSensor:allSmokeSensors){
            MultiObjectWrapper wrapper=new MultiObjectWrapper(smokeSensor);
            allDevices.add(wrapper);
        }
        return allDevices;
    }

    private void startMeasuring() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {

            List<MultiObjectWrapper> allDevices=getAllDevices();

            for(MultiObjectWrapper object:allDevices){
                switch (object.object()) {
                    case Camera camera -> {
                        if (camera.getDeviceStatus()) {
                            ((Camera) object.object()).measure();
                        }
                    }
                    case CO2Sensor co2Sensor -> {
                        if (co2Sensor.getDeviceStatus()) {
                            ((CO2Sensor) object.object()).measure();
                        }
                    }
                    case GlassBreakSensor glassBreakSensor -> {
                        if (glassBreakSensor.getDeviceStatus()) {
                            ((GlassBreakSensor) object.object()).measure();
                        }
                    }
                    case HeatSensor heatSensor -> {
                        if (heatSensor.getDeviceStatus()) {
                            ((HeatSensor) object.object()).measure();
                        }
                    }
                    case MotionSensor motionSensor -> {
                        if (motionSensor.getDeviceStatus()) {
                            ((MotionSensor) object.object()).measure();
                        }
                    }
                    case null, default -> {
                        if (((SmokeSensor) object.object()).getDeviceStatus()) {
                            ((SmokeSensor) object.object()).measure();
                        }
                    }
                }
            }
            deviceTableView.setItems(FXCollections.observableList(allDevices));

        }));

        timeline.setCycleCount(Timeline.INDEFINITE);

        Platform.runLater(timeline::play);
    }

    public void applyFilter(){
        String deviceType= deviceTypeComboBox.getValue();

        switch(deviceType){
            case("Camera"):{
                List<Camera> allCameras= DatabaseUtil.getAllCameras();
                List<Camera> filteredCameras=allCameras.parallelStream()
                        .filter(c-> c.getDeviceType().equals("Camera"))
                        .toList();

                List<MultiObjectWrapper> filteredCamerasWrapper=new ArrayList<>();

                for(Camera camera:filteredCameras){
                    MultiObjectWrapper filteredCamera=new MultiObjectWrapper(camera);
                    filteredCamerasWrapper.add(filteredCamera);
                }
                ObservableList<MultiObjectWrapper> observableCameraList=FXCollections.observableArrayList(filteredCamerasWrapper);

                deviceTableView.setItems(observableCameraList);
                break;
            }
            case("CO2 Sensor"):{
                List<CO2Sensor> allSensors= DatabaseUtil.getAllCO2Sensors();
                List<CO2Sensor> filteredSensors=allSensors.parallelStream()
                        .filter(c-> c.getDeviceType().equals("CO2 Sensor"))
                        .toList();

                List<MultiObjectWrapper> filteredSensorsWrapper=new ArrayList<>();

                for(CO2Sensor camera:filteredSensors){
                    MultiObjectWrapper filteredCamera=new MultiObjectWrapper(camera);
                    filteredSensorsWrapper.add(filteredCamera);
                }
                ObservableList<MultiObjectWrapper> observableCameraList=FXCollections.observableArrayList(filteredSensorsWrapper);

                deviceTableView.setItems(observableCameraList);
                break;
            }
            case("Glass Break Sensor"):{
                List<GlassBreakSensor> allSensors= DatabaseUtil.getAllGlassBreakSensors();
                List<GlassBreakSensor> filteredSensors=allSensors.parallelStream()
                        .filter(c-> c.getDeviceType().equals("Glass Break Sensor"))
                        .toList();

                List<MultiObjectWrapper> filteredSensorsWrapper=new ArrayList<>();

                for(GlassBreakSensor sensor:filteredSensors){
                    MultiObjectWrapper filteredCamera=new MultiObjectWrapper(sensor);
                    filteredSensorsWrapper.add(filteredCamera);
                }
                ObservableList<MultiObjectWrapper> observableCameraList=FXCollections.observableArrayList(filteredSensorsWrapper);

                deviceTableView.setItems(observableCameraList);
                break;
            }
            case("Heat Sensor"):{
                List<HeatSensor> allSensors= DatabaseUtil.getAllHeatSensors();
                List<HeatSensor> filteredSensors=allSensors.parallelStream()
                        .filter(c-> c.getDeviceType().equals("Heat Sensor"))
                        .toList();

                List<MultiObjectWrapper> filteredSensorsWrapper=new ArrayList<>();

                for(HeatSensor sensor:filteredSensors){
                    MultiObjectWrapper filteredCamera=new MultiObjectWrapper(sensor);
                    filteredSensorsWrapper.add(filteredCamera);
                }
                ObservableList<MultiObjectWrapper> observableCameraList=FXCollections.observableArrayList(filteredSensorsWrapper);

                deviceTableView.setItems(observableCameraList);
                break;
            }
            case("Motion Sensor"):{
                List<MotionSensor> allSensors= DatabaseUtil.getAllMotionSensors();
                List<MotionSensor> filteredSensors=allSensors.parallelStream()
                        .filter(c-> c.getDeviceType().equals("Motion Sensor"))
                        .toList();

                List<MultiObjectWrapper> filteredSensorsWrapper=new ArrayList<>();

                for(MotionSensor sensor:filteredSensors){
                    MultiObjectWrapper filteredCamera=new MultiObjectWrapper(sensor);
                    filteredSensorsWrapper.add(filteredCamera);
                }
                ObservableList<MultiObjectWrapper> observableCameraList=FXCollections.observableArrayList(filteredSensorsWrapper);

                deviceTableView.setItems(observableCameraList);
                break;
            }
            case("Smoke Sensor"):{
                List<SmokeSensor> allSensors= DatabaseUtil.getAllSmokeSensors();
                List<SmokeSensor> filteredSensors=allSensors.parallelStream()
                        .filter(c-> c.getDeviceType().equals("Smoke Sensor"))
                        .toList();

                List<MultiObjectWrapper> filteredSensorsWrapper=new ArrayList<>();

                for(SmokeSensor sensor:filteredSensors){
                    MultiObjectWrapper filteredCamera=new MultiObjectWrapper(sensor);
                    filteredSensorsWrapper.add(filteredCamera);
                }
                ObservableList<MultiObjectWrapper> observableCameraList=FXCollections.observableArrayList(filteredSensorsWrapper);

                deviceTableView.setItems(observableCameraList);
                break;
            }
        }
    }


}
