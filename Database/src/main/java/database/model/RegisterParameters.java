package database.model;

public class RegisterParameters {
    private String ip;

    public RegisterParameters() {
    }

    public RegisterParameters(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
