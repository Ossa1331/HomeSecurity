package classes;

import enums.Locations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.UpdateMeasureGlassBreakSensor;
import utils.DatabaseUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class GlassBreakSensor extends Device implements Measurable<Boolean>, Serializable {

    private static final Executor executor= Executors.newSingleThreadExecutor();

    private static final Logger logger= LoggerFactory.getLogger(GlassBreakSensor.class);

    private Boolean glassBreak;
    public GlassBreakSensor(Long deviceId, String deviceName, Boolean deviceStatus, String deviceModel, String deviceSerialNumber, String deviceManufacturer, LocalDate deviceManufacturingDate, Locations location, LocalDateTime dateAdded) {
        super(deviceId, deviceName, deviceStatus, deviceModel, deviceSerialNumber, deviceManufacturer, deviceManufacturingDate, location , "Glass Break Sensor", dateAdded);
        this.glassBreak=false;
    }
    public Boolean getGlassBreak() {
        return glassBreak;
    }


    public void setGlassBreak(Boolean glassBreak) {
        this.glassBreak = glassBreak;
    }

    public static GlassBreakSensorBuilder builder(){
        return new GlassBreakSensorBuilder();
    }

    @Override
    public Boolean measure(){
        Random random = new Random();
        this.glassBreak=random.nextBoolean();



        Event event=new Event(LocalDateTime.now(),this.getDeviceName(),this.getDeviceType());
        event.checkTriggerEvent(this);

        UpdateMeasureGlassBreakSensor thread= new UpdateMeasureGlassBreakSensor(this,event);
        executor.execute(thread);

        logger.info(this.getDeviceName() + " has been measured");

        return this.glassBreak;
    }

    public static class GlassBreakSensorBuilder extends Device.DeviceBuilder<GlassBreakSensorBuilder>{

        GlassBreakSensorBuilder(){}

        @Override
        public GlassBreakSensor build(){
            return new GlassBreakSensor(deviceId, deviceName, deviceStatus, deviceModel, deviceSerialNumber,deviceManufacturer,deviceManufacturingDate,location, deviceAdded);
        }
    }

}
