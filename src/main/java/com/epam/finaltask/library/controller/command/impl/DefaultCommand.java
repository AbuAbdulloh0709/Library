package com.epam.finaltask.library.controller.command.impl;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class DefaultCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
    }
}
