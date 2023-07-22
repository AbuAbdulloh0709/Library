package com.epam.finaltask.library.validator.impl;

import com.epam.finaltask.library.validator.UserValidator;

import java.util.Map;

import static com.epam.finaltask.library.controller.command.RequestParameter.*;

public class UserValidatorImpl implements UserValidator {
    private static final String INCORRECT_VALUE_PARAMETER = " incorrect";
    private static final String LOGIN_REGEX = "[\\p{Alpha}][\\p{Alpha}\\d]{4,29}";
    private static final String PASSWORD_REGEX = "[\\p{Alpha}][\\p{Alpha}\\d]{7,29}";
    private static final String LASTNAME_REGEX = "[A-Z\\p{Upper}][a-z\\p{Lower}]{1,20}";
    private static final String FIRSTNAME_REGEX = "[A-Z\\p{Upper}][a-z\\p{Lower}]{1,15}";
    private static final String PASSPORT_REGEX = "^[A-Z]{2}\\d{7}";
    private static final String EMAIL_REGEX = "(([\\p{Alpha}\\d._]+){5,25}@([\\p{Lower}]+){3,7}\\.([\\p{Lower}]+){2,3})";
    private static final String NUMBER_REGEX = "\\+998\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}";
//    private static final String NUMBER_REGEX = "\\+998\\d{9}";
    private static final UserValidator instance = new UserValidatorImpl();

    private UserValidatorImpl() {
    }

    public static UserValidator getInstance() {
        return instance;
    }

    @Override
    public boolean checkLogin(String login) {
        return login != null && login.matches(LOGIN_REGEX);
    }

    @Override
    public boolean checkPassword(String password) {
        return password != null && password.matches(PASSWORD_REGEX);
    }

    @Override
    public boolean checkFirstName(String firstName) {
        return firstName != null && firstName.matches(FIRSTNAME_REGEX);
    }

    @Override
    public boolean checkLastName(String lastName) {
        return lastName != null && lastName.matches(LASTNAME_REGEX);
    }

    @Override
    public boolean checkEmail(String email) {
        return email != null && email.matches(EMAIL_REGEX);
    }

    @Override
    public boolean checkNumber(String number) {
        return number != null && number.matches(NUMBER_REGEX);
    }

    @Override
    public boolean checkPassport(String passport) {
        return passport != null && passport.matches(PASSPORT_REGEX);
    }

    @Override
    public boolean checkUserData(Map<String, String> userData) {
        boolean isValid = true;
        if (!checkLogin(userData.get(LOGIN))) {
            userData.put(LOGIN, userData.get(LOGIN) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkPassword(userData.get(PASSWORD))) {
            userData.put(PASSWORD, userData.get(PASSWORD) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }

        isValid = isValid && checkUserPersonalData(userData);

        return isValid;
    }

    @Override
    public boolean checkUserPersonalData(Map<String, String> userData) {
        boolean isValid = true;
        if (!checkFirstName(userData.get(FIRSTNAME))) {
            userData.put(FIRSTNAME, userData.get(FIRSTNAME) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkLastName(userData.get(LASTNAME))) {
            userData.put(LASTNAME, userData.get(LASTNAME) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkEmail(userData.get(EMAIL))) {
            userData.put(EMAIL, userData.get(EMAIL) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkNumber(userData.get(PHONE_NUMBER))) {
            userData.put(PHONE_NUMBER, userData.get(PHONE_NUMBER) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        if (!checkPassport(userData.get(PASSPORT_NUMBER))) {
            userData.put(PASSPORT_NUMBER, userData.get(PASSPORT_NUMBER) + INCORRECT_VALUE_PARAMETER);
            isValid = false;
        }
        return isValid;
    }
}
