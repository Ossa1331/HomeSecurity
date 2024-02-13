package threads;

import classes.Change;
import classes.Device;
import javafx.application.Platform;

public class UpdateDeviceStatus extends DatabaseThreads implements Runnable {
    private final Change change;

    private final Device device;

    public UpdateDeviceStatus(Device device, Change change){
        this.change=change;
        this.device=device;
    }
    @Override
    public void run(){
        Platform.runLater(()->{
            try{
                super.updateStatusDevice(device,change);
            } catch(Exception ex){
                ex.printStackTrace();
            }
        });
    }
}
