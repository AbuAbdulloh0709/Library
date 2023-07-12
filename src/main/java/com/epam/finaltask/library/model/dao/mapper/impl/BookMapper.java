package com.epam.finaltask.library.model.dao.mapper.impl;

import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.BookAvailableCopies;
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
            Genre genre = new Genre();
            genre.setId(resultSet.getInt(ColumnName.BOOK_GENRE_ID));
            genre.setName(resultSet.getString(ColumnName.NAME));
            book.setGenre(genre);
            book.setDescription(resultSet.getString(ColumnName.BOOK_DESCRIPTION));
            book.setBookCopies(resultSet.getInt(ColumnName.BOOK_COPIES));

            BookAvailableCopies bac = new BookAvailableCopies();
            bac.setId(resultSet.getInt(ColumnName.BAC_ID));
            bac.setBookId(resultSet.getInt(ID));
            bac.setAvailableCopies(resultSet.getInt(ColumnName.BAC_AVAILABLE_COPIES));
            book.setBookAvailableCopies(bac);
            books.add(book);
        }
        return books;
    }
}
