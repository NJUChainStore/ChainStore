package master.global.model;

import java.util.ArrayList;

public class Buffer {
    private ArrayList<String> infos = new ArrayList<>();

    public void add(String info) {
        infos.add(info);
    }

    public void clear() {
        infos = new ArrayList<>();
    }

    public ArrayList<String> getInfos() {
        return infos;
    }
}
