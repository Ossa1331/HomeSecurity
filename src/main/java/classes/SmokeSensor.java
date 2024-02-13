package classes;

import enums.Locations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.UpdateMeasureCamera;
import threads.UpdateMeasureSmokeSensor;
import utils.DatabaseUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SmokeSensor extends Device implements Measurable<Float>, Serializable {

    private static final Executor executor= Executors.newSingleThreadExecutor();


    private static final Logger logger= LoggerFactory.getLogger(SmokeSensor.class);

    private Float currentObscuration;

    public SmokeSensor(Long deviceId, String deviceName, Boolean deviceStatus,String deviceModel, String deviceSerialNumber, String deviceManufacturer,  LocalDate deviceManufacturingDate, Locations location, Float currentObscuration, LocalDateTime dateAdded) {
        super(deviceId, deviceName, deviceStatus, deviceManufacturer,deviceModel,deviceSerialNumber, deviceManufacturingDate, location, "Smoke Sensor", dateAdded);
        this.currentObscuration=currentObscuration;
    }


    public Float getCurrentObscuration() {
        return currentObscuration;
    }

    public void setCurrentObscuration(Float currentObscuration) {
        this.currentObscuration = currentObscuration;
    }

    public static SmokeSensorBuilder builder(){
        return new SmokeSensorBuilder();
    }
    @Override
    public Float measure(){
        Random random = new Random();
        this.currentObscuration=random.nextFloat()*80;

        Event event=new Event(LocalDateTime.now(),this.getDeviceName(),this.getDeviceType());
        event.checkTriggerEvent(this);

        UpdateMeasureSmokeSensor thread=new UpdateMeasureSmokeSensor(this,event);
        executor.execute(thread);

        return this.currentObscuration;
    }


    public static class SmokeSensorBuilder extends Device.DeviceBuilder<SmokeSensorBuilder>{
        private Float currentObscuration;

        SmokeSensorBuilder(){}

        public SmokeSensorBuilder currentObscuration(Float currentObscuration){
            this.currentObscuration=currentObscuration;
            return this;
        }
        @Override
        public SmokeSensor build(){
            return new SmokeSensor(deviceId, deviceName, deviceStatus, deviceModel, deviceSerialNumber,deviceManufacturer,deviceManufacturingDate,location, currentObscuration, deviceAdded);
        }
    }
}
