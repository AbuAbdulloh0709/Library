package com.epam.finaltask.library.model.dao.mapper.impl;

import com.epam.finaltask.library.entity.BookAvailableCopies;
import com.epam.finaltask.library.model.dao.mapper.BaseMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.finaltask.library.model.dao.ColumnName.*;

public class BookAvailableCopiesMapper implements BaseMapper<BookAvailableCopies> {
    private static final BookAvailableCopiesMapper instance = new BookAvailableCopiesMapper();

    public static BookAvailableCopiesMapper getInstance() {
        return instance;
    }

    public BookAvailableCopiesMapper() {
    }

    @Override
    public List<BookAvailableCopies> retrieve(ResultSet resultSet) throws SQLException {
        List<BookAvailableCopies> list = new ArrayList<>();
        while (resultSet.next()) {
            BookAvailableCopies bookAvailableCopies = new BookAvailableCopies();
            bookAvailableCopies.setId(resultSet.getInt(ID));
            bookAvailableCopies.setBookId(resultSet.getInt(ORDER_BOOK_ID));
            bookAvailableCopies.setAvailableCopies(resultSet.getInt(AVAILABLE_COPIES));
            list.add(bookAvailableCopies);
        }
        return list;
    }
}
