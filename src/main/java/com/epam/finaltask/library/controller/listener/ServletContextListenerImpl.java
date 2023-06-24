package com.epam.finaltask.library.controller.listener;

import com.epam.finaltask.library.model.pool.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ServletContextListenerImpl implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ConnectionPool.getInstance().destroyPool();
    }
}
