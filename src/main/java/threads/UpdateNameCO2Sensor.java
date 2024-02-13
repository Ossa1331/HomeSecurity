package threads;

import classes.CO2Sensor;
import classes.Camera;
import classes.Change;
import javafx.application.Platform;

public class UpdateNameCO2Sensor extends DatabaseThreads implements Runnable {
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
            e.printStackTrace();
        }
    }
}
