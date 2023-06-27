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

    Optional<User> findUser(String firstName, String lastName, UserRole userRole) throws ServiceException;

    Optional<User> findUser(String login) throws ServiceException;

    List<User> findUsers(UserRole userRole) throws ServiceException;

    Map<User, String> findUsers(UserRole userRole, int page) throws ServiceException;

    Map<User, String> findUsers(Map<String, String> userData, int page) throws ServiceException;

    boolean registerUser(Map<String, String> userData, UserRole userRole, UserStatus userStatus) throws ServiceException;

    boolean checkRoles(Map<String, String> userRoles) throws ServiceException;

    boolean isLoginOccupied(String login) throws ServiceException;

    boolean isEmailOccupied(String email) throws ServiceException;

    List<User> getWaitingStudents(int page) throws ServiceException;


}
