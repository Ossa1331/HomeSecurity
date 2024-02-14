package classes;

import enums.Locations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.UpdateMeasureCO2Sensor;
import utils.DatabaseUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class CO2Sensor extends Device implements Serializable {

    private static final Logger logger= LoggerFactory.getLogger(CO2Sensor.class);

    private static final Executor executor= Executors.newSingleThreadExecutor();

    private Float currentCO2;

    public CO2Sensor(Long deviceId, String deviceName, Boolean deviceStatus, String deviceModel, String deviceSerialNumber, String deviceManufacturer, LocalDate deviceManufacturingDate, Float currentCO2, Locations location, LocalDateTime dateAdded) {
        super(deviceId, deviceName, deviceStatus, deviceModel, deviceSerialNumber, deviceManufacturer, deviceManufacturingDate, location, "CO2 Sensor", dateAdded);
        this.currentCO2=currentCO2;
    }
    public Float getCurrentCO2() {
        return currentCO2;
    }

    public static CO2SensorBuilder builder(){
        return new CO2SensorBuilder();
    }

    public Float measure(){
        Random random=new Random();
        this.currentCO2=random.nextFloat()*70;


        Event event=new Event(LocalDateTime.now(),this.getDeviceName(),this.getDeviceType());
        event.checkTriggerEvent(this);

        UpdateMeasureCO2Sensor thread= new UpdateMeasureCO2Sensor(this,event);

        executor.execute(thread);


        logger.info(this.getDeviceName() + " has been measured");

        return currentCO2;
    }
    public static class CO2SensorBuilder extends Device.DeviceBuilder<CO2SensorBuilder>{
        private Float currentCO2;

        CO2SensorBuilder(){}

        public CO2SensorBuilder currentCO2(Float currentCO2){
            this.currentCO2=currentCO2;
            return this;
        }
        @Override
        public CO2Sensor build(){
            return new CO2Sensor(deviceId, deviceName, deviceStatus, deviceModel, deviceSerialNumber,deviceManufacturer,deviceManufacturingDate, currentCO2,location, deviceAdded);
        }
    }
}
