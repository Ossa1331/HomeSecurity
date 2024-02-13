package threads;

import classes.CO2Sensor;
import classes.Change;
import classes.HeatSensor;
import javafx.application.Platform;

public class UpdateNameHeatSensor extends DatabaseThreads implements Runnable{

    private final String result;
    private final HeatSensor sensor;
    private final Change change;
    public UpdateNameHeatSensor(HeatSensor sensor, String result, Change change){
        this.sensor=sensor;
        this.result=result;
        this.change=change;
    }
    @Override
    public void run(){
        try{
            Platform.runLater(()->{
                super.updateNameHeatSensor(sensor, change, result);
            });
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
