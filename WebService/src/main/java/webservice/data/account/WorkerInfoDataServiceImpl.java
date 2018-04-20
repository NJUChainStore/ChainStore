package webservice.data.account;

import fintech100k.WebService.dataservice.account.WorkerInfoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trapx00.tagx00.data.dao.mission.ImageMissionDao;
import trapx00.tagx00.data.dao.mission.instance.ImageInstanceDao;
import trapx00.tagx00.entity.mission.instance.Instance;

import java.util.ArrayList;

@Service
public class WorkerInfoDataServiceImpl implements WorkerInfoDataService {

    private final ImageInstanceDao imageInstanceDao;
    private final ImageMissionDao imageMissionDao;

    @Autowired
    public WorkerInfoDataServiceImpl(ImageInstanceDao imageInstanceDao, ImageMissionDao imageMissionDao) {
        this.imageInstanceDao = imageInstanceDao;
        this.imageMissionDao = imageMissionDao;
    }

    /**
     * @param workerUsername
     * @return
     */
    @Override
    public Instance[] getInstanceByWorkerUsername(String workerUsername) {
        ArrayList<Instance> instances = new ArrayList<>();

        //获得每个种类的instance列表
        instances.addAll(imageInstanceDao.findImageInstancesByWorkerUsername(workerUsername));

        if (instances == null)
            return null;
        Instance[] instances1 = new Instance[instances.size()];
        for (int i = 0; i < instances.size(); i++) {
            instances1[i] = instances.get(i);
        }
        return instances1;
    }
}
