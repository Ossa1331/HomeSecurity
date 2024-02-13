package classes;

import enums.Locations;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Device extends Entity{

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    private String deviceType;

    private Boolean deviceStatus;

    private String deviceModel;
    private String deviceSerialNumber;

    private String deviceManufacturer;

    private LocalDate deviceManufacturingDate;


    private final LocalDateTime deviceAdded;

    private Locations location;



    public Device(Long deviceId, String deviceName, Boolean deviceStatus, String deviceModel, String deviceSerialNumber,
                  String deviceManufacturer, LocalDate deviceManufacturingDate, Locations location, String deviceType, LocalDateTime deviceAdded) {
        super(deviceId,deviceName);
        this.deviceStatus = deviceStatus;
        this.deviceManufacturer=deviceManufacturer;
        this.deviceManufacturingDate= deviceManufacturingDate;
        this.location=location;
        this.deviceModel=deviceModel;
        this.deviceSerialNumber=deviceSerialNumber;
        this.deviceAdded=deviceAdded;
        this.deviceType=deviceType;
    }

    public Boolean getDeviceStatus() {
        return deviceStatus;
    }

    public String getModel() {
        return deviceModel;
    }

    public void setDeviceModel(String model) {
        this.deviceModel = model;
    }

    public String getSerialNumber() {
        return deviceSerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.deviceSerialNumber = serialNumber;
    }

    public String getDeviceManufacturer() {
        return deviceManufacturer;
    }

    public void setDeviceManufacturer(String deviceManufacturer) {
        this.deviceManufacturer = deviceManufacturer;
    }

    public LocalDate getDeviceManufacturingDate() {
        return deviceManufacturingDate;
    }

    public void setDeviceManufacturingDate(LocalDate deviceManufacturingDate) {
        this.deviceManufacturingDate = deviceManufacturingDate;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    public void setDeviceStatus(Boolean deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String deviceStatusToString(){
        if(this.getDeviceStatus()) {
            return "ON";
        }
        else{
            return "OFF";
        }
    }
    public LocalDateTime getDeviceAdded() {
        return deviceAdded;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public String getDeviceSerialNumber() {
        return deviceSerialNumber;
    }

    public void setDeviceSerialNumber(String deviceSerialNumber) {
        this.deviceSerialNumber = deviceSerialNumber;
    }

    public static DeviceBuilder<? extends DeviceBuilder> builder(){
        return new DeviceBuilder<>();
    }

    public static class DeviceBuilder<T extends DeviceBuilder<T>> extends Entity.EntityBuilder<DeviceBuilder<T>>{
        protected Boolean deviceStatus;
        protected String deviceType;

        protected String deviceModel;
        protected String deviceSerialNumber;

        protected String deviceManufacturer;

        protected LocalDate deviceManufacturingDate;

        protected LocalDateTime deviceAdded;

        protected Locations location;


        DeviceBuilder(){}

        public T deviceStatus(Boolean deviceStatus){
            this.deviceStatus=deviceStatus;
            return (T)this;
        }
        public T deviceModel(String deviceModel){
            this.deviceModel=deviceModel;
            this.deviceAdded=LocalDateTime.now();
            return (T)this;
        }
        public T deviceSerialNumber(String deviceSerialNumber){
            this.deviceSerialNumber=deviceSerialNumber;
            return (T)this;
        }
        public T deviceManufacturer(String deviceManufacturer){
            this.deviceManufacturer=deviceManufacturer;
            return (T)this;
        }
        public T deviceManufacturingDate(LocalDate deviceManufacturingDate){
            this.deviceManufacturingDate=deviceManufacturingDate;
            return (T)this;
        }
        public T deviceDateAdded(LocalDateTime deviceAdded){
            this.deviceAdded=deviceAdded;
            return (T)this;
        }
        public T location(Locations location){
            this.location=location;
            return (T)this;
        }
        public T deviceType(String deviceType){
            this.deviceType=deviceType;
            return (T)this;
        }

        @Override
        public Device build(){
            return new Device(deviceId,deviceName,deviceStatus,deviceModel,deviceSerialNumber,deviceManufacturer,deviceManufacturingDate,location, deviceType, deviceAdded);
        }


    }


}
