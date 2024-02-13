package threads;

import classes.Camera;
import classes.Change;
import javafx.application.Platform;

public class SaveCameraThread extends DatabaseThreads implements Runnable {

    private final Camera camera;
    private final Change change;
    public SaveCameraThread(Camera camera, Change change){
        this.camera=camera;
        this.change=change;
    }
    @Override
    public void run(){
        Platform.runLater(()->{
            try{
                super.saveCamera(camera, change);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
