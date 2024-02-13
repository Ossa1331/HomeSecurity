package threads;

import classes.CO2Sensor;
import classes.Event;
import classes.SmokeSensor;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateMeasureSmokeSensor extends DatabaseThreads implements Runnable{
    private static final Logger logger= LoggerFactory.getLogger(CO2Sensor.class);

    private final SmokeSensor sensor;
    private final Event event;

    public UpdateMeasureSmokeSensor(SmokeSensor sensor, Event event){
        this.sensor=sensor;
        this.event=event;
    }
    @Override
    public void run(){
        logger.info("thread instanced");

        try{
            Platform.runLater(()->{
                super.updateSmokeSensor(sensor, event);
            });
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
