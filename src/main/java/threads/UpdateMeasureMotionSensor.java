package threads;

import classes.CO2Sensor;
import classes.Event;
import classes.MotionSensor;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateMeasureMotionSensor  extends DatabaseThreads implements Runnable{
    private static final Logger logger= LoggerFactory.getLogger(CO2Sensor.class);

    private final MotionSensor sensor;
    private final Event event;

    public UpdateMeasureMotionSensor(MotionSensor sensor, Event event){
        this.sensor=sensor;
        this.event=event;
    }
    @Override
    public void run(){
        logger.info("thread instanced");

        try{
            Platform.runLater(()->{
                super.updateMotionSensor(sensor, event);
            });

        }catch(Exception e) {
            logger.error("there has been an error in thread runtime. ", e);
        }
    }

}
