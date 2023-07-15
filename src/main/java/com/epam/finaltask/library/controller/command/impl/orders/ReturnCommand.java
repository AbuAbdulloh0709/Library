package com.epam.finaltask.library.controller.command.impl.orders;

import com.epam.finaltask.library.controller.command.*;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
import com.epam.finaltask.library.entity.Order;
import com.epam.finaltask.library.entity.enums.OrderType;
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

public class ReturnCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int DEFAULT_PAGE = 1;
    private final OrderService orderService = OrderServiceImpl.getInstance();
    private final OrderDetailService orderDetailService = OrderDetailServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        int page;
        String issueType = request.getParameter(RequestParameter.ISSUE_TYPE);
        String searchText = request.getParameter(RequestParameter.SEARCH_TEXT);
        if (issueType != null && issueType.trim().isEmpty()) {
            issueType = null;
        }
        if (searchText != null && searchText.trim().isEmpty()) {
            searchText = null;
        }

        if (request.getParameter(RequestParameter.PAGE) == null) {
            page = DEFAULT_PAGE;
        } else {
            page = Integer.parseInt(request.getParameter(RequestParameter.PAGE));
        }

        HashMap<String, String> orderDetailMap = new HashMap<>();
        String order_detail_user_id = String.valueOf(request.getSession().getAttribute(SessionAttribute.USER_ID));
        Integer id = Integer.valueOf(request.getParameter(RequestParameter.ID));
        orderDetailMap.put(ORDER_DETAIL_USER_ID, order_detail_user_id);
        orderDetailMap.put(ORDER_ID, String.valueOf(id));

        try {
            orderDetailService.returnIssuedBook(orderDetailMap);
            List<Order> issuedBooks = orderService.searchIssuedBooksByOrderTypeAndQuery(issueType == null ? null : OrderType.valueOf(issueType.toUpperCase()), searchText, page);
            List<Order> nextIssuedBooks = orderService.searchIssuedBooksByOrderTypeAndQuery(issueType == null ? null : OrderType.valueOf(issueType.toUpperCase()), searchText, page + 1);
            request.setAttribute(RequestAttribute.ORDERS, issuedBooks);
            request.setAttribute(RequestAttribute.SEARCH_TEXT,searchText);
            request.setAttribute(RequestAttribute.ISSUE_TYPE,issueType);
            request.setAttribute(RequestAttribute.PAGE, page);
            request.setAttribute(RequestAttribute.LAST, nextIssuedBooks.isEmpty());
            return new Router(PagePath.VIEW_ALL_ISSUED_BOOKS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to issued books page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
