package threads;

import classes.CO2Sensor;
import classes.Camera;
import classes.Event;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateMeasureCamera extends DatabaseThreads implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(CO2Sensor.class);

    private final Camera camera;
    private final Event event;

    public UpdateMeasureCamera(Camera camera, Event event){
        this.camera=camera;
        this.event=event;
    }
    @Override
    public void run(){
        logger.info("thread instanced");

        try{
            Platform.runLater(()->{
                super.updateCamera(camera, event);
            });

        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
