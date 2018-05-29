package master.parameters;

import master.global.entity.Table;

public class UpdateSelfParameters {
    private Table table;

    public UpdateSelfParameters() {
    }

    public UpdateSelfParameters(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
}
