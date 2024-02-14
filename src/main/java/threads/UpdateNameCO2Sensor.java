package threads;

import classes.CO2Sensor;
import classes.Camera;
import classes.Change;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateNameCO2Sensor extends DatabaseThreads implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(CO2Sensor.class);
    private final String result;
    private final CO2Sensor sensor;
    private final Change change;
    public UpdateNameCO2Sensor(CO2Sensor sensor, String result, Change change){
        this.sensor=sensor;
        this.result=result;
        this.change=change;
    }
    @Override
    public void run(){
        try{
            Platform.runLater(()->{
                super.updateNameCO2Sensor(sensor, change, result);
            });
        }catch(Exception e) {
            logger.error("there has been an error in thread runtime. ", e);
        }
    }
}
