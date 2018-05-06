package webservice.parameters;

public class SaveInfoParameters {
    private String info;

    public SaveInfoParameters() {
    }

    public SaveInfoParameters(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
