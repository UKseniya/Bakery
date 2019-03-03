package kz.epam.entities;

import java.io.Serializable;

public abstract class Entity implements Serializable {
//TODO: equals & hash for all entity ?
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
