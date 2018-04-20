package webservice.blservice.account;

import org.springframework.stereotype.Service;
import trapx00.tagx00.response.user.WorkerInfoResponse;

@Service
public interface WorkerInfoBlService {

    /**
     * get workerinfo of exp credits level and instance count
     *
     * @param workerUsername
     * @return
     */
    WorkerInfoResponse getWorkerInfo(String workerUsername);

}
