package com.epam.finaltask.library.model.service.impl;

import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.entity.enums.UserStatus;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.dao.DaoProvider;
import com.epam.finaltask.library.model.dao.Transaction;
import com.epam.finaltask.library.model.dao.UserDao;
import com.epam.finaltask.library.model.service.UserService;
import com.epam.finaltask.library.util.PasswordEncoder;
import com.epam.finaltask.library.util.PhoneNumberFormatter;
import com.epam.finaltask.library.validator.UserValidator;
import com.epam.finaltask.library.validator.impl.UserValidatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.finaltask.library.controller.command.RequestParameter.*;

public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final UserServiceImpl instance = new UserServiceImpl();
    private static final String INCORRECT_VALUE_PARAMETER = "incorrect";
    private static final String NUMBER_REMOVING_SYMBOLS_REGEX = "[+()-]";
    private static final String NUMBER_REPLACEMENT_REGEX = "";
    private static final String EMPTY_VALUE_PARAMETER = "";

    private UserServiceImpl() {
    }

    public static UserServiceImpl getInstance() {
        return instance;
    }


    @Override
    public Optional<User> findUser(String login, String password) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        UserValidator validator = UserValidatorImpl.getInstance();
        try {
            if (validator.checkLogin(login) && validator.checkPassword(password)) {
                Optional<User> user = userDao.findUserByLogin(login);
                Optional<String> userPassword = userDao.findUserPassword(login);
                Optional<String> encodedPassword = PasswordEncoder.encode(password);
                if (user.isPresent() && userPassword.isPresent() && encodedPassword.isPresent() &&
                        userPassword.get().equals(encodedPassword.get())) {
                    return user;
                }
            }
        } catch (DaoException e) {
            LOGGER.error("Error has occurred while searching for user with login \"{}\": {}", login, e);
            throw new ServiceException("Error has occurred while searching for user with login \"" + login + "\": ", e);
        } finally {
            userDao.closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String firstName, String lastName, UserRole userRole) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUser(String login) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public List<User> findUsers(UserRole userRole) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        try {
            return userDao.findUsersByRole(userRole);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding users: " + exception);
            throw new ServiceException("Error has occurred while finding users: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public Map<User, String> findUsers(UserRole userRole, int page) throws ServiceException {
        return null;
    }

    @Override
    public Map<User, String> findUsers(Map<String, String> userData, int page) throws ServiceException {
        return null;
    }

    @Override
    public boolean registerUser(Map<String, String> userData, UserRole userRole, UserStatus userStatus) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(true);
        UserValidator validator = UserValidatorImpl.getInstance();
        Transaction transaction = Transaction.getInstance();

        try {
            if (validator.checkUserData(userData)) {
                if (!userData.get(PASSWORD).equals(userData.get(REPEATED_PASSWORD))) {
                    userData.put(REPEATED_PASSWORD, INCORRECT_VALUE_PARAMETER);
                    return false;
                }
                Optional<String> password = PasswordEncoder.encode(userData.get(PASSWORD));
                if (password.isPresent()) {
                    transaction.begin(userDao);
                    BigInteger number = new BigInteger(
                            userData.get(PHONE_NUMBER).replaceAll(NUMBER_REMOVING_SYMBOLS_REGEX, NUMBER_REPLACEMENT_REGEX));
                    User user = new User();
                    user.setFirstName(userData.get(FIRSTNAME));
                    user.setLastName(userData.get(LASTNAME));
                    user.setPassportNumber(userData.get(PASSPORT_NUMBER));
                    user.setEmail(userData.get(EMAIL));
                    user.setRole(userRole);
                    user.setAddress(userData.get(ADDRESS));
                    user.setBirthDate(userData.get(BIRTH_DATE));
                    user.setStatus(userStatus);
                    user.setLogin(userData.get(LOGIN));
                    user.setPassword(userData.get(LOGIN));
                    user.setPhoneNumber(number);
                    userDao.add(user);
                    userDao.updateUserPassword(password.get(), userData.get(LOGIN));
                    transaction.commit();
                    return true;
                }
            }
            return false;
        } catch (DaoException exception) {
            try {
                transaction.rollback();
            } catch (DaoException daoException) {
                LOGGER.error("Error has occurred while doing transaction rollback for registering user: " + daoException);
            }
            LOGGER.error("Error has occurred while registering user: " + exception);
            throw new ServiceException("Error has occurred while registering user: ", exception);
        } finally {
            try {
                transaction.end();
            } catch (DaoException exception) {
                LOGGER.error("Error has occurred while ending transaction for registering user: " + exception);
            }
        }
    }

    @Override
    public boolean checkRoles(Map<String, String> userRoles) throws ServiceException {
        return false;
    }

    @Override
    public boolean isLoginOccupied(String login) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);

        try {
            Optional<User> user = userDao.findUserByLogin(login);
            return user.isPresent();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while checking login availability: " + exception);
            throw new ServiceException("Error has occurred while checking login availability: ", exception);
        } finally {
            userDao.closeConnection();
        }

    }

    @Override
    public boolean isEmailOccupied(String email) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        try {
            Optional<User> user = userDao.findUserByEmail(email);
            return user.isPresent();
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while checking email availability: " + exception);
            throw new ServiceException("Error has occurred while checking email availability: ", exception);
        } finally {
            userDao.closeConnection();
        }
    }

    @Override
    public List<User> getWaitingStudents(int page) throws ServiceException {
        DaoProvider daoProvider = DaoProvider.getInstance();
        UserDao userDao = daoProvider.getUserDao(false);
        int startElementNumber = page * 15 - 15;
        List<User> students;
        try {
            System.out.println(startElementNumber+" start");
            students = userDao.getUsersByRoleAndStatus(UserRole.STUDENT, UserStatus.INACTIVE, startElementNumber);
        } catch (DaoException exception) {
            LOGGER.error("Error has occurred while finding waiting students: " + exception);
            throw new ServiceException("Error has occurred while finding waiting students: ", exception);
        } finally {
            userDao.closeConnection();
        }
        return students;
    }
}
