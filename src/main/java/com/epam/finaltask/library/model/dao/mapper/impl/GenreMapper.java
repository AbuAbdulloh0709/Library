package com.epam.finaltask.library.model.dao.mapper.impl;

import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.model.dao.ColumnName;
import com.epam.finaltask.library.model.dao.mapper.BaseMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.finaltask.library.model.dao.ColumnName.ID;

public class GenreMapper implements BaseMapper<Genre> {

    private static final GenreMapper instance = new GenreMapper();

    public static GenreMapper getInstance() {
        return instance;
    }

    public GenreMapper() {
    }

    @Override
    public List<Genre> retrieve(ResultSet resultSet) throws SQLException {
        List<Genre> genres = new ArrayList<>();
        while (resultSet.next()){
            Genre genre = new Genre();
            genre.setId(resultSet.getInt(ID));
            genre.setName(resultSet.getString(ColumnName.GENRE_NAME));
            genres.add(genre);
        }
        return genres;
    }
}
