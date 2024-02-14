package threads;

import classes.CO2Sensor;
import classes.Change;
import classes.GlassBreakSensor;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateNameGlassBreakSensor extends DatabaseThreads implements Runnable{
    private static final Logger logger= LoggerFactory.getLogger(CO2Sensor.class);
    private final String result;
    private final GlassBreakSensor sensor;
    private final Change change;
    public UpdateNameGlassBreakSensor(GlassBreakSensor sensor, String result, Change change){
        this.sensor=sensor;
        this.result=result;
        this.change=change;
    }
    @Override
    public void run(){
        try{
            Platform.runLater(()->{
                super.updateNameGlassBreakSensor(sensor, change, result);
            });
        }catch(Exception e) {
            logger.error("there has been an error in thread runtime. ", e);
        }
    }
}
