package master.global.model;

import java.util.ArrayList;

public class L2Buffer {
    private ArrayList<String> infos = new ArrayList<>();

    public void clear() {
        infos = new ArrayList<>();
    }

    public ArrayList<String> getInfos() {
        return infos;
    }

    public void setInfos(ArrayList<String> infos) {
        this.infos = infos;
    }
}
