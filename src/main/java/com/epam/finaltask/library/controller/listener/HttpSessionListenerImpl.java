package com.epam.finaltask.library.controller.listener;

import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.entity.enums.UserRole;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

import static com.epam.finaltask.library.controller.command.SessionAttribute.*;

@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {
    private static final String DEFAULT_LOCALE = "en_US";
    private static final String DEFAULT_LANGUAGE = "EN";

    @Override
    public void sessionCreated(HttpSessionEvent sessionEvent) {
        HttpSession session = sessionEvent.getSession();
        session.setAttribute(CURRENT_PAGE, PagePath.HOME);
        session.setAttribute(LOCALE, DEFAULT_LOCALE);
        session.setAttribute(LANGUAGE, DEFAULT_LANGUAGE);
        session.setAttribute(ROLE, UserRole.GUEST.getRole());
    }
}
