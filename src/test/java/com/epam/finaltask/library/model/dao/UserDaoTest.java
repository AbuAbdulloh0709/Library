package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.model.dao.impl.UserDaoImpl;
import com.epam.finaltask.library.model.mapper.impl.UserMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.sql.*;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

public class UserDaoTest {
    private MockedStatic<UserMapper> userMapper;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private Statement statement;

    @Mock
    private ResultSet resultSet;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserDaoImpl userDao;

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
        userMapper = mockStatic(UserMapper.class);
    }

    @Test
    public void add() throws SQLException, DaoException {
        when(connection.prepareStatement(anyString(), anyInt())).thenReturn(preparedStatement);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);

        User user = new User();
        user.setRole(UserRole.STUDENT);
        long expected = 0;
        long actual = userDao.add(user);
        Assert.assertEquals(actual, expected);
    }

}
