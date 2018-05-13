package master.global.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class L2Buffer {
    private List<String> infos = Collections.synchronizedList(new ArrayList<>());

    public void clear() {
        infos = new ArrayList<>();
    }

    public List<String> getInfos() {
        return infos;
    }

    public void setInfos(List<String> infos) {
        this.infos = infos;
    }
}
