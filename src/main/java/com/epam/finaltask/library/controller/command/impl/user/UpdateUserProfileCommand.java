package com.epam.finaltask.library.controller.command.impl.user;

import com.epam.finaltask.library.controller.command.*;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserRole;
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
import static com.epam.finaltask.library.controller.command.RequestParameter.ID;
import static com.epam.finaltask.library.controller.command.impl.RequestAttribute.*;

public class UpdateUserProfileCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String PROFILE_UPDATED_MESSAGE_KEY = "profile.update";
    private static final String ERROR_UPDATE_MESSAGE_KEY = "error.sign_up";
    private static final String EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY = "error.email_availability";
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        Map<String, String> userData = new HashMap<>();
        userData.put(ID, String.valueOf(user.getId()));
        userData.put(EMAIL, request.getParameter(EMAIL));
        userData.put(FIRSTNAME, request.getParameter(FIRSTNAME));
        userData.put(LASTNAME, request.getParameter(LASTNAME));
        userData.put(BIRTH_DATE, request.getParameter(BIRTH_DATE));
        userData.put(PHONE_NUMBER, request.getParameter(PHONE_NUMBER));
        userData.put(PASSPORT_NUMBER, request.getParameter(PASSPORT_NUMBER));
        userData.put(ADDRESS, request.getParameter(ADDRESS));

        try {
            if (userService.isEmailOccupied(userData.get(EMAIL)) && !user.getEmail().equals(userData.get(EMAIL))) {
                request.setAttribute(MESSAGE, EMAIL_AVAILABILITY_ERROR_MESSAGE_KEY);
                request.setAttribute(USER, userService.findUserById(user.getId()).get());
                return new Router(PagePath.PROFILE, Router.RouterType.FORWARD);
            }
            if (userService.updateUserDetails(userData)) {
                session.setAttribute(SessionAttribute.USER, userService.findUserById(user.getId()).get());
                session.setAttribute(SessionAttribute.NUMBER, userData.get(PHONE_NUMBER));
                request.setAttribute(MESSAGE, PROFILE_UPDATED_MESSAGE_KEY);
                return new Router(PagePath.DASHBOARD, Router.RouterType.FORWARD);
            } else {
                request.setAttribute(USER, userService.findUserById(user.getId()).get());
                request.setAttribute(MESSAGE, ERROR_UPDATE_MESSAGE_KEY);
                return new Router(PagePath.PROFILE, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while updating user details: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
