package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.model.dao.impl.UserDaoImpl;

public class DaoProvider {

    private DaoProvider() {

    }

    public static DaoProvider getInstance() {
        return DaoProviderHolder.instance;
    }

    public UserDao getUserDao(boolean isTransaction){
        return new UserDaoImpl(isTransaction);
    }


    private static class DaoProviderHolder {
        private static final DaoProvider instance = new DaoProvider();
    }
}
