package com.epam.finaltask.library.model.dao.impl;

import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.entity.enums.UserStatus;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.dao.UserDao;
import com.epam.finaltask.library.model.dao.mapper.impl.UserMapper;
import com.epam.finaltask.library.model.pool.ConnectionPool;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static com.epam.finaltask.library.model.dao.ColumnName.USER_PASSWORD;

public class UserDaoImpl extends UserDao {

    private static final String SQL_INSERT_USER = "INSERT INTO users(login, first_name, last_name, passport_number, email, role, address, birth_date, status, phone_number) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_USERS_BY_LOGIN =
            "SELECT * FROM users WHERE login = ?";

    private static final String SQL_SELECT_USERS_BY_EMAIL =
            "SELECT * FROM users WHERE email = ?";

    private static final String SQL_SELECT_USER_PASSWORD = "SELECT password FROM users WHERE login = ?";

    private static final String SQL_UPDATE_USER_PASSWORD = "UPDATE users SET password = ? WHERE login = ?";

    private static final String SQL_SELECT_ALL_USERS_BY_ROLE =
            "SELECT * from users WHERE role = ?";

    private static final String SQL_SELECT_USERS_BY_ROLE_AND_STATUS =
            "SELECT * from ( select * from users order by id) as u WHERE role = ? and status = ? LIMIT 15 OFFSET ?";


    private static final String SQL_SELECT_ALL_USERS_BY_ROLE_WITH_PAGING =
            "SELECT * from ( select * from users order by id) as u WHERE role = ? LIMIT 15 OFFSET ?";

    private static final String SQL_SEARCH_USERS_BY_ROLE_AND_STATUS_AND_SEARCH_QUERY =
            "SELECT *\n" +
                    "from ( select * from users ordreturn Optional.empty();er by id) as u \n" +
                    "WHERE role = ?\n" +
                    "  and status = ?\n" +
                    "  and (u.first_name like '%' || ? || '%' or u.last_name like '%' || ? || '%' or u.phone_number like '%' || ? || '%' or\n" +
                    "       u.passport_number like '%' || ? || '%')\n" +
                    "LIMIT 15 OFFSET ?";

    private static final String SQL_SEARCH_USERS_BY_ROLE_AND_SEARCH_QUERY =
            "SELECT *\n" +
                    "from ( select * from users order by id) as u \n" +
                    "WHERE role = ?\n" +
                    "  and (u.first_name like '%' || ? || '%' or u.last_name like '%' || ? || '%' or u.phone_number like '%' || ? || '%' or\n" +
                    "       u.passport_number like '%' || ? || '%')\n" +
                    "LIMIT 15 OFFSET ?";

    private static final String SQL_SEARCH_USERS_BY_ROLE_AND_STATUS =
            "SELECT *\n" +
                    "from ( select * from users order by id) as u\n" +
                    "WHERE role = ?\n" +
                    "  and status = ?\n" +
                    "LIMIT 15 OFFSET ?";

    private static final String SQL_CHANGE_USER_STATUS = "UPDATE users SET status = ? WHERE id = ?";

    public UserDaoImpl() {
    }

    public UserDaoImpl(boolean isTransaction) {
        if (!isTransaction) {
            connection = ConnectionPool.getInstance().getConnection();
        }
    }

    @Override
    public int add(User user) throws DaoException {
        try (PreparedStatement prepareStatement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, user.getLogin());
            prepareStatement.setString(2, user.getFirstName());
            prepareStatement.setString(3, user.getLastName());
            prepareStatement.setString(4, user.getPassportNumber());
            prepareStatement.setString(5, user.getEmail());
            prepareStatement.setString(6, user.getRole().getRole());
            prepareStatement.setString(7, user.getAddress());
            prepareStatement.setString(8, user.getBirthDate());
            prepareStatement.setString(9, user.getStatus().getStatus());
            prepareStatement.setString(10, user.getPhoneNumber().toString());
            prepareStatement.execute();
            ResultSet resultSet = prepareStatement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while adding user: " + sqlException);
            throw new DaoException("Error has occurred while adding user: ", sqlException);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer integer) throws DaoException {
        return false;
    }

    @Override
    public List<User> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<User> findById(Integer integer) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding user by login: " + exception);
            throw new DaoException("Error has occurred while finding user by login: ", exception);
        }
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> findUserByPassword(String password) throws DaoException {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_EMAIL)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                UserMapper userMapper = UserMapper.getInstance();
                users = userMapper.retrieve(resultSet);
            }
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while finding user by email: " + exception);
            throw new DaoException("Error has occurred while finding user by email: ", exception);
        }
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public List<User> findUsersByStatus(User user, int startElementNumber) throws DaoException {
        return null;
    }

    @Override
    public List<User> findUsersByRole(UserRole userRole) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USERS_BY_ROLE)) {
            preparedStatement.setString(1, userRole.getRole());
            ResultSet resultSet = preparedStatement.executeQuery();
            UserMapper userMapper = UserMapper.getInstance();
            users = userMapper.retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding users by role: " + sqlException);
            throw new DaoException("Error has occurred while finding users by role: ", sqlException);
        }
        return users;
    }

    @Override
    public List<User> findUsersByRole(UserRole userRole, int startElementNumber) throws DaoException {
        return null;
    }

    @Override
    public Optional<String> findUserPassword(String login) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USER_PASSWORD)) {
            preparedStatement.setString(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getString(USER_PASSWORD));
            }
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding user password: " + sqlException);
            throw new DaoException("Error has occurred while finding user password: ", sqlException);
        }
        return Optional.empty();
    }

    @Override
    public boolean updateUserPassword(String password, String login) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, login);
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating user password: " + exception);
            throw new DaoException("Error has occurred while updating user password: ", exception);
        }
    }

    @Override
    public List<User> getUsersByRoleAndStatus(UserRole role, UserStatus status, int startElementNumber) throws DaoException {
        List<User> users;
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_ROLE_AND_STATUS)) {
            preparedStatement.setString(1, role.getRole());
            preparedStatement.setString(2, status.getStatus());
            preparedStatement.setInt(3, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            users = UserMapper.getInstance().retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding users by role and status: " + sqlException);
            throw new DaoException("Error has occurred while finding users by role and status: ", sqlException);
        }
        return users;
    }


    @Override
    public List<User> getUsersByRole(UserRole role, int startElementNumber) throws DaoException {
        List<User> users;
//        ConnectionPool connectionPool = ConnectionPool.getInstance();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_USERS_BY_ROLE_WITH_PAGING)) {
            preparedStatement.setString(1, role.getRole());
            preparedStatement.setInt(2, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            users = UserMapper.getInstance().retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding users by role: " + sqlException);
            throw new DaoException("Error has occurred while finding users by role: ", sqlException);
        }
        return users;
    }

    @Override
    public List<User> searchUsersByRoleAndStatusAndQuery(UserRole role, UserStatus userStatus, String search, int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SEARCH_USERS_BY_ROLE_AND_STATUS_AND_SEARCH_QUERY)) {
            preparedStatement.setString(1, role.getRole());
            preparedStatement.setString(2, userStatus.getStatus());
            preparedStatement.setString(3, search);
            preparedStatement.setString(4, search);
            preparedStatement.setString(5, search);
            preparedStatement.setString(6, search);
            preparedStatement.setInt(7, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            users = UserMapper.getInstance().retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding users by role, status and search query: " + sqlException);
            throw new DaoException("Error has occurred while finding users by role, status and search query: ", sqlException);
        }
        return users;
    }

    @Override
    public List<User> searchUsersByRoleAndQuery(UserRole role, String search, int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SEARCH_USERS_BY_ROLE_AND_SEARCH_QUERY)) {
            preparedStatement.setString(1, role.getRole());
            preparedStatement.setString(2, search);
            preparedStatement.setString(3, search);
            preparedStatement.setString(4, search);
            preparedStatement.setString(5, search);
            preparedStatement.setInt(6, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            users = UserMapper.getInstance().retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding users by role and search query: " + sqlException);
            throw new DaoException("Error has occurred while finding users by role and search query: ", sqlException);
        }
        return users;
    }

    @Override
    public List<User> searchUsersByRoleAndStatus(UserRole role, UserStatus userStatus,int startElementNumber) throws DaoException {
        List<User> users;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_USERS_BY_ROLE_AND_STATUS)) {
            preparedStatement.setString(1, role.getRole());
            preparedStatement.setString(2, userStatus.getStatus());
            preparedStatement.setInt(3, startElementNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            users = UserMapper.getInstance().retrieve(resultSet);
        } catch (SQLException sqlException) {
            LOGGER.error("Error has occurred while finding users by role and status: " + sqlException);
            throw new DaoException("Error has occurred while finding users by role and status: ", sqlException);
        }
        return users;
    }

    @Override
    public boolean changeUserStatus(int id, UserStatus status) throws DaoException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_USER_STATUS)) {
            preparedStatement.setString(1, status.getStatus());
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
            return true;
        } catch (SQLException exception) {
            LOGGER.error("Error has occurred while updating user status: " + exception);
            throw new DaoException("Error has occurred while updating user status: ", exception);
        }
    }


}
