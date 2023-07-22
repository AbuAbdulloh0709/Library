package com.epam.finaltask.library.controller.command.impl.go_to;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.controller.command.SessionAttribute;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.UserService;
import com.epam.finaltask.library.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GoToProfileCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();
    @Override
    public Router execute(HttpServletRequest request) {
        int id = (int) request.getSession().getAttribute(SessionAttribute.USER_ID);
        try {
            User user = userService.findUserById(id).get();
            request.setAttribute(RequestAttribute.USER,user);
            return new Router(PagePath.PROFILE, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to profile page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
