package webservice.data.account;

import fintech100k.WebService.dataservice.account.RequesterInfoDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trapx00.tagx00.data.dao.mission.ImageMissionDao;
import trapx00.tagx00.data.dao.mission.instance.ImageInstanceDao;
import trapx00.tagx00.entity.mission.Mission;
import trapx00.tagx00.entity.mission.instance.Instance;
import trapx00.tagx00.publicdatas.mission.MissionType;

import java.util.ArrayList;

@Service
public class RequesterInfoDataServiceImpl implements RequesterInfoDataService {
    private final ImageInstanceDao imageInstanceDao;
    private final ImageMissionDao imageMissionDao;

    @Autowired
    public RequesterInfoDataServiceImpl(ImageInstanceDao imageInstanceDao, ImageMissionDao imageMissionDao) {
        this.imageInstanceDao = imageInstanceDao;
        this.imageMissionDao = imageMissionDao;
    }

    /**
     * get missions by requesterUsername
     *
     * @param requesterUsername
     * @return
     */
    @Override
    public Mission[] getMissionsByRequesterUsername(String requesterUsername) {

        ArrayList<Mission> missions = new ArrayList<>();
        missions.addAll(imageMissionDao.findAll());
        Mission[] mission = new Mission[missions.size()];
        for (int i = 0; i < missions.size(); i++)
            mission[i] = missions.get(i);
        return mission;
    }

    /**
     * get instances by missionId
     *
     * @param missionId
     * @return
     */
    @Override
    public Instance[] getInstancesByMissionId(int missionId, MissionType missionType) {
        ArrayList<Instance> instances = new ArrayList<>();
        Instance[] instances1 = null;
        switch (missionType) {
            case IMAGE:
                instances.addAll(imageInstanceDao.findInstancesByMissionId(missionId));
                if (instances.size() == 0)
                    return null;
                instances1 = new Instance[instances.size()];
                for (int i = 0; i < instances1.length; i++) {
                    Instance instanceVo = instances.get(i);
                    instances1[i] = instanceVo;
                }
        }
        return instances1;
    }


}
