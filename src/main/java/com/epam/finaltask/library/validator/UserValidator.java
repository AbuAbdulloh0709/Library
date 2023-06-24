package com.epam.finaltask.library.validator;

import java.util.Map;

public interface UserValidator {
    boolean checkLogin(String login);

    boolean checkPassword(String password);

    boolean checkFirstName(String firstName);

    boolean checkLastName(String lastName);

    boolean checkEmail(String email);

    boolean checkNumber(String number);

    boolean checkPassport(String passport);

    boolean checkUserData(Map<String, String> userData);

    boolean checkUserPersonalData(Map<String, String> userData);

}
