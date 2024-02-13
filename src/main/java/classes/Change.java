package classes;

import java.time.LocalDateTime;

public class Change {
    private String deviceName;
    private String deviceType;
    private String message;

    public LocalDateTime getTime_added() {
        return time_added;
    }

    public void setTime_added(LocalDateTime time_added) {
        this.time_added = time_added;
    }

    private LocalDateTime time_added;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Change(String deviceName, String deviceType, String message, LocalDateTime time_added){
        this.deviceName=deviceName;
        this.deviceType=deviceType;
        this.message=message;
        this.time_added=time_added;
    }
}
