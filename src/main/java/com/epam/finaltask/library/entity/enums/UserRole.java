package com.epam.finaltask.library.entity.enums;

public enum UserRole {
    /**
     * Admin user role.
     */
    ADMIN,
    /**
     * Teacher user role.
     */
    LIBRARIAN,
    /**
     * User user role.
     */
    STUDENT,
    /**
     * Guest user role.
     */
    GUEST;

    /**
     * Get role string.
     *
     * @return the string
     */
    public String getRole(){
        return this.toString().toLowerCase();
    }
}
