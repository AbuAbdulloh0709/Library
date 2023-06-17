package com.epam.finaltask.library.entity;

import java.sql.Timestamp;

public abstract class AbstractEntity {
    private int id;
    private Timestamp createdAt;

    protected AbstractEntity() {

    }

    protected AbstractEntity(int id, Timestamp createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AbstractEntity abstractEntity = (AbstractEntity) o;
        return id == abstractEntity.id;
    }

    @Override
    public int hashCode() {
        int result = 1;
        return result * 31 + Long.hashCode(id);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("id=id").append("}");
        return sb.toString();
    }
}
