package com.epam.finaltask.library.model.service;

import com.epam.finaltask.library.exception.ServiceException;

import java.util.Map;

public interface OrderDetailService {
    boolean returnIssuedBook(Map<String, String> orderDetailMap) throws ServiceException;
    boolean acceptRequestedBook(Map<String, String> orderDetailMap) throws ServiceException;
    boolean rejectRequest(Map<String, String> orderDetailMap) throws ServiceException;
}
