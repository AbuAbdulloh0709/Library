package com.epam.finaltask.library.model.dao.mapper.impl;

import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.entity.Image;
import com.epam.finaltask.library.model.dao.ColumnName;
import com.epam.finaltask.library.model.dao.mapper.BaseMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.epam.finaltask.library.model.dao.ColumnName.ID;

public class ImageMapper implements BaseMapper<Image> {

    private static final ImageMapper instance = new ImageMapper();

    public static ImageMapper getInstance() {
        return instance;
    }

    public ImageMapper() {
    }

    @Override
    public List<Image> retrieve(ResultSet resultSet) throws SQLException {
        List<Image> images = new ArrayList<>();
        while (resultSet.next()){
            Image image = new Image();
            image.setId(resultSet.getInt(ID));
            image.setUrl(resultSet.getString(ColumnName.IMAGE_URL));
            image.setBookId(resultSet.getInt(ColumnName.IMAGE_BOOK_ID));
            images.add(image);
        }
        return images;
    }
}
