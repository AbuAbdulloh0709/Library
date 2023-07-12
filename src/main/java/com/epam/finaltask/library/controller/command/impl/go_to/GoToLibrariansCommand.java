package com.epam.finaltask.library.controller.command.impl.go_to;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.UserService;
import com.epam.finaltask.library.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.epam.finaltask.library.controller.command.impl.RequestAttribute.LIBRARIANS;

public class GoToLibrariansCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            List<User> librarians = userService.findUsers(UserRole.LIBRARIAN);
            request.setAttribute(LIBRARIANS, librarians);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to librarians page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }


        return new Router(PagePath.LIBRARIANS, Router.RouterType.FORWARD);
    }
}
