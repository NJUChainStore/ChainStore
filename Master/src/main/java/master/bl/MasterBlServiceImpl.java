package master.bl;

import master.blservice.MasterBlService;
import master.global.entity.*;
import master.response.ReceiveCompleteReceivedResponse;
import master.response.RegisterResponse;
import master.response.SendCompleteReceivedResponse;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class MasterBlServiceImpl implements MasterBlService {
    private static Table table = new Table();

    /**
     * register a machine
     *
     * @param role
     * @return
     */
    @Override
    public RegisterResponse register(Role role, String ip) {
        // 生成一个accessToken，在自己的表里增加一项为这个accessToken对应一台机器，其角色由PathVariable来决定
        // 机器向主机请求时，需要带上accessToken，主机来检查这是哪一台机器
        // 再生成一个masterToken，并发给注册者，注册者通过masterToken判断一次请求是否由主机发出
        String accessToken = UUID.randomUUID().toString(); // 生成accessToken;
        String masterToken = UUID.randomUUID().toString();
        System.out.println(String.format("Register from %s accepted. Generated accessToken: %s, masterToken: %s", role, accessToken, masterToken));

        if (role.equals(Role.MINER)) {
            table.setMiner(new MinerItem(System.currentTimeMillis(), accessToken, masterToken));
        } else {
            table.getDatabases().add(new DatabaseItem(System.currentTimeMillis(), masterToken, accessToken, DatabaseState.Available, new Date(), ip));
        }
        return new RegisterResponse(accessToken, masterToken);
    }

    /**
     * send complete sign and set its state to free
     *
     * @param id
     * @return
     */
    @Override
    public SendCompleteReceivedResponse sendComplete(String id) {
        return new SendCompleteReceivedResponse(setDatabaseState(id, DatabaseState.Available));
    }

    /**
     * receive complete sign and set its state to free
     *
     * @param id
     * @return
     */
    @Override
    public ReceiveCompleteReceivedResponse receiveComplete(String id) {
        return new ReceiveCompleteReceivedResponse(setDatabaseState(id, DatabaseState.Available));
    }

    private String setDatabaseState(String id, DatabaseState databaseState) {
        int index = 0;
        String masterToken = "";
        List<DatabaseItem> databaseItems = table.getDatabases();
        for (DatabaseItem databaseItem : databaseItems) {
            if (databaseItem.getAccessToken().equals(id)) {
                databaseItem.setState(databaseState);
                databaseItems.set(index, databaseItem);
                masterToken = databaseItem.getMasterToken();
                break;
            }
            index++;
        }
        table.setDatabases(databaseItems);
        return masterToken;
    }
}
