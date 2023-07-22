package com.epam.finaltask.library.controller.command.impl.signing;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.entity.enums.UserStatus;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.UserService;
import com.epam.finaltask.library.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.epam.finaltask.library.controller.command.RequestParameter.*;
import static com.epam.finaltask.library.controller.command.impl.RequestAttribute.MESSAGE;
import static com.epam.finaltask.library.controller.command.impl.RequestAttribute.USER;

public class SignUpCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SIGN_UP_CONFIRM_MESSAGE_KEY = "confirm.sign_up";
    private static final String SIGN_UP_ERROR_MESSAGE_KEY = "error.sign_up";
    private static final String LOGIN_AVAILABILITY_ERROR_MESSAGE_KEY = "error.login_availability";
    private static final String EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY = "error.email_availability";
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        Map<String, String> userData = new HashMap<>();
        userData.put(LOGIN, request.getParameter(LOGIN));
        userData.put(EMAIL, request.getParameter(EMAIL));
        userData.put(PASSWORD, request.getParameter(PASSWORD));
        userData.put(REPEATED_PASSWORD, request.getParameter(REPEATED_PASSWORD));
        userData.put(FIRSTNAME, request.getParameter(FIRSTNAME));
        userData.put(LASTNAME, request.getParameter(LASTNAME));
        userData.put(BIRTH_DATE, request.getParameter(BIRTH_DATE));
        userData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        userData.put(PASSPORT_NUMBER, request.getParameter(PASSPORT_NUMBER));
        userData.put(ADDRESS, request.getParameter(ADDRESS));

        try {
            if (userService.isEmailOccupied(userData.get(EMAIL))) {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_UP, Router.RouterType.FORWARD);
            }
            if (userService.isLoginOccupied(userData.get(LOGIN))) {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, LOGIN_AVAILABILITY_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_UP, Router.RouterType.FORWARD);
            }
            if (userService.registerUser(userData, UserRole.STUDENT, UserStatus.INACTIVE)) {
                request.setAttribute(MESSAGE, SIGN_UP_CONFIRM_MESSAGE_KEY);
                return new Router(PagePath.HOME, Router.RouterType.FORWARD);
            } else {
                request.setAttribute(USER, userData);
                request.setAttribute(MESSAGE, SIGN_UP_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_UP, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while signing in: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
