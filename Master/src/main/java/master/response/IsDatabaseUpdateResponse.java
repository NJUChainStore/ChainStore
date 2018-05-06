package master.response;

public class IsDatabaseUpdateResponse extends Response {
    private boolean isUpdate;

    public IsDatabaseUpdateResponse() {
    }

    public IsDatabaseUpdateResponse(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }
}
