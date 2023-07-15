package com.epam.finaltask.library.entity.enums;

public enum OrderType {
    /**
     * If a book is given to read in room.
     */
    IN_ROOM,
    /**
     * If a book is given to borrow.
     */
    BORROW;
    public String getType(){
        return this.toString().toLowerCase();
    }

    public String getOrderType(){
        if (this.getType().equals("in_room")){
            return "In Room";
        } else {
            return "Borrow";
        }
    }
}
