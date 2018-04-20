package webservice.entity.account;

import trapx00.tagx00.entity.Entity;
import trapx00.tagx00.entity.annotation.Column;

public class Role extends Entity {
    public final static String WORKER_NAME = "ROLE_WORKER";
    public final static String REQUESTER_NAME = "ROLE_REQUESTER";
    public final static String ADMIN_NAME = "ROLE_ADMIN";
    public final static Role WORKER = new Role(WORKER_NAME);
    public final static Role REQUESTER = new Role(REQUESTER_NAME);
    public final static Role ADMIN = new Role(ADMIN_NAME);


    @Column(name = "name")
    private String name;

    public Role() {

    }

    public Role(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
