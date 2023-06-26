package com.epam.finaltask.library.model.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface BaseMapper<T> {
    List<T> retrieve(ResultSet resultSet) throws SQLException;
}
