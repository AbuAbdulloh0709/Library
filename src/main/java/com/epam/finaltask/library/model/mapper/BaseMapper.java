package com.epam.finaltask.library.model.mapper;

import com.epam.finaltask.library.entity.AbstractEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface BaseMapper<T extends AbstractEntity> {
    List<T> retrieve(ResultSet resultSet) throws SQLException;
}
