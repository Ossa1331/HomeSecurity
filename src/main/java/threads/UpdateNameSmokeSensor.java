package threads;

import classes.CO2Sensor;
import classes.Change;
import classes.SmokeSensor;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateNameSmokeSensor extends DatabaseThreads implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(CO2Sensor.class);
    private final String result;
    private final SmokeSensor sensor;
    private final Change change;
    public UpdateNameSmokeSensor(SmokeSensor sensor, String result, Change change){
        this.sensor=sensor;
        this.result=result;
        this.change=change;
    }
    @Override
    public void run(){
        try{
            Platform.runLater(()->{
                super.updateNameSmokeSensor(sensor, change, result);
            });
        }catch(Exception e) {
            logger.error("there has been an error in thread runtime. ", e);
        }
    }
}
