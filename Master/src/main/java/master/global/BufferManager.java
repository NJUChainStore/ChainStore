package master.global;

import master.global.model.Buffer;
import master.global.model.L2Buffer;

public class BufferManager {
    public static volatile Buffer buffer = new Buffer();
    public static volatile L2Buffer l2Buffer = new L2Buffer();
}
