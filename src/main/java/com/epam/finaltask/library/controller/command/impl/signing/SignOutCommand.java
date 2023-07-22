package com.epam.finaltask.library.controller.command.impl.signing;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.controller.command.SessionAttribute;
import com.epam.finaltask.library.entity.User;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SignOutCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        ServletContext servletContext = request.getServletContext();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        servletContext.removeAttribute(user.getLogin());
        request.getSession().invalidate();
        return new Router(PagePath.HOME, Router.RouterType.REDIRECT);
    }
}
