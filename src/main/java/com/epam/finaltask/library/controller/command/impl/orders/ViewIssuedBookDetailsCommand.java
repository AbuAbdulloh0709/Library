package com.epam.finaltask.library.controller.command.impl.orders;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.RequestParameter;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.OrderService;
import com.epam.finaltask.library.model.service.impl.OrderServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ViewIssuedBookDetailsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final OrderService orderService = OrderServiceImpl.getInstance();

    @Override
    public Router execute(HttpServletRequest request) {
        int order_id = Integer.parseInt(request.getParameter(RequestParameter.ORDER_ID));
        try {
            request.setAttribute(RequestAttribute.ORDER, orderService.findOrderById(order_id).get());
            return new Router(PagePath.VIEW_ISSUED_BOOK_DETAILS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while issued book with details: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
}
