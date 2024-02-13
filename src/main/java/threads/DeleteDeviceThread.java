package threads;

import classes.Change;
import classes.Device;
import javafx.application.Platform;

public class DeleteDeviceThread extends DatabaseThreads implements Runnable{
    private final Change change;
    private final Device device;

    public DeleteDeviceThread(Device device, Change change){
        this.device=device;
        this.change=change;
    }
    @Override
    public void run(){
        Platform.runLater(()->{
            try{
                super.DeleteDevice(device, change);
            }catch(Exception e){
                e.printStackTrace();
            }
        });
    }
}
