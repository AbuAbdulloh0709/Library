package com.epam.finaltask.library.controller.command.impl.orders;

import com.epam.finaltask.library.controller.command.*;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
import com.epam.finaltask.library.entity.Order;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.OrderDetailService;
import com.epam.finaltask.library.model.service.OrderService;
import com.epam.finaltask.library.model.service.impl.OrderDetailServiceImpl;
import com.epam.finaltask.library.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;

import static com.epam.finaltask.library.controller.command.RequestParameter.ORDER_DETAIL_USER_ID;
import static com.epam.finaltask.library.controller.command.RequestParameter.ORDER_ID;

public class SearchRequestsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_PAGE = 1;
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        int page;
        String searchText = request.getParameter(RequestParameter.SEARCH_TEXT);

        if (request.getParameter(RequestParameter.PAGE) == null) {
            page = DEFAULT_PAGE;
        } else {
            page = Integer.parseInt(request.getParameter(RequestParameter.PAGE));
        }

        try {
            List<Order> issuedBooks = searchText == null ? orderService.requestedBooks(page) : orderService.searchRequestedBooksByQuery(searchText, page);
            List<Order> nextIssuedBooks = searchText == null ? orderService.requestedBooks(page + 1) : orderService.searchRequestedBooksByQuery(searchText, page + 1);
            request.setAttribute(RequestAttribute.ORDERS, issuedBooks);
            request.setAttribute(RequestAttribute.SEARCH_TEXT, searchText);
            request.setAttribute(RequestAttribute.PAGE, page);
            request.setAttribute(RequestAttribute.LAST, nextIssuedBooks.isEmpty());
            return new Router(PagePath.STUDENT_REQUEST_BOOKS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to issued books page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
