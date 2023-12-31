package com.epam.finaltask.library.model.service;

import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.entity.enums.UserStatus;
import com.epam.finaltask.library.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    Optional<User> findUser(String login, String password) throws ServiceException;

    Optional<User> findUserById(int id) throws ServiceException;

    Optional<User> findUserByEmail(String email) throws ServiceException;

    Optional<User> findUserByPassport(String passport) throws ServiceException;

    Optional<User> findUserByPassportOrEmail(String search_text) throws ServiceException;

    List<User> findUsers(UserRole userRole) throws ServiceException;

    boolean registerUser(Map<String, String> userData, UserRole userRole, UserStatus userStatus) throws ServiceException;

    boolean updateUserDetails(Map<String, String> userData) throws ServiceException;

    boolean isLoginOccupied(String login) throws ServiceException;

    boolean isEmailOccupied(String email) throws ServiceException;

    List<User> getWaitingStudents(int page) throws ServiceException;

    List<User> getAllStudents(int page) throws ServiceException;

    List<User> searchStudents(UserStatus userStatus, String search, int page) throws ServiceException;

    boolean changeUserStatus(int id, UserStatus userStatus) throws ServiceException;

    int countStudents() throws ServiceException;

    int countLibrarians() throws ServiceException;

}
