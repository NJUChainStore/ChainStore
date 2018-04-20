package webservice.util;

import fintech100k.WebService.entity.account.User;
import trapx00.tagx00.entity.account.TempUser;
import trapx00.tagx00.entity.mission.instance.Instance;
import trapx00.tagx00.vo.mission.instance.InstanceDetailVo;
import trapx00.tagx00.vo.user.UserSaveVo;
import trapx00.tagx00.vo.user.info.RequesterInfoVo;
import trapx00.tagx00.vo.user.info.WorkerInfoVo;

public class Converter {
    /**
     * convert userSaveVo to tempUser
     *
     * @param userSaveVo the userSaveVo
     * @param code       the validation code
     * @return the user
     */
    public static TempUser userSaveVoToTempUser(UserSaveVo userSaveVo, String code) {
        return new TempUser(userSaveVo.getUsername(), userSaveVo.getPassword(), userSaveVo.getEmail(), userSaveVo.getRoles(), code);
    }

    public static User tempUserToUser(TempUser tempUser) {
        return new User(tempUser.getUsername(), tempUser.getPassword(), tempUser.getEmail(), tempUser.getRoles(), 0, 0);
    }

    public static RequesterInfoVo userToRequesterInfoVo(User user, int submittedMissionCount,
                                                        int instanceCount, int awaitingCommentInstanceCount,
                                                        int inProgressInstanceCount, int finalizedInstanceCount) {
        return new RequesterInfoVo(user.getUsername(), user.getEmail(), submittedMissionCount,
                instanceCount, awaitingCommentInstanceCount, inProgressInstanceCount,
                finalizedInstanceCount);
    }

    public static WorkerInfoVo userToWorkerInfoVo(User user, int completedMissionCount, int acceptedMissionCount, int inProgressMissionCount, int abandonedMissionCount) {
        return new WorkerInfoVo(user.getUsername(), user.getEmail(), user.getCredits(), user.getExp(), LevelUtil.caculateLevel(user.getExp()),
                completedMissionCount, acceptedMissionCount, inProgressMissionCount, abandonedMissionCount);
    }

    public static InstanceDetailVo instanceToInstanceDetailResponse(Instance instance) {
        return new InstanceDetailVo();
    }
}
