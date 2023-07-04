package com.epam.finaltask.library.tag;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.tagext.TagSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class PagesTag  extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger();
    private int page;
    private String command;
    private String status;
    private String search_text;
    private boolean isLast;

    public void setPage(int page) {
        this.page = page;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSearch_text(String search_text) {
        this.search_text = search_text;
    }

    @Override
    public int doStartTag() throws JspException {
        String contextPath = pageContext.getServletContext().getContextPath();
        StringBuilder pageTag = new StringBuilder();
        pageTag.append("<div id=\"pages\" style=\"display: flex; justify-content: center; font-family: Arial, sans-serif;\">");
        pageTag.append("<input value=\"<\" style=\"margin-right: 10px; padding: 4px 8px; border-radius: 4px; background-color: #45a049; color: #FFF; font-weight: bold; border: none; cursor: pointer; font-size: 14px;\" ");
        pageTag.append("onclick=\"location.href='").append(contextPath);
        pageTag.append("/controller?command=").append(command);
        pageTag.append("&page=").append(page - 1);
        pageTag.append("&status=").append(status);
        pageTag.append("&search_text=").append(search_text).append("'\" ");
        if (page == 1) {
            pageTag.append("disabled style=\"background-color: #10a049; color: #888; cursor: not-allowed;\"");
        }
        pageTag.append("/><div style=\"font-weight: bold; font-size: 18px;\">").append(page).append("</div>");
        pageTag.append("<input value=\">\" style=\"margin-left: 10px; padding: 4px 8px; border-radius: 4px; background-color: #45a049; color: #FFF; font-weight: bold; border: none; cursor: pointer; font-size: 14px;\" ");
        pageTag.append("onclick=\"location.href='").append(contextPath);
        pageTag.append("/controller?command=").append(command);
        pageTag.append("&page=").append(page + 1);
        pageTag.append("&status=").append(status);
        pageTag.append("&search_text=").append(search_text).append("'\" ");
        if (isLast) {
            pageTag.append("disabled style=\"background-color: #10a049; color: #888; cursor: not-allowed;\"");
        }
        pageTag.append("/></div>");
        try {
            pageContext.getOut().write(pageTag.toString());
        } catch (IOException exception) {
            LOGGER.error("Error has occurred while writing tag into stream: " + exception);
            throw new JspTagException("Error has occurred while writing tag into stream: ", exception);
        }

        return SKIP_BODY;
    }
}
