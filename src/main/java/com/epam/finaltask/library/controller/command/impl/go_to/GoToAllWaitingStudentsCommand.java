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
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class GoToAllWaitingStudentsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_PAGE = 1;
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        int page;

        System.out.println(request.getParameter(RequestParameter.PAGE));

        if (request.getParameter(RequestParameter.PAGE) == null) {
            page = DEFAULT_PAGE;
        } else {
            page = Integer.parseInt(request.getParameter(RequestParameter.PAGE));
        }
        System.out.println(page);
        try {
            List<User> students = userService.getWaitingStudents(page);
            List<User> nextStudents = userService.getWaitingStudents(page + 1);
            request.setAttribute(RequestAttribute.WAITING_STUDENTS, students);
            request.setAttribute(RequestAttribute.PAGE, page);
            request.setAttribute(RequestAttribute.LAST, nextStudents.isEmpty());
            return new Router(PagePath.ALL_WAITING_STUDENTS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to waiting students page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
