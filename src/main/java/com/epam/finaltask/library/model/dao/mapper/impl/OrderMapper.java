package com.epam.finaltask.library.model.dao.mapper.impl;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.entity.Order;
import com.epam.finaltask.library.entity.User;
import com.epam.finaltask.library.entity.enums.OrderType;
import com.epam.finaltask.library.entity.enums.UserRole;
import com.epam.finaltask.library.entity.enums.UserStatus;
import com.epam.finaltask.library.model.dao.ColumnName;
import com.epam.finaltask.library.model.dao.mapper.BaseMapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.finaltask.library.model.dao.ColumnName.*;

public class OrderMapper implements BaseMapper<Order> {

    private static final OrderMapper instance = new OrderMapper();

    public static OrderMapper getInstance() {
        return instance;
    }

    @Override
    public List<Order> retrieve(ResultSet resultSet) throws SQLException {
        List<Order> orderList = new ArrayList<>();
        while (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getInt(ColumnName.ORDER_ID));

            User user = new User();
            user.setId(resultSet.getInt(ORDER_USER_ID));
            user.setFirstName(resultSet.getString(USER_FIRST_NAME));
            user.setLastName(resultSet.getString(USER_LAST_NAME));
            user.setPassportNumber(resultSet.getString(USER_PASSPORT_NUMBER));
            user.setEmail(resultSet.getString(USER_EMAIL));
            user.setLogin(resultSet.getString(USER_LOGIN));
            user.setPassword(resultSet.getString(USER_PASSWORD));
            user.setRole(UserRole.valueOf(resultSet.getString(USER_ROLE).toUpperCase()));
            user.setAddress(resultSet.getString(USER_ADDRESS));
            user.setBirthDate(resultSet.getString(USER_BIRTH_DATE));
            user.setStatus(UserStatus.valueOf(resultSet.getString(USER_STATUS).toUpperCase()));
            user.setPhoneNumber(new BigInteger(resultSet.getString(USER_PHONE_NUMBER)));
            user.setCreatedAt(resultSet.getTimestamp(USER_CREATED_AT));

            order.setUser(user);

            Book book = new Book();
            book.setId(resultSet.getInt(ORDER_BOOK_ID));
            book.setTitle(resultSet.getString(BOOK_TITLE));
            book.setAuthor(resultSet.getString(BOOK_AUTHOR));
            book.setCreatedAt(resultSet.getTimestamp(BOOK_CREATED_AT));
            Genre genre = new Genre();
            genre.setId(resultSet.getInt(BOOK_GENRE_ID));
            genre.setName(resultSet.getString(GENRE_NAME));
            book.setGenre(genre);
            book.setDescription(resultSet.getString(BOOK_DESCRIPTION));
            book.setBookCopies(resultSet.getInt(BOOK_COPIES));

            order.setBook(book);
            order.setIssueDate(resultSet.getString(ORDER_ISSUE_DATE));
            order.setIssueDate(resultSet.getString(ORDER_RETURN_DATE));
            order.setIssueDate(resultSet.getString(ORDER_USAGE_DAYS));
            order.setOrderType(OrderType.valueOf(resultSet.getString(ORDER_TYPE).toUpperCase()));

            orderList.add(order);
        }

        return orderList;
    }
}
