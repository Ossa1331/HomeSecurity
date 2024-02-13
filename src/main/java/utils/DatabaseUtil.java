package utils;

import classes.*;
import enums.Locations;
import javafx.scene.control.Alert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DatabaseUtil {


    private static final Logger logger= LoggerFactory.getLogger(DatabaseUtil.class);
    private static final String DATABASE_FILE="conf/database.properties";
    private static Connection connectToDatabase() throws SQLException, IOException {
        Properties properties =new Properties();
        properties.load(new FileReader(DATABASE_FILE));
        String urlDatabase= properties.getProperty("databaseUrl");
        String username=properties.getProperty("username");
        String password=properties.getProperty("password");

        return DriverManager.getConnection(urlDatabase,username,password);
    }

    public static List<Camera> getAllCameras(){
        List<Camera> cameras=new ArrayList<>();

        try(Connection connection=connectToDatabase()){
            String sqlQuery="SELECT * FROM CAMERA";
            Statement stmt=connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs=stmt.getResultSet();

            mapResultSetToCameraList(rs, cameras);
        }
        catch(SQLException |IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            logger.error("Error retrieving Cameras from the database.",ex);
            alert.setHeaderText("Error retrieving Cameras from the database.");
            alert.setContentText("Please check your internet connection.");
            alert.showAndWait();
            cameras=FileUtil.deserializeCamerasFromFile();
            return cameras;
        }
        return cameras;
    }
    public static void mapResultSetToCameraList(ResultSet rs, List<Camera> cameras) throws SQLException {
        while(rs.next()){
            DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd.MM.yyyy");
            String name=rs.getString("NAME");
            Boolean status=rs.getBoolean("STATUS");
            String model=rs.getString("MODEL");
            String serialNumber=rs.getString("SERIAL_NUMBER");
            String manufacturer=rs.getString("MANUFACTURER");
            LocalDate manufacturingDate=rs.getDate("MANUFACTURING_DATE").toLocalDate();
            LocalDateTime dateAdded=rs.getTimestamp("DATE_ADDED").toLocalDateTime();
            manufacturingDate.format(formatter);
            String location=rs.getString("LOCATION");
            Boolean humanIdentified=rs.getBoolean("HUMAN_IDENTIFIED");

            Locations deviceLocation=Locations.BACKYARD;

            switch(location){
                case("Bathroom"):{
                    deviceLocation= Locations.BATHROOM;
                    break;
                }
                case("Bedroom"):{
                    deviceLocation= Locations.BEDROOM;
                    break;
                }
                case("Dining room"):{
                    deviceLocation= Locations.DINING_ROOM;
                    break;
                }
                case("Courtyard"):{
                    deviceLocation= Locations.COURTYARD;
                    break;
                }
                case("Terrace"):{
                    deviceLocation= Locations.TERRACE;
                    break;
                }
                case("Hallway"):{
                    deviceLocation= Locations.HALLWAY;
                    break;
                }
                case("Kitchen"):{
                    deviceLocation= Locations.KITCHEN;
                    break;
                }
                case("Basement"):{
                    deviceLocation= Locations.BASEMENT;
                    break;
                }
                case("Stairway"):{
                    deviceLocation= Locations.STAIRWAY;
                    break;
                }
                case("Utility room"):{
                    deviceLocation= Locations.UTILITY_ROOM;
                    break;
                }

            }
            Camera camera= (Camera) Camera.builder()
                    .deviceManufacturer(manufacturer)
                    .deviceType("Camera")
                    .deviceModel(model)
                    .deviceStatus(status)
                    .deviceSerialNumber(serialNumber)
                    .deviceDateAdded(dateAdded)
                    .location(deviceLocation)
                    .deviceManufacturingDate(manufacturingDate)
                    .deviceId(0)
                    .deviceName(name)
                    .build();
            camera.setHumanIdentified(humanIdentified);
            cameras.add(camera);
        }
    }
    public static List<CO2Sensor> getAllCO2Sensors(){
        List<CO2Sensor> CO2Sensors=new ArrayList<>();

        try(Connection connection=connectToDatabase()){
            String sqlQuery="SELECT * FROM CO2SENSOR";
            Statement stmt=connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs=stmt.getResultSet();

            mapResultSetToCO2SensorList(rs, CO2Sensors);
        }
        catch(SQLException |IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            logger.error("Error retrieving CO2 Sensors from the database.",ex);
            alert.setHeaderText("Error retrieving CO2 Sensors from the database.");
            alert.setContentText("Please check your internet connection.");
            alert.showAndWait();
            CO2Sensors=FileUtil.deserializeC02SensorsFromFile();
            return CO2Sensors;
        }
        return CO2Sensors;
    }
    public static void mapResultSetToCO2SensorList(ResultSet rs, List<CO2Sensor> sensors) throws SQLException {
        while(rs.next()){
            String name=rs.getString("NAME");
            Boolean status=rs.getBoolean("STATUS");
            String model=rs.getString("MODEL");
            String serialNumber=rs.getString("SERIAL_NUMBER");
            String manufacturer=rs.getString("MANUFACTURER");
            LocalDate manufacturingDate=rs.getDate("MANUFACTURING_DATE").toLocalDate();
            LocalDateTime dateAdded=rs.getTimestamp("DATE_ADDEd").toLocalDateTime();
            String location=rs.getString("LOCATION");
            Float currentC02=rs.getFloat("CURRENTC02");

            Locations deviceLocation=Locations.BACKYARD;

            switch(location){
                case("Bathroom"):{
                    deviceLocation= Locations.BATHROOM;
                    break;
                }
                case("Bedroom"):{
                    deviceLocation= Locations.BEDROOM;
                    break;
                }
                case("Dining room"):{
                    deviceLocation= Locations.DINING_ROOM;
                    break;
                }
                case("Courtyard"):{
                    deviceLocation= Locations.COURTYARD;
                    break;
                }
                case("Terrace"):{
                    deviceLocation= Locations.TERRACE;
                    break;
                }
                case("Hallway"):{
                    deviceLocation= Locations.HALLWAY;
                    break;
                }
                case("Kitchen"):{
                    deviceLocation= Locations.KITCHEN;
                    break;
                }
                case("Basement"):{
                    deviceLocation= Locations.BASEMENT;
                    break;
                }
                case("Stairway"):{
                    deviceLocation= Locations.STAIRWAY;
                    break;
                }
                case("Utility room"):{
                    deviceLocation= Locations.UTILITY_ROOM;
                    break;
                }

            }
            CO2Sensor sensor= (CO2Sensor) CO2Sensor.builder()
                    .currentCO2(currentC02)
                    .deviceType("CO2 Sensor")
                    .deviceManufacturer(manufacturer)
                    .deviceModel(model)
                    .deviceStatus(status)
                    .deviceSerialNumber(serialNumber)
                    .location(deviceLocation)
                    .deviceManufacturingDate(manufacturingDate)
                    .deviceDateAdded(dateAdded)
                    .deviceName(name)
                    .build();

            sensors.add(sensor);
        }
    }
    public static List<GlassBreakSensor> getAllGlassBreakSensors(){
        List<GlassBreakSensor> glassBreakSensors=new ArrayList<>();

        try(Connection connection=connectToDatabase()){
            String sqlQuery="SELECT * FROM GLASS_BREAK_SENSOR";
            Statement stmt=connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs=stmt.getResultSet();

            mapResultSetToGlassBreakSensorList(rs, glassBreakSensors);
        }
        catch(SQLException |IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            logger.error("Error retrieving Glass Break Sensors from the database.",ex);
            alert.setHeaderText("Error retrieving Glass Break Sensors from the database.");
            alert.setContentText("Please check your internet connection.");
            alert.showAndWait();
            glassBreakSensors=FileUtil.deserializeGlassBreakSensorsFromFile();
            return glassBreakSensors;
        }
        return glassBreakSensors;
    }
    public static void mapResultSetToGlassBreakSensorList(ResultSet rs, List<GlassBreakSensor> sensors) throws SQLException{
        while(rs.next()){
            String name=rs.getString("NAME");
            Boolean status=rs.getBoolean("STATUS");
            String model=rs.getString("MODEL");
            String serialNumber=rs.getString("SERIAL_NUMBER");
            String manufacturer=rs.getString("MANUFACTURER");
            LocalDate manufacturingDate=rs.getDate("MANUFACTURING_DATE").toLocalDate();
            LocalDateTime dateAdded=rs.getTimestamp("DATE_ADDED").toLocalDateTime();
            String location=rs.getString("LOCATION");
            Boolean glassBroken=rs.getBoolean("GLASS_BROKEN");

            Locations deviceLocation=Locations.BACKYARD;

            switch(location){
                case("Bathroom"):{
                    deviceLocation= Locations.BATHROOM;
                    break;
                }
                case("Bedroom"):{
                    deviceLocation= Locations.BEDROOM;
                    break;
                }
                case("Dining room"):{
                    deviceLocation= Locations.DINING_ROOM;
                    break;
                }
                case("Courtyard"):{
                    deviceLocation= Locations.COURTYARD;
                    break;
                }
                case("Terrace"):{
                    deviceLocation= Locations.TERRACE;
                    break;
                }
                case("Hallway"):{
                    deviceLocation= Locations.HALLWAY;
                    break;
                }
                case("Kitchen"):{
                    deviceLocation= Locations.KITCHEN;
                    break;
                }
                case("Basement"):{
                    deviceLocation= Locations.BASEMENT;
                    break;
                }
                case("Stairway"):{
                    deviceLocation= Locations.STAIRWAY;
                    break;
                }
                case("Utility room"):{
                    deviceLocation= Locations.UTILITY_ROOM;
                    break;
                }

            }
            GlassBreakSensor sensor= (GlassBreakSensor) GlassBreakSensor.builder()
                    .deviceManufacturer(manufacturer)
                    .deviceType("Glass Break Sensor")
                    .deviceModel(model)
                    .deviceStatus(status)
                    .deviceSerialNumber(serialNumber)
                    .location(deviceLocation)
                    .deviceManufacturingDate(manufacturingDate)
                    .deviceDateAdded(dateAdded)
                    .deviceName(name)
                    .build();
            sensor.setGlassBreak(glassBroken);
            sensors.add(sensor);
        }
    }
    public static List<HeatSensor> getAllHeatSensors(){
        List<HeatSensor> heatSensors=new ArrayList<>();

        try(Connection connection=connectToDatabase()){
            String sqlQuery="SELECT * FROM HEAT_SENSOR";
            Statement stmt=connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs=stmt.getResultSet();

            mapResultSetToHeatSensorList(rs, heatSensors);
        }
        catch(SQLException |IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            logger.error("Error retrieving Heat Sensors from the database.",ex);
            alert.setHeaderText("Error retrieving Heat Sensors from the database.");
            alert.setContentText("Please check your internet connection.");
            alert.showAndWait();
            heatSensors=FileUtil.deserializeHeatSensorsFromFile();
            return heatSensors;
        }
        return heatSensors;
    }
    public static void mapResultSetToHeatSensorList(ResultSet rs, List<HeatSensor> sensors) throws SQLException {
        while(rs.next()){
            String name=rs.getString("NAME");
            Boolean status=rs.getBoolean("STATUS");
            String model=rs.getString("MODEL");
            String serialNumber=rs.getString("SERIAL_NUMBER");
            String manufacturer=rs.getString("MANUFACTURER");
            LocalDate manufacturingDate=rs.getDate("MANUFACTURING_DATE").toLocalDate();
            LocalDateTime dateAdded=rs.getTimestamp("DATE_ADDED").toLocalDateTime();
            String location=rs.getString("LOCATION");
            Float temperatureUpperLimit=rs.getFloat("UPPER_LIMIT");
            Float temperatureLowerLimit=rs.getFloat("LOWER_LIMIT");
            Float temperatureInC=rs.getFloat("TEMPERATURE_IN_C");

            Locations deviceLocation=Locations.BACKYARD;

            switch(location){
                case("Bathroom"):{
                    deviceLocation= Locations.BATHROOM;
                    break;
                }
                case("Bedroom"):{
                    deviceLocation= Locations.BEDROOM;
                    break;
                }
                case("Dining room"):{
                    deviceLocation= Locations.DINING_ROOM;
                    break;
                }
                case("Courtyard"):{
                    deviceLocation= Locations.COURTYARD;
                    break;
                }
                case("Terrace"):{
                    deviceLocation= Locations.TERRACE;
                    break;
                }
                case("Hallway"):{
                    deviceLocation= Locations.HALLWAY;
                    break;
                }
                case("Kitchen"):{
                    deviceLocation= Locations.KITCHEN;
                    break;
                }
                case("Basement"):{
                    deviceLocation= Locations.BASEMENT;
                    break;
                }
                case("Stairway"):{
                    deviceLocation= Locations.STAIRWAY;
                    break;
                }
                case("Utility room"):{
                    deviceLocation= Locations.UTILITY_ROOM;
                    break;
                }

            }
            HeatSensor sensor= (HeatSensor) HeatSensor.builder()
                    .temperatureInC(temperatureInC)
                    .sensorUpperLimit(temperatureUpperLimit)
                    .sensorLowerLimit(temperatureLowerLimit)
                    .deviceType("Heat Sensor")
                    .deviceManufacturer(manufacturer)
                    .deviceModel(model)
                    .deviceStatus(status)
                    .deviceSerialNumber(serialNumber)
                    .location(deviceLocation)
                    .deviceManufacturingDate(manufacturingDate)
                    .deviceDateAdded(dateAdded)
                    .deviceName(name)
                    .build();
            sensors.add(sensor);
        }
    }
    public static List<MotionSensor> getAllMotionSensors(){
        List<MotionSensor> motionSensors=new ArrayList<>();

        try(Connection connection=connectToDatabase()){
            String sqlQuery="SELECT * FROM MOTION_SENSOR";
            Statement stmt=connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs=stmt.getResultSet();

            mapResultSetToMotionSensorList(rs, motionSensors);
        }
        catch(SQLException |IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            logger.error("Error retrieving Motion Sensors from the database.",ex);
            alert.setHeaderText("Error retrieving Motion Sensors from the database.");
            alert.setContentText("Please check your internet connection.");
            alert.showAndWait();
            motionSensors=FileUtil.deserializeMotionSensorsFromFile();
            return motionSensors;
        }
        return motionSensors;
    }
    public static void mapResultSetToMotionSensorList(ResultSet rs, List<MotionSensor> sensors) throws SQLException {
        while(rs.next()){
            String name=rs.getString("NAME");
            Boolean status=rs.getBoolean("STATUS");
            String model=rs.getString("MODEL");
            String serialNumber=rs.getString("SERIAL_NUMBER");
            String manufacturer=rs.getString("MANUFACTURER");
            LocalDate manufacturingDate=rs.getDate("MANUFACTURING_DATE").toLocalDate();
            LocalDateTime dateAdded=rs.getTimestamp("DATE_ADDED").toLocalDateTime();
            String location=rs.getString("LOCATION");
            Boolean anomalyDetected=rs.getBoolean("ANOMALY_DETECTED");

            Locations deviceLocation=Locations.BACKYARD;

            switch(location){
                case("Bathroom"):{
                    deviceLocation= Locations.BATHROOM;
                    break;
                }
                case("Bedroom"):{
                    deviceLocation= Locations.BEDROOM;
                    break;
                }
                case("Dining room"):{
                    deviceLocation= Locations.DINING_ROOM;
                    break;
                }
                case("Courtyard"):{
                    deviceLocation= Locations.COURTYARD;
                    break;
                }
                case("Terrace"):{
                    deviceLocation= Locations.TERRACE;
                    break;
                }
                case("Hallway"):{
                    deviceLocation= Locations.HALLWAY;
                    break;
                }
                case("Kitchen"):{
                    deviceLocation= Locations.KITCHEN;
                    break;
                }
                case("Basement"):{
                    deviceLocation= Locations.BASEMENT;
                    break;
                }
                case("Stairway"):{
                    deviceLocation= Locations.STAIRWAY;
                    break;
                }
                case("Utility room"):{
                    deviceLocation= Locations.UTILITY_ROOM;
                    break;
                }

            }
            MotionSensor sensor= (MotionSensor) MotionSensor.builder()
                    .deviceManufacturer(manufacturer)
                    .deviceType("Motion Sensor")
                    .deviceModel(model)
                    .deviceStatus(status)
                    .deviceSerialNumber(serialNumber)
                    .location(deviceLocation)
                    .deviceManufacturingDate(manufacturingDate)
                    .deviceDateAdded(dateAdded)
                    .deviceId(0)
                    .deviceName(name)
                    .build();

            sensor.setAnomalyDetected(anomalyDetected);
            sensors.add(sensor);
        }
    }
    public static List<SmokeSensor> getAllSmokeSensors(){
        List<SmokeSensor> smokeSensors=new ArrayList<>();

        try(Connection connection=connectToDatabase()){
            String sqlQuery="SELECT * FROM SMOKE_SENSOR";
            Statement stmt=connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs=stmt.getResultSet();

            mapResultSetToSmokeSensorList(rs, smokeSensors);
        }
        catch(SQLException |IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            logger.error("Error retrieving Smoke Sensors from the database.",ex);
            alert.setHeaderText("Error retrieving Smoke Sensors from the database.");
            alert.setContentText("Please check your internet connection.");
            alert.showAndWait();
            smokeSensors=FileUtil.deserializeSmokeSensorsFromFile();
            return smokeSensors;
        }
        return smokeSensors;
    }

    public static void mapResultSetToSmokeSensorList(ResultSet rs, List<SmokeSensor> sensors) throws SQLException {
        while(rs.next()){
            String name=rs.getString("NAME");
            Boolean status=rs.getBoolean("STATUS");
            String model=rs.getString("MODEL");
            String serialNumber=rs.getString("SERIAL_NUMBER");
            String manufacturer=rs.getString("MANUFACTURER");
            LocalDate manufacturingDate=rs.getDate("MANUFACTURING_DATE").toLocalDate();
            LocalDateTime dateAdded=rs.getTimestamp("DATE_ADDED").toLocalDateTime();
            String location=rs.getString("LOCATION");
            Float currentObscuration=rs.getFloat("CURRENT_OBSCURATION");

            Locations deviceLocation=Locations.BACKYARD;

            switch(location){
                case("Bathroom"):{
                    deviceLocation= Locations.BATHROOM;
                    break;
                }
                case("Bedroom"):{
                    deviceLocation= Locations.BEDROOM;
                    break;
                }
                case("Dining room"):{
                    deviceLocation= Locations.DINING_ROOM;
                    break;
                }
                case("Courtyard"):{
                    deviceLocation= Locations.COURTYARD;
                    break;
                }
                case("Terrace"):{
                    deviceLocation= Locations.TERRACE;
                    break;
                }
                case("Hallway"):{
                    deviceLocation= Locations.HALLWAY;
                    break;
                }
                case("Kitchen"):{
                    deviceLocation= Locations.KITCHEN;
                    break;
                }
                case("Basement"):{
                    deviceLocation= Locations.BASEMENT;
                    break;
                }
                case("Stairway"):{
                    deviceLocation= Locations.STAIRWAY;
                    break;
                }
                case("Utility room"):{
                    deviceLocation= Locations.UTILITY_ROOM;
                    break;
                }

            }
            SmokeSensor sensor= (SmokeSensor) SmokeSensor.builder()
                    .currentObscuration(currentObscuration)
                    .deviceType("Smoke Sensor")
                    .deviceManufacturer(manufacturer)
                    .deviceModel(model)
                    .deviceStatus(status)
                    .deviceSerialNumber(serialNumber)
                    .location(deviceLocation)
                    .deviceManufacturingDate(manufacturingDate)
                    .deviceDateAdded(dateAdded)
                    .deviceId(0)
                    .deviceName(name)
                    .build();

            sensors.add(sensor);
        }
    }

    public static List<Device> getAllDevices(){
        List<Device> devices=new ArrayList<>();

        List<Camera> cameras= getAllCameras();
        List<CO2Sensor> co2Sensors=getAllCO2Sensors();
        List<GlassBreakSensor>glassBreakSensors=getAllGlassBreakSensors();
        List<HeatSensor> heatSensors=getAllHeatSensors();
        List<MotionSensor> motionSensors=getAllMotionSensors();
        List<SmokeSensor> smokeSensors=getAllSmokeSensors();

        devices.addAll(cameras);
        devices.addAll(co2Sensors);
        devices.addAll(glassBreakSensors);
        devices.addAll(heatSensors);
        devices.addAll(motionSensors);
        devices.addAll(smokeSensors);

        return devices;
    }

    public static void saveCamera(Camera camera){
        try(Connection connection=connectToDatabase()){

            String insertCameraSql= "INSERT INTO CAMERA(NAME, STATUS, MODEL, MANUFACTURER, MANUFACTURING_DATE, LOCATION, CURRENT_CAMERA_TIME, HUMAN_IDENTIFIED, SERIAL_NUMBER, DATE_ADDED) VALUES(" +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt= connection.prepareStatement(insertCameraSql);
            pstmt.setString(1, camera.getDeviceName());
            pstmt.setBoolean(2, camera.getDeviceStatus());
            pstmt.setString(3, camera.getDeviceModel());
            pstmt.setString(4, camera.getDeviceManufacturer());
            pstmt.setDate(5, new Date(camera.getDeviceManufacturingDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pstmt.setString(6, camera.getLocation().getLocation());
            pstmt.setTimestamp(7,Timestamp.valueOf(camera.getCurrentCameraTime()));
            pstmt.setBoolean(8, camera.getHumanIdentified());
            pstmt.setString(9, camera.getDeviceSerialNumber());
            pstmt.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.execute();

        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving Camera to database.");
            alert.setContentText("Please try again.");
            logger.error("Error saving Camera to database",ex);
            alert.showAndWait();
        }
    }
    public static void saveCO2Sensor(CO2Sensor sensor){
        try(Connection connection=connectToDatabase()){

            String insertCameraSql= "INSERT INTO CO2SENSOR(NAME, STATUS, MODEL, MANUFACTURER, MANUFACTURING_DATE, LOCATION, CURRENTC02, SERIAL_NUMBER, DATE_ADDED) VALUES(" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt= connection.prepareStatement(insertCameraSql);
            pstmt.setString(1, sensor.getDeviceName());
            pstmt.setBoolean(2,sensor.getDeviceStatus());
            pstmt.setString(3, sensor.getDeviceModel());
            pstmt.setString(4, sensor.getDeviceManufacturer());
            pstmt.setDate(5, new Date(sensor.getDeviceManufacturingDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pstmt.setString(6, sensor.getLocation().getLocation());
            pstmt.setFloat(7, sensor.measure());
            pstmt.setString(8, sensor.getDeviceSerialNumber());
            pstmt.setTimestamp(9,Timestamp.valueOf(LocalDateTime.now()));
            pstmt.execute();

        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error saving Camera to database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving Camera to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();

        }
    }
    public static void saveGlassBreakSensor(GlassBreakSensor sensor){
        try(Connection connection=connectToDatabase()){

            String insertCameraSql= "INSERT INTO GLASS_BREAK_SENSOR(NAME, STATUS, MODEL, MANUFACTURER, MANUFACTURING_DATE, LOCATION, GLASS_BROKEN, SERIAL_NUMBER, DATE_ADDED) VALUES(" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt= connection.prepareStatement(insertCameraSql);
            pstmt.setString(1, sensor.getDeviceName());
            pstmt.setBoolean(2,sensor.getDeviceStatus());
            pstmt.setString(3, sensor.getDeviceModel());
            pstmt.setString(4, sensor.getDeviceManufacturer());
            pstmt.setDate(5, new Date(sensor.getDeviceManufacturingDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pstmt.setString(6, sensor.getLocation().getLocation());
            pstmt.setBoolean(7, sensor.getGlassBreak());
            pstmt.setString(8, sensor.getDeviceSerialNumber());
            pstmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.execute();

        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving Glass Break Sensor to database.");
            alert.setContentText("Please try again.");
            logger.error("Error saving object to Glass Break Sensor to database",ex);
            alert.showAndWait();

        }
    }
    public static void saveHeatSensor(HeatSensor sensor){
        try(Connection connection=connectToDatabase()){

            String insertCameraSql= "INSERT INTO HEAT_SENSOR(NAME, STATUS, MODEL, MANUFACTURER, MANUFACTURING_DATE, LOCATION, UPPER_LIMIT, LOWER_LIMIT, TEMPERATURE_IN_C, TEMPERATURE_IN_F, SERIAL_NUMBER, DATE_ADDED) VALUES(" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt= connection.prepareStatement(insertCameraSql);
            pstmt.setString(1, sensor.getDeviceName());
            pstmt.setBoolean(2,sensor.getDeviceStatus());
            pstmt.setString(3, sensor.getDeviceModel());
            pstmt.setString(4, sensor.getDeviceManufacturer());
            pstmt.setDate(5, new Date(sensor.getDeviceManufacturingDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pstmt.setString(6, sensor.getLocation().getLocation());
            pstmt.setFloat(7, sensor.getSensorUpperLimit());
            pstmt.setFloat(8, sensor.getSensorLowerLimit());

            sensor.setTemperatureInC(sensor.measure());
            sensor.setTemperatureInF(sensor.getTemperatureInC());

            pstmt.setFloat(9, sensor.getTemperatureInC());
            pstmt.setFloat(10, sensor.getTemperatureInF());
            pstmt.setString(11, sensor.getDeviceSerialNumber());
            pstmt.setTimestamp(12, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.execute();

        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            String message="Error saving Heat Sensor to database";
            alert.setTitle("Error");
            alert.setHeaderText("Error saving Heat Sensor to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
            logger.error(message,ex);
            System.out.println(message);
        }
    }
    public static void saveMotionSensor(MotionSensor sensor){
        try(Connection connection=connectToDatabase()){

            String insertCameraSql= "INSERT INTO MOTION_SENSOR(NAME, STATUS, MODEL, MANUFACTURER, MANUFACTURING_DATE, LOCATION, ANOMALY_DETECTED, SERIAL_NUMBER, DATE_ADDED) VALUES(" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt= connection.prepareStatement(insertCameraSql);
            pstmt.setString(1, sensor.getDeviceName());
            pstmt.setBoolean(2,sensor.getDeviceStatus());
            pstmt.setString(3, sensor.getDeviceModel());
            pstmt.setString(4, sensor.getDeviceManufacturer());
            pstmt.setDate(5, new Date(sensor.getDeviceManufacturingDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pstmt.setString(6, sensor.getLocation().getLocation());
            pstmt.setBoolean(7, sensor.isAnomalyDetected());
            pstmt.setString(8, sensor.getDeviceSerialNumber());
            pstmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));

            pstmt.execute();

        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error saving Motion Sensor to database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving Motion Sensor to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }
    public static void saveSmokeSensor(SmokeSensor sensor){
        try(Connection connection=connectToDatabase()){

            String insertCameraSql= "INSERT INTO SMOKE_SENSOR(NAME, STATUS, MODEL, MANUFACTURER, MANUFACTURING_DATE, LOCATION, CURRENT_OBSCURATION, SERIAL_NUMBER, DATE_ADDED) VALUES(" +
                    "?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement pstmt= connection.prepareStatement(insertCameraSql);
            pstmt.setString(1, sensor.getDeviceName());
            pstmt.setBoolean(2,sensor.getDeviceStatus());
            pstmt.setString(3, sensor.getDeviceModel());
            pstmt.setString(4, sensor.getDeviceManufacturer());
            pstmt.setDate(5, new Date(sensor.getDeviceManufacturingDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()));
            pstmt.setString(6, sensor.getLocation().getLocation());
            pstmt.setFloat(7, sensor.getCurrentObscuration());
            pstmt.setString(8, sensor.getDeviceSerialNumber());
            pstmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.execute();

        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error saving Smoke Sensor to database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving Smoke Sensor to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }
    public static void deleteCamera(Camera camera){
        try(Connection connection=connectToDatabase()){
            String deleteCameraSql="DELETE FROM CAMERA WHERE NAME = ?;";

            PreparedStatement pstmt=connection.prepareStatement(deleteCameraSql);

            pstmt.setString(1, camera.getDeviceName());
            pstmt.execute();

            logger.info("Camera has been deleted successfully");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error deleting Camera from database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting object from database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }
    public static void deleteCO2Sensor(CO2Sensor sensor){
        try(Connection connection=connectToDatabase()){
            String deleteCO2SensorSql="DELETE FROM CO2SENSOR WHERE NAME = ?;";

            PreparedStatement pstmt=connection.prepareStatement(deleteCO2SensorSql);
            pstmt.setString(1, sensor.getDeviceName());
            pstmt.execute();

            logger.info("CO2 Sensor has been deleted successfully");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error deleting CO2 Sensor from database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting CO2 Sensor from database.");
            alert.setContentText("Please check your internet connection.");
            alert.showAndWait();

        }
    }

    public static void deleteGlassBreakSensor(GlassBreakSensor sensor){
        try(Connection connection=connectToDatabase()){
            String deleteCO2SensorSql="DELETE FROM GLASS_BREAK_SENSOR WHERE NAME = ?;";

            PreparedStatement pstmt=connection.prepareStatement(deleteCO2SensorSql);
            pstmt.setString(1, sensor.getDeviceName());
            pstmt.execute();

            logger.info("Glass break sensor has been deleted successfully");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error deleting Glass Break Sensor from database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting Glass Break Sensor from database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }

    public static void deleteHeatSensor(HeatSensor sensor){
        try(Connection connection=connectToDatabase()){
            String deleteCO2SensorSql="DELETE FROM HEAT_SENSOR WHERE NAME = ?;";

            PreparedStatement pstmt=connection.prepareStatement(deleteCO2SensorSql);
            pstmt.setString(1, sensor.getDeviceName());
            pstmt.execute();

            logger.info("Heat sensor has been deleted successfully");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error deleting Heat Sensor from database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting Heat Sensor from database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();

        }
    }
    public static void deleteMotionSensor(MotionSensor sensor){
        try(Connection connection=connectToDatabase()){
            String deleteCO2SensorSql="DELETE FROM MOTION_SENSOR WHERE NAME = ?;";

            PreparedStatement pstmt=connection.prepareStatement(deleteCO2SensorSql);
            pstmt.setString(1, sensor.getDeviceName());
            pstmt.execute();

            logger.info("Motion sensor has been deleted successfully");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error deleting Motion Sensor from database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting Motion Sensor from database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }
    public static void deleteSmokeSensor(SmokeSensor sensor){
        try(Connection connection=connectToDatabase()){
            String deleteCO2SensorSql="DELETE FROM SMOKE_SENSOR WHERE NAME = ?;";

            PreparedStatement pstmt=connection.prepareStatement(deleteCO2SensorSql);
            pstmt.setString(1, sensor.getDeviceName());
            pstmt.execute();

            logger.info("Smoke sensor has been deleted successfully");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error deleting Smoke Sensor from database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting Smoke Sensor from database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }

    public static void updateDevice(Device device){
        try(Connection connection= connectToDatabase()) {
            if (device.getDeviceStatus()) {
                switch (device.getDeviceType()) {
                    case "CO2 Sensor" -> {
                        String sqlQuery = "UPDATE CO2SENSOR SET STATUS=false WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("CO2 Sensor " + device.getDeviceName() + " has successfully been updated");
                    }
                    case "Camera" -> {
                        String sqlQuery = "UPDATE CAMERA SET STATUS=false WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("Camera " + device.getDeviceName() + " has successfully been updated");
                    }
                    case "Glass Break Sensor" -> {
                        String sqlQuery = "UPDATE GLASS_BREAK_SENSOR SET STATUS=false WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("Glass Break Sensor " + device.getDeviceName() + " has successfully been updated");
                    }
                    case "Heat Sensor" -> {
                        String sqlQuery = "UPDATE HEAT_SENSOR SET STATUS=false WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("Heat Sensor " + device.getDeviceName() + " has successfully been updated");
                    }
                    case "Motion Sensor" -> {
                        String sqlQuery = "UPDATE MOTION_SENSOR SET STATUS=false WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("Motion Sensor " + device.getDeviceName() + " has successfully been updated");
                    }
                    case "Smoke Sensor" -> {
                        String sqlQuery = "UPDATE SMOKE_SENSOR SET STATUS=false WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("Smoke Sensor " + device.getDeviceName() + " has successfully been updated");
                    }
                }
            }
            else{
                switch (device.getDeviceType()) {
                    case "CO2 Sensor" -> {
                        String sqlQuery = "UPDATE CO2SENSOR SET STATUS=true WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("CO2 Sensor " + device.getDeviceName() + " has successfully been updated");
                    }
                    case "Camera" -> {
                        String sqlQuery = "UPDATE CAMERA SET STATUS=true WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("Camera " + device.getDeviceName() + " has successfully been updated");
                    }
                    case "Glass Break Sensor" -> {
                        String sqlQuery = "UPDATE GLASS_BREAK_SENSOR SET STATUS=true WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("Glass Break Sensor " + device.getDeviceName() + " has successfully been updated");
                    }
                    case "Heat Sensor" -> {
                        String sqlQuery = "UPDATE HEAT_SENSOR SET STATUS=true WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("Heat Sensor " + device.getDeviceName() + " has successfully been updated");
                    }
                    case "Motion Sensor" -> {
                        String sqlQuery = "UPDATE MOTION_SENSOR SET STATUS=true WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("Motion Sensor " + device.getDeviceName() + " has successfully been updated");
                    }
                    case "Smoke Sensor" -> {
                        String sqlQuery = "UPDATE SMOKE_SENSOR SET STATUS=true WHERE NAME=?";
                        PreparedStatement pstmt = connection.prepareStatement(sqlQuery);
                        pstmt.setString(1, device.getDeviceName());
                        pstmt.execute();

                        logger.info("Smoke Sensor " + device.getDeviceName() + " has successfully been updated");
                    }
                }
            }
        }
        catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error updating sensors to database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating sensors to database");
            alert.setContentText("Please check your internet connection");
            alert.showAndWait();
        }
    }
    public static List<Change> getAllChanges(){

        List<Change> allChanges=new ArrayList<>();

        try(Connection connection=connectToDatabase()){
            String sqlQuery="SELECT * FROM CHANGE_LOG";
            Statement stmt=connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs=stmt.getResultSet();
            while(rs.next()){
                String deviceName= rs.getString("DEVICE_NAME");
                String deviceType=rs.getString("DEVICE_TYPE");
                String message=rs.getString("MESSAGE");
                LocalDateTime time= rs.getTimestamp("TIME_ADDED").toLocalDateTime();
                Change change= new Change(deviceName,deviceType, message, time);

                allChanges.add(change);
            }

        }catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error retrieving change log from database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Database issue");
            alert.setContentText("Please check your connection");
            alert.showAndWait();
        }
        return allChanges;
    }
    public static void saveChange(Change change){
        try(Connection connection=connectToDatabase()){

            String insertChangeSql= "INSERT INTO CHANGE_LOG(DEVICE_NAME,DEVICE_TYPE, MESSAGE, TIME_ADDED) VALUES(" +
                    "?, ?, ?, ?);";
            PreparedStatement pstmt= connection.prepareStatement(insertChangeSql);
            pstmt.setString(1, change.getDeviceName());
            pstmt.setString(2, change.getDeviceType());
            pstmt.setString(3, change.getMessage());
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.execute();

        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving change to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
            logger.error("Error saving object to database",ex);
        }
    }

    public static void dismissChanges(){
        try(Connection connection=connectToDatabase()){
            String dismissSql="DELETE FROM CHANGE_LOG";
            PreparedStatement pstmt= connection.prepareStatement(dismissSql);
            pstmt.execute();
        }
        catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            logger.error("Error dismissing changes from database",ex);
            alert.setHeaderText("Error dismissing changes from the database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
        }
    }
    public static List<Event> getAllEvents(){

        List<Event> allEvents=new ArrayList<>();

        try(Connection connection=connectToDatabase()){
            String sqlQuery="SELECT * FROM EVENT_LOG";
            Statement stmt=connection.createStatement();
            stmt.execute(sqlQuery);
            ResultSet rs=stmt.getResultSet();
            while(rs.next()){
                String deviceName= rs.getString("DEVICE_NAME");
                String deviceType=rs.getString("DEVICE_TYPE");
                String message=rs.getString("MESSAGE");
                LocalDateTime time= rs.getTimestamp("TIME_ADDED").toLocalDateTime();

                Event event= new Event(time, deviceName,deviceType);
                event.setMessage(message);
                allEvents.add(event);
            }

        }catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            logger.error("Error retrieving Events from the database.",ex);
            alert.setHeaderText("Error retrieving Events from the the database.");
            alert.setContentText("Please check your internet connection.");
            alert.showAndWait();
        }
        return allEvents;
    }
    public static void saveEvent(Event event){
        try(Connection connection=connectToDatabase()){
            String insertEventSql="INSERT INTO EVENT_LOG(DEVICE_NAME, DEVICE_TYPE, MESSAGE, TIME_ADDED) VALUES(?, ?, ?, ?);";

            PreparedStatement pstmt=connection.prepareStatement(insertEventSql);
            pstmt.setString(1, event.getDeviceName());
            pstmt.setString(2, event.getDeviceType());
            pstmt.setString(3, event.getMessage());
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.execute();
        }
        catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            logger.error("Error saving Event to database",ex);
            alert.setTitle("Error");
            alert.setHeaderText("Error saving Event to database.");
            alert.setContentText("Please check your internet connection.");
            alert.showAndWait();
        }
    }
    public static void dismissEvents(){
        try(Connection connection=connectToDatabase()){
            String dismissSql="DELETE FROM EVENT_LOG";
            PreparedStatement pstmt= connection.prepareStatement(dismissSql);
            pstmt.execute();
        }
        catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            logger.error("Error dismissing Events from the database",ex);
            alert.setHeaderText("Error dismissing Events from the database.");
            alert.setContentText("Please check your internet connection.");
            alert.showAndWait();
        }
    }
    public static void updateCamera(Camera camera, String newName){
        try(Connection connection=connectToDatabase()){
            String updateCameraSql="UPDATE CAMERA SET NAME=? WHERE NAME=?;";

            PreparedStatement pstmt=connection.prepareStatement(updateCameraSql);

            pstmt.setString(1, newName);
            pstmt.setString(2, camera.getDeviceName());
            pstmt.execute();

            logger.info("Camera's name has been successfully updated to database");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error deleting object from database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
            logger.error("Error updating Camera's name in the database.");
        }
    }
    public static void updateCO2Sensor(CO2Sensor sensor, String newName){
        try(Connection connection=connectToDatabase()){
            String updateCO2SensorSql="UPDATE CO2SENSOR SET NAME=? WHERE NAME=?;";

            PreparedStatement pstmt=connection.prepareStatement(updateCO2SensorSql);
            pstmt.setString(1,newName);
            pstmt.setString(2, sensor.getDeviceName());
            pstmt.execute();

            logger.info("CO2 Sensor name has been changed successfully");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating CO2 Sensor's name in the database.");
            alert.setContentText("Please try again.");
            logger.error("Error updating CO2 Sensor's name in the database.");

            alert.showAndWait();


        }
    }

    public static void updateGlassBreakSensor(GlassBreakSensor sensor, String newName){
        try(Connection connection=connectToDatabase()){
            String deleteCO2SensorSql="UPDATE GLASS_BREAK_SENSOR SET NAME=? WHERE NAME=?;";

            PreparedStatement pstmt=connection.prepareStatement(deleteCO2SensorSql);
            pstmt.setString(1, newName);
            pstmt.setString(2,sensor.getDeviceName());
            pstmt.execute();

            logger.info("Glass Break Sensor's name has been updated successfully");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating Glass Break Sensor's name to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
            logger.error("Error updating Glass Break Sensor's name to database.");
        }
    }

    public static void updateHeatSensor(HeatSensor sensor, String newName){
        try(Connection connection=connectToDatabase()){
            String deleteCO2SensorSql="UPDATE HEAT_SENSOR SET NAME=? WHERE NAME=?;";

            PreparedStatement pstmt=connection.prepareStatement(deleteCO2SensorSql);
            pstmt.setString(1, newName);
            pstmt.setString(2, sensor.getDeviceName());
            pstmt.execute();

            logger.info("Heat sensor has been deleted successfully");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating Heat Sensor's name to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
            logger.error("Error updating Heat Sensor's name to database.");
        }
    }
    public static void updateMotionSensor(MotionSensor sensor, String newName){
        try(Connection connection=connectToDatabase()){
            String deleteCO2SensorSql="UPDATE MOTION_SENSOR SET NAME=? WHERE NAME=?;";

            PreparedStatement pstmt=connection.prepareStatement(deleteCO2SensorSql);
            pstmt.setString(1, newName);
            pstmt.setString(2, sensor.getDeviceName());
            pstmt.execute();

            logger.info("Motion sensor has been deleted successfully");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating Motion Sensor's name from database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();
            logger.error("Error updating Motion Sensor's name to database");
        }
    }
    public static void updateSmokeSensor(SmokeSensor sensor, String newName ){
        try(Connection connection=connectToDatabase()){
            String updateSmokeSensorSql="UPDATE SMOKE_SENSOR SET NAME=? WHERE NAME=?;";

            PreparedStatement pstmt=connection.prepareStatement(updateSmokeSensorSql);
            pstmt.setString(1, newName);
            pstmt.setString(2, sensor.getDeviceName());
            pstmt.execute();

            logger.info("Smoke Sensor's name has been updated successfully");
        }
        catch(SQLException | IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating Smoke Sensor's name to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();

            logger.error("Error updating Smoke Sensor's name to database");
        }
    }

    public static void updateMeasurableCamera(Camera camera){
        try(Connection connection=connectToDatabase()){
            String updateCameraSql="UPDATE CAMERA SET HUMAN_IDENTIFIED =? WHERE NAME=?;";

            PreparedStatement pstmt=connection.prepareStatement(updateCameraSql);
            pstmt.setBoolean(1, camera.getHumanIdentified());
            pstmt.setString(2, camera.getDeviceName());
            pstmt.execute();

            logger.info("Camera measurement updated!");

        }catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating Smoke Sensor's name to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();

            logger.error("Error updating Camera's measures to database");
        }
    }
    public static void updateMeasurableCO2Sensor(CO2Sensor sensor){
        try(Connection connection=connectToDatabase()){
            String updateSmokeSensorSql="UPDATE CO2SENSOR SET CURRENTC02 =? WHERE NAME=?;";

            PreparedStatement pstmt=connection.prepareStatement(updateSmokeSensorSql);
            pstmt.setFloat(1, sensor.getCurrentCO2());
            pstmt.setString(2, sensor.getDeviceName());
            pstmt.execute();

            logger.info("CO2 Sensor measurement updated!");

        }catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating Smoke Sensor's name to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();

            logger.error("Error updating CO2 Sensor's measures to database");
        }
    }
    public static void updateMeasurableGlassBreakSensor(GlassBreakSensor sensor){
        try(Connection connection=connectToDatabase()){
            String updateGlassBreakSensorSql="UPDATE GLASS_BREAK_SENSOR SET GLASS_BROKEN =? WHERE NAME=?;";

            PreparedStatement pstmt=connection.prepareStatement(updateGlassBreakSensorSql);
            pstmt.setBoolean(1, sensor.getGlassBreak());
            pstmt.setString(2, sensor.getDeviceName());
            pstmt.execute();

            logger.info("Glass Break Sensor measurement updated!");

        }catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating Smoke Sensor's name to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();

            logger.error("Error updating Glass Break Sensor's measures to database");
        }
    }
    public static void updateMeasurableHeatSensor(HeatSensor sensor){
        try(Connection connection=connectToDatabase()){
            String updateHeatSensorSql="UPDATE HEAT_SENSOR SET TEMPERATURE_IN_C =? WHERE NAME=?;";
            String updateHeatSensorSql2="UPDATE HEAT_SENSOR SET TEMPERATURE_IN_F= ? WHERE NAME=?";

            PreparedStatement pstmt=connection.prepareStatement(updateHeatSensorSql);
            pstmt.setFloat(1, sensor.getTemperatureInC());
            pstmt.setString(2, sensor.getDeviceName());
            pstmt.execute();
            PreparedStatement pstmt2=connection.prepareStatement(updateHeatSensorSql2);
            pstmt2.setFloat(1, sensor.getTemperatureInF());
            pstmt2.setString(2, sensor.getDeviceName());
            pstmt2.execute();

            logger.info("Heat Sensor measurement updated!");


        }catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating Smoke Sensor's name to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();

            logger.error("Error updating Heat sensor's measures to database");
        }
    }
    public static void updateMeasurableMotionSensor(MotionSensor sensor){
        try(Connection connection=connectToDatabase()){
            String updateHeatSensorSql="UPDATE MOTION_SENSOR SET ANOMALY_DETECTED =? WHERE NAME=?;";

            PreparedStatement pstmt=connection.prepareStatement(updateHeatSensorSql);
            pstmt.setBoolean(1, sensor.isAnomalyDetected());
            pstmt.setString(2, sensor.getDeviceName());
            pstmt.execute();

            logger.info("Motion sensor measurement updated!");

        }catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating Smoke Sensor's name to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();

            logger.error("Error updating Heat sensor's measures to database");
        }
    }
    public static void updateMeasurableSmokeSensor(SmokeSensor sensor){
        try(Connection connection=connectToDatabase()){
            String updateHeatSensorSql="UPDATE SMOKE_SENSOR SET CURRENT_OBSCURATION =? WHERE NAME=?;";

            PreparedStatement pstmt=connection.prepareStatement(updateHeatSensorSql);
            pstmt.setFloat(1, sensor.getCurrentObscuration());
            pstmt.setString(2, sensor.getDeviceName());
            pstmt.execute();

            logger.info("Smoke sensor measurement updated!");


        }catch(SQLException|IOException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error updating Smoke Sensor's name to database.");
            alert.setContentText("Please try again.");
            alert.showAndWait();

            logger.error("Error updating Smoke sensor's measures to database");
        }
    }



}
