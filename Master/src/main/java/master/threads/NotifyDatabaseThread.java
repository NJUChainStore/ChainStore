package master.threads;

import master.bl.MasterRequestBlServiceImpl;
import master.blservice.MasterRequestBlService;
import master.global.TableManager;
import master.global.entity.DatabaseItem;
import master.global.entity.DatabaseState;

import java.util.Date;
import java.util.List;

/**
 * 通知存储结机自查
 */
public class NotifyDatabaseThread implements Runnable {
    private final static int FIND_LOOP = 1000 * 60;
    private final static int VALIDATE_LOOP = 1000 * 30;

    private MasterRequestBlService masterRequestBlService = (MasterRequestBlService) new MasterRequestBlServiceImpl();

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(FIND_LOOP);
                Date nowTime = new Date();
                List<DatabaseItem> databaseItemList = TableManager.table.getDatabases();
                for (int i = 0; i < databaseItemList.size(); i++) {
                    DatabaseItem databaseItem = databaseItemList.get(i);
                    if (nowTime.getTime() - databaseItem.getLastValidateTime().getTime() > VALIDATE_LOOP) {
                        databaseItem.setLastValidateTime(nowTime);
                        databaseItem.setState(DatabaseState.Validating);
                        databaseItemList.set(i, databaseItem);
                        masterRequestBlService.sendValidateRequest(databaseItemList.get(i));
                    }
                }
                TableManager.table.setDatabases(databaseItemList);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.start();
    }
}

