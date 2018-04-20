package webservice.dataservice.account;

import trapx00.tagx00.entity.mission.instance.Instance;

public interface WorkerInfoDataService {

    /**
     * @param workerUsername
     * @return
     */
    Instance[] getInstanceByWorkerUsername(String workerUsername);
}
