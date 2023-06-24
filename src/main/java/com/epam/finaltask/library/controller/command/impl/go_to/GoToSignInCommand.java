package com.epam.finaltask.library.controller.command.impl.go_to;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class GoToSignInCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.SIGN_IN, Router.RouterType.FORWARD);
    }
}
