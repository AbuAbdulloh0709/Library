package com.epam.finaltask.library.model.dao;

import com.epam.finaltask.library.model.dao.impl.*;

public class DaoProvider {

    private DaoProvider() {

    }

    public static DaoProvider getInstance() {
        return DaoProviderHolder.instance;
    }

    public UserDao getUserDao(boolean isTransaction) {
        return new UserDaoImpl(isTransaction);
    }

    public GenreDao getGenreDao(boolean isTransaction) {
        return new GenreDaoImpl(isTransaction);
    }

    public BookDao getBookDao(boolean isTransaction) {
        return new BookDaoImpl(isTransaction);
    }

    public ImageDao getImageDao(boolean isTransaction) {
        return new ImageDaoImpl(isTransaction);
    }

    public OrderDao getOrderDao(boolean isTransaction) {
        return new OrderDaoImpl(isTransaction);
    }

    public OrderDetailDao getOrderDetailDao(boolean isTransaction) {
        return new OrderDetailDaoImpl(isTransaction);
    }

    private static class DaoProviderHolder {
        private static final DaoProvider instance = new DaoProvider();
    }
}
