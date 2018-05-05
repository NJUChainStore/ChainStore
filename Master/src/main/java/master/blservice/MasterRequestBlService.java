package master.blservice;

import master.global.entity.DatabaseItem;

public interface MasterRequestBlService {
    /**
     * send request to ask database to validate itself
     *
     * @param databaseItem
     * @param index
     */
    void sendValidateRequest(DatabaseItem databaseItem, int index);
}
