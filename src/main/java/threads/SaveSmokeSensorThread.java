package threads;

import classes.SmokeSensor;
import classes.Change;
import javafx.application.Platform;

public class SaveSmokeSensorThread extends DatabaseThreads implements Runnable {
    private final SmokeSensor smokeSensor;
    private final Change change;

    public SaveSmokeSensorThread(SmokeSensor smokeSensor, Change change){
        this.smokeSensor=smokeSensor;
        this.change=change;
    }
    @Override
    public void run(){
        Platform.runLater(()->{
            try{
                super.saveSmokeSensor(smokeSensor, change);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
