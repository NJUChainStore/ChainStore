package master.blservice;

import master.global.entity.DatabaseItem;

public interface MasterRequestBlService {
    /**
     * send request to ask database to validate itself
     *
     * @param databaseItem
     */
    void sendValidateRequest(DatabaseItem databaseItem);

    /**
     * get block from mining and broadcast to database
     */
    void calculateBlockAndBroadcast();
}
