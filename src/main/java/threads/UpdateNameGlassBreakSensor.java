package threads;

import classes.CO2Sensor;
import classes.Change;
import classes.GlassBreakSensor;
import javafx.application.Platform;

public class UpdateNameGlassBreakSensor extends DatabaseThreads implements Runnable{
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
            e.printStackTrace();
        }
    }
}
