package com.epam.finaltask.library.controller.command.impl.signing;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.controller.command.SessionAttribute;
import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.UserService;
import com.epam.finaltask.library.model.service.impl.UserServiceImpl;
import com.epam.finaltask.library.util.PhoneNumberFormatter;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static com.epam.finaltask.library.controller.command.RequestParameter.*;
import static com.epam.finaltask.library.controller.command.impl.RequestAttribute.*;

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final String SIGN_IN_ERROR_MESSAGE_KEY = "error.sign_in";
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);

        try {
            Optional<User> user = userService.findUser(login, password);
            if (user.isPresent()) {
                ServletContext servletContext = request.getServletContext();
                servletContext.setAttribute(login, user.get().getStatus());
                String number = PhoneNumberFormatter.format(user.get().getPhoneNumber());
                session.setAttribute(SessionAttribute.NUMBER, number);
                session.setAttribute(SessionAttribute.USER, user.get());
                session.setAttribute(SessionAttribute.ROLE, user.get().getRole().getRole());
                if (user.get().getRole().equals(UserRole.ADMIN))
                    return new Router(PagePath.ADMIN, Router.RouterType.REDIRECT);
                else
                    return new Router(PagePath.HOME, Router.RouterType.REDIRECT);
            } else {
                request.setAttribute(USER_LOGIN, login);
                request.setAttribute(USER_PASSWORD, password);
                request.setAttribute(MESSAGE, SIGN_IN_ERROR_MESSAGE_KEY);
                return new Router(PagePath.SIGN_IN, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while signing in: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
