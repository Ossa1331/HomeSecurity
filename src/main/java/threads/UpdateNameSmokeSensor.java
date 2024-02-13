package threads;

import classes.CO2Sensor;
import classes.Change;
import classes.SmokeSensor;
import javafx.application.Platform;

public class UpdateNameSmokeSensor extends DatabaseThreads implements Runnable {
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
            e.printStackTrace();
        }
    }
}
