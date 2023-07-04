package com.epam.finaltask.library.model.dao.mapper.impl;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.model.dao.ColumnName;
import com.epam.finaltask.library.model.dao.mapper.BaseMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.finaltask.library.model.dao.ColumnName.ID;

public class BookMapper implements BaseMapper<Book> {

    private static final BookMapper instance = new BookMapper();

    public static BookMapper getInstance() {
        return instance;
    }

    public BookMapper() {
    }

    @Override
    public List<Book> retrieve(ResultSet resultSet) throws SQLException {
        List<Book> books = new ArrayList<>();
        while (resultSet.next()){
            Book book = new Book();
            book.setId(resultSet.getInt(ID));
            book.setTitle(resultSet.getString(ColumnName.BOOK_TITLE));
            book.setAuthor(resultSet.getString(ColumnName.BOOK_AUTHOR));
            book.setGenreId(resultSet.getInt(ColumnName.BOOK_GENRE_ID));
            book.setDescription(resultSet.getString(ColumnName.BOOK_DESCRIPTION));
            book.setBookCopies(resultSet.getInt(ColumnName.BOOK_COPIES));
            books.add(book);
        }
        return books;
    }
}
