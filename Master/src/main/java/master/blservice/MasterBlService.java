package master.blservice;

import master.global.entity.Role;
import master.response.ReceiveCompleteReceivedResponse;
import master.response.RegisterResponse;
import master.response.SendCompleteReceivedResponse;

public interface MasterBlService {
    /**
     * register a machine
     *
     * @param role
     * @return
     */
    RegisterResponse register(Role role);

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
