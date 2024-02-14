package threads;

import classes.CO2Sensor;
import classes.Camera;
import classes.Change;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.DatabaseUtil;

public class UpdateNameCamera extends DatabaseThreads implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(CO2Sensor.class);
    private final String result;
    private final Camera camera;
    private final Change change;
    public UpdateNameCamera(Camera camera, String result, Change change){
        this.camera=camera;
        this.result=result;
        this.change=change;
    }
    @Override
    public void run(){
        try{
            Platform.runLater(()->{
                super.updateNameCamera(camera, change, result);
            });
        }catch(Exception e) {
            logger.error("there has been an error in thread runtime. ", e);
        }
    }
}
