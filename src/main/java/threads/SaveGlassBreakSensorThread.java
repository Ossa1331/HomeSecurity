package threads;

import classes.GlassBreakSensor;
import classes.Change;
import javafx.application.Platform;

public class SaveGlassBreakSensorThread extends DatabaseThreads implements Runnable{

    private final GlassBreakSensor glassBreakSensor;
    private final Change change;

    public SaveGlassBreakSensorThread(GlassBreakSensor glassBreakSensor, Change change){
        this.glassBreakSensor=glassBreakSensor;
        this.change=change;
    }

    public void run(){
        Platform.runLater(()->{
            try{
                super.saveGlassBreakSensor(glassBreakSensor, change);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
