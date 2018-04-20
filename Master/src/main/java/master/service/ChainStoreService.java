package master.service;

import java.io.Serializable;

public interface ChainStoreService<T extends Serializable> {
    void addData(String username, String projectName, T data);

    void deleteData(String id);

    void updateData(String id, T data);

    void findData(String id);
}
