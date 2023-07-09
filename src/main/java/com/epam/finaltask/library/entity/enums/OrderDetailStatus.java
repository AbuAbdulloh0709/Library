package com.epam.finaltask.library.entity.enums;

public enum OrderDetailStatus {
    CREATED,
    CANCELED,
    ACCEPTED,
    RETURNED;

    public String getStatus(){
       return this.toString().toLowerCase();
    }
}
