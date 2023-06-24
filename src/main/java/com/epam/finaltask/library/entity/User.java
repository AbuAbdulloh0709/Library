package com.epam.finaltask.library.entity;

import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.entity.enums.UserStatus;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;

public class User extends AbstractEntity {
    private String firstName;
    private String lastName;
    private String passportNumber;
    private String email;
    private String login;
    private String password;
    private UserRole role;
    private String address;
    private String birthDate;
    private UserStatus status;
    private BigInteger phoneNumber;

    public User() {
    }

    public User(int id, Timestamp createdAt, String firstName, String lastName, String passportNumber, String email, String login, String password, UserRole role, String address, String birthDate, UserStatus status) {
        super(id, createdAt);
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportNumber = passportNumber;
        this.email = email;
        this.login = login;
        this.password = password;
        this.role = role;
        this.address = address;
        this.birthDate = birthDate;
        this.status = status;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void setPhoneNumber(BigInteger phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public BigInteger getPhoneNumber() {
        return phoneNumber;
    }
}
