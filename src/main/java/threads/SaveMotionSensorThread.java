package threads;

import classes.MotionSensor;
import classes.Change;
import javafx.application.Platform;

public class SaveMotionSensorThread extends DatabaseThreads implements Runnable{

    private final MotionSensor motionSensor;
    private final Change change;

    public SaveMotionSensorThread(MotionSensor motionSensor, Change change){
        this.motionSensor=motionSensor;
        this.change=change;
    }
    @Override
    public void run(){
        Platform.runLater(()->{
            try{
                super.saveMotionSensor(motionSensor, change);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
