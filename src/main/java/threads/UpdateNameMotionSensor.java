package threads;

import classes.CO2Sensor;
import classes.Change;
import classes.MotionSensor;
import javafx.application.Platform;

public class UpdateNameMotionSensor extends DatabaseThreads implements Runnable {
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
            e.printStackTrace();
        }
    }
}
