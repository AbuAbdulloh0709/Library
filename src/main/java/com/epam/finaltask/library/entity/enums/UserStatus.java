package com.epam.finaltask.library.entity.enums;

public enum UserStatus {
    /**
     * Active user status.
     */
    ACTIVE,
    /**
     * Active user status.
     * when admin or librarian approve new student, status will be active
     * and students can order books
     */
    INACTIVE,
    /**
     * Blocked user status.
     */
    BLOCKED;

    /**
     * Get status string.
     *
     * @return the string
     */
    public String getStatus() {
        return this.toString().toLowerCase();
    }
}
