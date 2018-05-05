package master.blservice;

import master.exception.NoAvailableDatabaseException;
import master.global.entity.Role;
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
    FindBlockInfoResponse findBlockInfo(int blockIndex, int blockOffset) throws NoAvailableDatabaseException;

    /**
     * save the info
     *
     * @param data
     * @return
     */
    SaveInfoResponse saveInfo(String data);
}
