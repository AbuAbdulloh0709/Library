package com.epam.finaltask.library.controller.command.impl.go_to;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.RequestParameter;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.UserService;
import com.epam.finaltask.library.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GoToAllStudentsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_PAGE = 1;
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        int page;

        if (request.getParameter(RequestParameter.PAGE) == null) {
            page = DEFAULT_PAGE;
        } else {
            page = Integer.parseInt(request.getParameter(RequestParameter.PAGE));
        }
        try {
            List<User> students = userService.getAllStudents(page);
            List<User> nextStudents = userService.getAllStudents(page + 1);
            request.setAttribute(RequestAttribute.ALL_STUDENTS, students);
            request.setAttribute(RequestAttribute.PAGE, page);
            request.setAttribute(RequestAttribute.LAST, nextStudents.isEmpty());
            return new Router(PagePath.ALL_STUDENTS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to all students page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
