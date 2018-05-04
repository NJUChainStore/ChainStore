package master.threads;

public class ThreadStarter {
    public void runMasterThreads() {
        new NotifyDatabaseThread().start();
    }

}
