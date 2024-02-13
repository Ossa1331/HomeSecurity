package threads;

import classes.HeatSensor;
import classes.Change;
import javafx.application.Platform;

public class SaveHeatSensorThread extends DatabaseThreads implements Runnable{
    private final HeatSensor heatSensor;
    private final Change change;

    public SaveHeatSensorThread(HeatSensor heatSensor, Change change){
        this.heatSensor=heatSensor;
        this.change=change;
    }
    @Override
    public void run(){
        Platform.runLater(()->{
            try{
                super.saveHeatSensor(heatSensor, change);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
