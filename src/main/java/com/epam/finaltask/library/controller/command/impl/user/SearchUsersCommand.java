package com.epam.finaltask.library.controller.command.impl.user;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.RequestParameter;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.UserStatus;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.UserService;
import com.epam.finaltask.library.model.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SearchUsersCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_PAGE = 1;
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        int page;
        String status = request.getParameter(RequestParameter.STATUS);
        String searchText = request.getParameter(RequestParameter.SEARCH_TEXT);
        if (status != null && status.trim().isEmpty()) {
            status=null;
        }
         if (searchText != null && searchText.trim().isEmpty()) {
            searchText=null;
        }
        if (request.getParameter(RequestParameter.PAGE) == null) {
            page = DEFAULT_PAGE;
        } else {
            page = Integer.parseInt(request.getParameter(RequestParameter.PAGE));
        }
        try {
            List<User> students = userService.searchStudents(status == null ? null : UserStatus.valueOf(status.toUpperCase()), searchText == null ? "" : searchText, page);
            List<User> nextStudents = userService.searchStudents(status == null ? null : UserStatus.valueOf(status.toUpperCase()), searchText == null ? "" : searchText, page + 1);
            request.setAttribute(RequestAttribute.ALL_STUDENTS, students);
            request.setAttribute(RequestAttribute.PAGE, page);
            request.setAttribute(RequestAttribute.STATUS, status);
            request.setAttribute(RequestAttribute.SEARCH_TEXT, searchText);
            request.setAttribute(RequestAttribute.LAST, nextStudents.isEmpty());
            return new Router(PagePath.ALL_STUDENTS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to all students page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
