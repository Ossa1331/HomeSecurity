package classes;

import enums.Locations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.UpdateMeasureMotionSensor;
import utils.DatabaseUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MotionSensor extends Device implements Measurable<Boolean>, Serializable {

    private static final Executor executor= Executors.newSingleThreadExecutor();


    private static final Logger logger= LoggerFactory.getLogger(MotionSensor.class);


    private Boolean anomalyDetected;
    public MotionSensor(Long deviceId, String deviceName, Boolean deviceStatus, String deviceModel, String deviceSerialNumber, String deviceManufacturer, LocalDate deviceManufacturingDate, Locations location, LocalDateTime deviceAdded) {
        super(deviceId, deviceName, deviceStatus, deviceModel,deviceSerialNumber, deviceManufacturer,deviceManufacturingDate, location, "Motion Sensor", deviceAdded);
        anomalyDetected=false;
    }


    public Boolean isAnomalyDetected() {
        return anomalyDetected;
    }

    public void setAnomalyDetected(Boolean anomalyDetected) {
        this.anomalyDetected = anomalyDetected;
    }

    public static MotionSensorBuilder builder(){
        return new MotionSensorBuilder();
    }
    @Override
    public Boolean measure(){
        Random random = new Random();
        this.anomalyDetected=random.nextBoolean();


        Event event=new Event(LocalDateTime.now(),this.getDeviceName(),this.getDeviceType());
        event.checkTriggerEvent(this);

        UpdateMeasureMotionSensor thread=new UpdateMeasureMotionSensor(this,event);
        executor.execute(thread);

        return this.anomalyDetected;
    }
    public static class MotionSensorBuilder extends Device.DeviceBuilder<MotionSensorBuilder>{

        MotionSensorBuilder(){}

        @Override
        public MotionSensor build(){
            return new MotionSensor(deviceId, deviceName, deviceStatus, deviceModel, deviceSerialNumber,deviceManufacturer,deviceManufacturingDate,location, deviceAdded);
        }
    }
}
