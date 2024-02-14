package threads;

import classes.CO2Sensor;
import classes.Change;
import classes.MotionSensor;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateNameMotionSensor extends DatabaseThreads implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(CO2Sensor.class);
    private final String result;
    private final MotionSensor sensor;
    private final Change change;
    public UpdateNameMotionSensor(MotionSensor sensor, String result, Change change){
        this.sensor=sensor;
        this.result=result;
        this.change=change;
    }
    @Override
    public void run(){
        try{
            Platform.runLater(()->{
                super.updateNameMotionSensor(sensor, change, result);
            });
        }catch(Exception e) {
            logger.error("there has been an error in thread runtime. ", e);
        }
    }
}
