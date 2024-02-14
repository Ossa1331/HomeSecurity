package threads;

import classes.CO2Sensor;
import classes.Event;
import classes.HeatSensor;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateMeasureHeatSensor extends DatabaseThreads implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(CO2Sensor.class);

    private final HeatSensor sensor;
    private final Event event;

    public UpdateMeasureHeatSensor(HeatSensor sensor, Event event){
        this.sensor=sensor;
        this.event=event;
    }
    @Override
    public void run(){
        logger.info("thread instanced");

        try{
            Platform.runLater(()->{
                super.updateHeatSensor(sensor, event);
            });

        }catch(Exception e) {
            logger.error("there has been an error in thread runtime. ", e);
        }
    }
}
