package master.blservice;

import master.exception.NoAvailableDatabaseException;
import master.global.entity.Role;
import master.global.entity.Table;
import master.response.*;

public interface MasterBlService {
    /**
     * register a machine
     *
     * @param role
     * @param ip
     * @return
     */
    RegisterResponse register(Role role, String ip);

    /**
     * send complete sign and set its state to free
     *
     * @param id
     * @return
     */
    SendCompleteReceivedResponse sendComplete(String id);

    /**
     * receive complete sign and set its state to free
     *
     * @param id
     * @return
     */
    ReceiveCompleteReceivedResponse receiveComplete(String id);

    /**
     * find the block's info with the index and offset
     *
     * @param blockIndex
     * @param blockOffset
     * @return
     */
    FindBlockInfoResponse findBlockInfo(long blockIndex, int blockOffset) throws NoAvailableDatabaseException;

    /**
     * save the info and broadcast
     *
     * @param data
     * @return
     */
    SaveInfoResponse saveInfoAndBroadcast(String data);

    /**
     * is the database all updated
     *
     * @param latestBlockIndex
     * @return
     */
    IsDatabaseUpdateResponse isDatabaseUpdate(int latestBlockIndex);

    /**
     * update self's buffer and table
     *
     * @param table
     * @return
     */
    UpdateSelfResponse updateTable(Table table);

    /**
     * save the info
     *
     * @param info
     * @return
     */
    SaveInfoResponse saveInfo(String info);

    /**
     * clear the master buffer
     *
     * @return
     */
    BufferClearResponse clearBuffer();
}
