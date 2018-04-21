package master.blservice;

import master.response.ReceiveCompleteReceivedResponse;
import master.response.SendCompleteReceivedResponse;

public interface MasterBlService {
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
}
