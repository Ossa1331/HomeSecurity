package threads;

import classes.Change;
import classes.Device;
import com.example.homesecurity.InsertHeatSensorController;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateDeviceStatus extends DatabaseThreads implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(UpdateDeviceStatus.class);
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
                logger.error("There has been an error running a thread");
            }
        });
    }
}
