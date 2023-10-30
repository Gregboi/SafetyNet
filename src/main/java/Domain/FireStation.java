package Domain;

public class FireStation {
    private String fireStationAddress;
    private int fireStation;

    public FireStation() {
    }

    public FireStation(String fireStationAddress, int fireStation) {
        this.fireStationAddress = fireStationAddress;
        this.fireStation = fireStation;
    }

    public String getAddress() {
        return fireStationAddress;
    }

    public void setAddress(String fireStationAddress) {
        this.fireStationAddress = fireStationAddress;
    }

    public int getFireStation() {
        return fireStation;
    }

    public void setFireStation(int fireStation) {
        this.fireStation = fireStation;
    }

}
