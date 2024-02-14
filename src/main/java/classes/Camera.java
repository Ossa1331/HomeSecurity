package classes;

import enums.Locations;
import javafx.scene.chart.PieChart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.UpdateMeasureCO2Sensor;
import threads.UpdateMeasureCamera;
import utils.DatabaseUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Camera extends Device implements Measurable<Boolean>, Serializable {

    private static final Logger logger= LoggerFactory.getLogger(Camera.class);
    private static final Executor executor= Executors.newSingleThreadExecutor();

    private final LocalDateTime currentCameraTime;

    private Boolean humanIdentified;
    public Camera(Long deviceId, String deviceName, Boolean deviceStatus, String deviceModel, String deviceSerialNumber, String deviceManufacturer, LocalDate deviceManufacturingDate, Locations location, LocalDateTime dateAdded) {
        super(deviceId, deviceName, deviceStatus, deviceModel, deviceSerialNumber, deviceManufacturer,deviceManufacturingDate, location, "Camera", dateAdded);

        this.currentCameraTime=LocalDateTime.now();

        this.humanIdentified=false;
    }

    public LocalDateTime getCurrentCameraTime() {
        return currentCameraTime;
    }

    public Boolean getHumanIdentified() {
        return humanIdentified;
    }

    public void setHumanIdentified(Boolean humanIdentified) {
        this.humanIdentified = humanIdentified;
    }

    public static CameraBuilder builder(){
        return new CameraBuilder();
    }

    public Boolean measure(){
        Random random = new Random();
        this.humanIdentified=random.nextBoolean();

        Event event=new Event(LocalDateTime.now(),this.getDeviceName(),this.getDeviceType());
        event.checkTriggerEvent(this);

        UpdateMeasureCamera thread=new UpdateMeasureCamera(this,event);

        executor.execute(thread);

        logger.info(this.getDeviceName() + " has been measured");

        return this.humanIdentified;
    }

    public static class CameraBuilder extends Device.DeviceBuilder<CameraBuilder>{

        CameraBuilder(){}

        @Override
        public Camera build(){
            return new Camera(deviceId, deviceName, deviceStatus, deviceModel, deviceSerialNumber,deviceManufacturer,deviceManufacturingDate,location, deviceAdded);

        }
    }
}
