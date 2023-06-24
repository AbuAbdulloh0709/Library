package com.epam.finaltask.library.model.service;

import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.exception.DaoException;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.dao.DaoProvider;
import com.epam.finaltask.library.model.dao.Transaction;
import com.epam.finaltask.library.model.dao.UserDao;
import com.epam.finaltask.library.model.service.impl.UserServiceImpl;
import com.epam.finaltask.library.util.PasswordEncoder;
import com.epam.finaltask.library.util.PhoneNumberFormatter;
import com.epam.finaltask.library.validator.impl.UserValidatorImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static com.epam.finaltask.library.controller.command.RequestParameter.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

public class UserServiceTest {
    private MockedStatic<DaoProvider> daoProviderHolder;
    private MockedStatic<UserValidatorImpl> userValidator;
    private MockedStatic<Transaction> transactionMocked;
    private MockedStatic<PasswordEncoder> passwordEncoder;
    private MockedStatic<PhoneNumberFormatter> phoneNumberFormatter;

    @Mock
    private DaoProvider daoProvider;

    @Mock
    private Transaction transaction;

    @Mock
    private UserDao userDao;

    @Mock
    private UserValidatorImpl validator;

    @InjectMocks
    private UserServiceImpl userService = UserServiceImpl.getInstance();

    @BeforeClass
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeTest
    public void initStatic() {
        daoProviderHolder = mockStatic(DaoProvider.class);
        userValidator = mockStatic(UserValidatorImpl.class);
        transactionMocked = mockStatic(Transaction.class);
        passwordEncoder = mockStatic(PasswordEncoder.class);
        phoneNumberFormatter = mockStatic(PhoneNumberFormatter.class);
    }

    @Test(dataProvider = "userData")
    public void registerUser(Map<String, String> userData) throws DaoException, ServiceException {
        daoProviderHolder.when(DaoProvider::getInstance).thenReturn(daoProvider);
        userValidator.when(UserValidatorImpl::getInstance).thenReturn(validator);
        transactionMocked.when(Transaction::getInstance).thenReturn(transaction);
        when(daoProvider.getUserDao(true)).thenReturn(userDao);

        when(validator.checkUserData(anyMap())).thenReturn(true);
        when(userDao.add(any(User.class))).thenReturn(Integer.valueOf(1));
        when(userDao.updateUserPassword(anyString(), anyString())).thenReturn(true);
        doNothing().when(transaction).begin(userDao);
        doNothing().when(transaction).commit();
        doNothing().when(transaction).end();

        boolean actual = userService.registerUser(userData);
        Assert.assertFalse(actual);
    }

    @DataProvider(name = "userData")
    public Object[][] getUserData(){
        return new Object[][]{
                {
                   new HashMap<>(Map.of(LOGIN,"abuabdulloh0709",PASSWORD,"password",REPEATED_PASSWORD,"password",
                           FIRSTNAME,"Muslimbek",LASTNAME,"Soliyev",EMAIL,"AbuAbdulloh0709@gmail.com",BIRTH_DATE,"2000-07-09",
                           PHONE_NUMBER,"+998916541977"))
                }
        };
    }
}
