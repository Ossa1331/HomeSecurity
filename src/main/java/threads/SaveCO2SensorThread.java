package threads;

import classes.CO2Sensor;
import classes.Change;
import javafx.application.Platform;

public class SaveCO2SensorThread extends DatabaseThreads implements Runnable{

    private final CO2Sensor co2Sensor;
    private final Change change;

    public SaveCO2SensorThread(CO2Sensor co2Sensor, Change change){
        this.co2Sensor=co2Sensor;
        this.change=change;
    }
    @Override
    public void run(){
        Platform.runLater(()->{
            try{
                super.saveCO2Sensor(co2Sensor, change);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
