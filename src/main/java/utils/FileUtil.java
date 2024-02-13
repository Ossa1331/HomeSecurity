package utils;

import classes.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileUtil {

    private static final Logger logger= LoggerFactory.getLogger(FileUtil.class);

    private static final String CAMERAS_SERIALIZATION_FILE_NAME = "dat/serialized-cameras.txt";

    private static final String CO2_SENSORS_SERIALIZATION_FILE_NAME = "dat/serialized-co2sensors.txt";

    private static final String GLASS_BREAK_SENSORS_SERIALIZATION_FILE_NAME = "dat/serialized-glassbreaksensors.txt";

    private static final String HEAT_SENSORS_SERIALIZATION_FILE_NAME = "dat/serialzed-heatsensors.txt";

    private static final String MOTION_SENSORS_SERIALIZATION_FILE_NAME = "dat/serialized-motionsensors.txt";

    private static final String SMOKE_SENSORS_SERIALIZATION_FILE_NAME="dat/serialized-smokesensors.txt";

    public static void serializeCamera(Camera camera) {
        try {
            FileOutputStream fileOut = new FileOutputStream(CAMERAS_SERIALIZATION_FILE_NAME, true);

            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(camera);

            objectOut.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void serializeCO2Sensor(CO2Sensor sensor) {
        try {
            FileOutputStream fileOut = new FileOutputStream(CO2_SENSORS_SERIALIZATION_FILE_NAME, true);

            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(sensor);

            objectOut.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void serializeGlassBreakSensor(GlassBreakSensor sensor) {
        try {
            FileOutputStream fileOut = new FileOutputStream(GLASS_BREAK_SENSORS_SERIALIZATION_FILE_NAME, true);

            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(sensor);

            objectOut.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void serializeHeatSensor(HeatSensor sensor) {
        try {
            FileOutputStream fileOut = new FileOutputStream(HEAT_SENSORS_SERIALIZATION_FILE_NAME, true);

            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(sensor);

            objectOut.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void serializeMotionSensor(MotionSensor sensor) {
        try {
            FileOutputStream fileOut = new FileOutputStream(MOTION_SENSORS_SERIALIZATION_FILE_NAME, true);

            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(sensor);

            objectOut.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void serializeSmokeSensor(SmokeSensor sensor) {
        try {
            FileOutputStream fileOut = new FileOutputStream(SMOKE_SENSORS_SERIALIZATION_FILE_NAME, true);

            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(sensor);

            objectOut.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static List<Camera> deserializeCamerasFromFile() {
        List<Camera> deserializedCameras = new ArrayList<>();
        try (ObjectInputStream cameraStream = new ObjectInputStream(new FileInputStream(CAMERAS_SERIALIZATION_FILE_NAME))) {
            while (true) {
                try {
                    Camera camera = (Camera) cameraStream.readObject();
                    deserializedCameras.add(camera);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Error while deserializing Cameras from file", ex);
        }
        return deserializedCameras;
    }
    public static List<CO2Sensor> deserializeC02SensorsFromFile() {
        List<CO2Sensor> deserializedCO2Sensors = new ArrayList<>();
        try (ObjectInputStream sensorStream = new ObjectInputStream(new FileInputStream(CO2_SENSORS_SERIALIZATION_FILE_NAME))) {
            while (true) {
                try {
                    CO2Sensor sensor = (CO2Sensor) sensorStream.readObject();
                    deserializedCO2Sensors.add(sensor);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Error while deserializing CO2 Sensors from file", ex);
        }
        return deserializedCO2Sensors;
    }
    public static List<GlassBreakSensor> deserializeGlassBreakSensorsFromFile() {
        List<GlassBreakSensor> deserializedGlassBreakSensors = new ArrayList<>();
        try (ObjectInputStream sensorStream = new ObjectInputStream(new FileInputStream(GLASS_BREAK_SENSORS_SERIALIZATION_FILE_NAME))) {
            while (true) {
                try {
                    GlassBreakSensor sensor = (GlassBreakSensor) sensorStream.readObject();
                    deserializedGlassBreakSensors.add(sensor);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Error while deserializing Glass Break Sensors from file", ex);
        }
        return deserializedGlassBreakSensors;
    }
    public static List<HeatSensor> deserializeHeatSensorsFromFile() {
        List<HeatSensor> deserializedHeatSensors = new ArrayList<>();
        try (ObjectInputStream sensorStream = new ObjectInputStream(new FileInputStream(HEAT_SENSORS_SERIALIZATION_FILE_NAME))) {
            while (true) {
                try {
                    HeatSensor sensor = (HeatSensor) sensorStream.readObject();
                    deserializedHeatSensors.add(sensor);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Error while deserializing Heat Sensor from file" ,ex);
        }
        return deserializedHeatSensors;
    }
    public static List<MotionSensor> deserializeMotionSensorsFromFile() {
        List<MotionSensor> deserializedMotionSensors = new ArrayList<>();
        try (ObjectInputStream sensorStream = new ObjectInputStream(new FileInputStream(MOTION_SENSORS_SERIALIZATION_FILE_NAME))) {
            while (true) {
                try {
                    MotionSensor sensor = (MotionSensor) sensorStream.readObject();
                    deserializedMotionSensors.add(sensor);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Error while deserializing Motion Sensors from file",ex);
        }
        return deserializedMotionSensors;
    }
    public static List<SmokeSensor> deserializeSmokeSensorsFromFile() {
        List<SmokeSensor> deserializedSmokeSensors = new ArrayList<>();
        try (ObjectInputStream sensorStream = new ObjectInputStream(new FileInputStream(CAMERAS_SERIALIZATION_FILE_NAME))) {
            while (true) {
                try {
                    SmokeSensor sensor = (SmokeSensor) sensorStream.readObject();
                    deserializedSmokeSensors.add(sensor);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            logger.error("Error while deserializing Smoke Sensors from file", ex);
        }
        return deserializedSmokeSensors;
    }

    public static List<Device> getAllDevicesFromFile(){
        List<Camera> allCameras= deserializeCamerasFromFile();
        List<CO2Sensor> allCo2Sensors=deserializeC02SensorsFromFile();
        List<GlassBreakSensor> allGlassBreakSenors=deserializeGlassBreakSensorsFromFile();
        List<HeatSensor> allHeatSensors=deserializeHeatSensorsFromFile();
        List<MotionSensor> allMotionSensors=deserializeMotionSensorsFromFile();
        List<SmokeSensor> allSmokeSensors=deserializeSmokeSensorsFromFile();

        List<Device> allDevices = new ArrayList<>(allCameras);
        allDevices.addAll(allCo2Sensors);
        allDevices.addAll(allGlassBreakSenors);
        allDevices.addAll(allHeatSensors);
        allDevices.addAll(allMotionSensors);
        allDevices.addAll(allSmokeSensors);

        return allDevices;
    }

}
