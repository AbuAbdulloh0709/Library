package com.epam.finaltask.library.model.dao.mapper.impl;

import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.entity.enums.UserStatus;
import com.epam.finaltask.library.model.dao.mapper.BaseMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.finaltask.library.model.dao.ColumnName.*;

public class UserMapper implements BaseMapper<User> {

    private static final UserMapper instance = new UserMapper();

    private UserMapper() {

    }

    public static UserMapper getInstance() {
        return instance;
    }

    @Override
    public List<User> retrieve(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();

        while (resultSet.next()) {
            User user = new User();
            user.setId(resultSet.getInt(ID));
            user.setFirstName(resultSet.getString(USER_FIRST_NAME));
            user.setLastName(resultSet.getString(USER_LAST_NAME));
            user.setPassportNumber(resultSet.getString(USER_PASSPORT_NUMBER));
            user.setEmail(resultSet.getString(USER_EMAIL));
            user.setLogin(resultSet.getString(USER_LOGIN));
            user.setPassword(resultSet.getString(USER_PASSWORD));
            user.setRole(UserRole.valueOf(resultSet.getString(USER_ROLE).toUpperCase()));
            user.setAddress(resultSet.getString(USER_ADDRESS));
            user.setBirthDate(resultSet.getString(USER_BIRTH_DATE));
            user.setStatus(UserStatus.valueOf(resultSet.getString(USER_STATUS).toUpperCase()));
            user.setPhoneNumber(new BigInteger(resultSet.getString(USER_PHONE_NUMBER)));
            user.setCreatedAt(resultSet.getTimestamp(CREATED_AT));
            userList.add(user);
        }

        return userList;
    }
}
