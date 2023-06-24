package com.epam.finaltask.library.model.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface BaseMapper<T> {
    List<T> retrieve(ResultSet resultSet) throws SQLException;
}
