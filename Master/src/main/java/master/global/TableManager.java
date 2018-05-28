package master.global;

import master.global.entity.Table;

public class TableManager {
    public static volatile Table table = new Table();
    public static String localUrl;
}
