package threads;

import classes.Change;
import javafx.application.Platform;

public class SaveChangeThread extends DatabaseThreads implements Runnable{
    private final Change change;


    public SaveChangeThread(Change change){
        this.change=change;
    }
    @Override
    public void run(){
        Platform.runLater(()->{
            try{
                super.saveChange(change);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
