package com.epam.finaltask.library.controller.command.impl.orders;

import com.epam.finaltask.library.controller.command.*;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
import com.epam.finaltask.library.entity.Order;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.OrderService;
import com.epam.finaltask.library.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.List;

public class SearchHistoryForStudentCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        String from_date;
        String to_date;
        int user_id = (int) request.getSession().getAttribute(SessionAttribute.USER_ID);

        if (request.getParameter(RequestParameter.FROM_DATE) == null) {
            from_date = LocalDate.now().withDayOfMonth(1).toString();
        } else {
            from_date = request.getParameter(RequestParameter.FROM_DATE);
        }

        if (request.getParameter(RequestParameter.TO_DATE) == null) {
            to_date = LocalDate.now().toString();
        } else {
            to_date = request.getParameter(RequestParameter.TO_DATE);
        }

        String search_text = request.getParameter(RequestParameter.SEARCH_TEXT);
        try {
            List<Order> issuedBooks = orderService.searchOrderHistoryForStudent(user_id, from_date, to_date, search_text);
            request.setAttribute(RequestAttribute.FROM_DATE, from_date);
            request.setAttribute(RequestAttribute.TO_DATE, to_date);
            request.setAttribute(RequestAttribute.SEARCH_TEXT, search_text);
            request.setAttribute(RequestAttribute.ORDERS, issuedBooks);
            return new Router(PagePath.HISTORY_FOR_STUDENT, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to history page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
