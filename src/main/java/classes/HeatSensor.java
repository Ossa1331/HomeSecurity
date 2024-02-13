package classes;

import enums.Locations;
import exceptions.LowerHigherThanUpperLimitException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import threads.UpdateMeasureHeatSensor;
import utils.DatabaseUtil;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public non-sealed class HeatSensor extends Device implements CalculateCAndF, Measurable<Float>, Serializable {
    private static final Executor executor= Executors.newSingleThreadExecutor();

    private static final Logger logger= LoggerFactory.getLogger(HeatSensor.class);

    private final Float sensorUpperLimit;

    private final Float sensorLowerLimit;

    private Float temperatureInC;

    private Float temperatureInF;



    public HeatSensor(Long deviceId, String deviceName, Boolean deviceStatus, String deviceModel, String deviceSerialNumber, String deviceManufacturer,
                      LocalDate deviceManufacturingDate, Locations location, Float sensorUpperLimit, Float sensorLowerLimit, Float temperatureInC, LocalDateTime dateAdded) {
        super(deviceId, deviceName, deviceStatus,deviceModel,deviceSerialNumber, deviceManufacturer, deviceManufacturingDate, location, "Heat Sensor", dateAdded);
        this.sensorLowerLimit=sensorLowerLimit;
        this.sensorUpperLimit=sensorUpperLimit;
        this.temperatureInC=temperatureInC;
        this.temperatureInF=celsiusToFahrenheit(this.temperatureInC);
    }

    public Float celsiusToFahrenheit(Float tempInCelsius){

        this.temperatureInF= (tempInCelsius*(9/5))+32;

        return this.temperatureInF;
    }

    @Override
    public Float fahrenheitToCelsius(Float tempInFahrenheit){

        this.temperatureInC=(tempInFahrenheit-32) *(5/9);

        return this.temperatureInC;
    }


    public Float getTemperatureInC() {
        return temperatureInC;
    }

    public void setTemperatureInC(Float temperatureInC) {
        this.temperatureInC = temperatureInC;
    }

    public Float getSensorUpperLimit() {
        return sensorUpperLimit;
    }

    public Float getSensorLowerLimit() {
        return sensorLowerLimit;
    }

    public Float getTemperatureInF() {
        return temperatureInF;
    }

    public void setTemperatureInF(Float temperatureInF) {
        this.temperatureInF = temperatureInF;
    }

    public static HeatSensorBuilder builder(){
        return new HeatSensorBuilder();
    }


    @Override
    public Float measure() throws LowerHigherThanUpperLimitException {
        if(this.sensorLowerLimit>=this.sensorUpperLimit){
            throw new LowerHigherThanUpperLimitException();
        }
        Random random = new Random();
        this.temperatureInC=this.sensorLowerLimit+ random.nextFloat()*(this.sensorUpperLimit-this.sensorLowerLimit);
        this.temperatureInF=celsiusToFahrenheit(this.temperatureInC);

        Event event=new Event(LocalDateTime.now(),this.getDeviceName(),this.getDeviceType());
        event.checkTriggerEvent(this);

        UpdateMeasureHeatSensor thread=new UpdateMeasureHeatSensor(this,event);
        executor.execute(thread);

        logger.info(this.getDeviceName() + " has been measured");

        return this.temperatureInC;
    }


    public static class HeatSensorBuilder extends Device.DeviceBuilder<HeatSensorBuilder>{
        private Float sensorUpperLimit;

        private Float sensorLowerLimit;

        private Float temperatureInC;


        HeatSensorBuilder(){}

        public HeatSensorBuilder sensorUpperLimit(Float sensorUpperLimit){
            this.sensorUpperLimit=sensorUpperLimit;
            return this;
        }
        public HeatSensorBuilder sensorLowerLimit(Float sensorLowerLimit){
            this.sensorLowerLimit=sensorLowerLimit;
            return this;
        }
        public HeatSensorBuilder temperatureInC(Float temperatureInC){
            this.temperatureInC=temperatureInC;
            Float temperatureInF = (temperatureInC * (9 / 5)) + 32;
            return this;
        }
        @Override
        public HeatSensor build(){
            return new HeatSensor(deviceId, deviceName, deviceStatus, deviceModel, deviceSerialNumber,deviceManufacturer,deviceManufacturingDate,location, sensorUpperLimit, sensorLowerLimit, temperatureInC, deviceAdded);
        }
    }

}
