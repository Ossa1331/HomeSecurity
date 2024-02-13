package threads;

import classes.Camera;
import classes.Change;
import javafx.application.Platform;
import utils.DatabaseUtil;

public class UpdateNameCamera extends DatabaseThreads implements Runnable {
    private final String result;
    private final Camera camera;
    private final Change change;
    public UpdateNameCamera(Camera camera, String result, Change change){
        this.camera=camera;
        this.result=result;
        this.change=change;
    }
    @Override
    public void run(){
        try{
            Platform.runLater(()->{
                super.updateNameCamera(camera, change, result);
            });
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}
