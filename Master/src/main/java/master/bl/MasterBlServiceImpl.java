package master.bl;

import master.blservice.MasterBlService;
import master.response.ReceiveCompleteReceivedResponse;
import master.response.SendCompleteReceivedResponse;
import org.springframework.stereotype.Service;

@Service
public class MasterBlServiceImpl implements MasterBlService {
    /**
     * send complete sign and set its state to free
     *
     * @param id
     * @return
     */
    @Override
    public SendCompleteReceivedResponse sendComplete(String id) {
        return null;
    }

    /**
     * receive complete sign and set its state to free
     *
     * @param id
     * @return
     */
    @Override
    public ReceiveCompleteReceivedResponse receiveComplete(String id) {
        return null;
    }
}
