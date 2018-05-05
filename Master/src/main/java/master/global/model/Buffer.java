package master.global.model;

import java.util.ArrayList;

public class Buffer {
    private static final int MAX_NUMBER = 10000;
    private ArrayList<String> infos = new ArrayList<>();

    public void add(String info) {
        infos.add(info);
    }

    public boolean isFull() {
        return infos.size() >= MAX_NUMBER;
    }

    public void clear() {
        infos = new ArrayList<>();
    }

    public ArrayList<String> getInfos() {
        return infos;
    }

    public long getNowOffset() {
        return infos.size() % MAX_NUMBER;
    }

    public String getInfo(int index) {
        return infos.get(index);
    }
}
