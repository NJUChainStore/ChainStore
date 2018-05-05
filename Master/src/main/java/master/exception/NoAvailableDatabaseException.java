package master.exception;

import master.response.WrongResponse;

public class NoAvailableDatabaseException extends Exception {

    private WrongResponse response = new WrongResponse(10001, "No available database.");

    public WrongResponse getResponse() {
        return response;
    }
}
