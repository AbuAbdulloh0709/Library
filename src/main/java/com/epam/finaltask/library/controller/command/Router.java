package com.epam.finaltask.library.controller.command;

public class Router {

    private String page = PagePath.HOME;
    private RouterType type = RouterType.FORWARD;

    public Router(String page, RouterType type) {
        if (page != null)
            this.page = page;
        if (type != null)
            this.type = type;
    }

    public String getPage() {
        return page;
    }

    public RouterType getType() {
        return type;
    }

    public enum RouterType {
        FORWARD,
        REDIRECT
    }
}
