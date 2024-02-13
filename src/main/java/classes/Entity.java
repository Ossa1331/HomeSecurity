package classes;

public class Entity {

    public Entity(Long deviceId, String deviceName) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    private Long deviceId;

    private String deviceName;

    public static class EntityBuilder<T extends EntityBuilder>{
        protected Long deviceId;
        protected String deviceName;

        public T deviceName(String deviceName){
            this.deviceName=deviceName;
            return (T)this;
        }
        public T deviceId(long deviceId){
            this.deviceId=deviceId;
            return (T)this;
        }
        public Entity build(){
            return new Entity(deviceId, deviceName);
        }
    }
}
