package com.epam.finaltask.library.controller.command.impl.book;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.PagePath;
import com.epam.finaltask.library.controller.command.RequestParameter;
import com.epam.finaltask.library.controller.command.Router;
import com.epam.finaltask.library.controller.command.impl.RequestAttribute;
import com.epam.finaltask.library.entity.Book;
import com.epam.finaltask.library.entity.Genre;
import com.epam.finaltask.library.exception.ServiceException;
import com.epam.finaltask.library.model.service.BookService;
import com.epam.finaltask.library.model.service.GenreService;
import com.epam.finaltask.library.model.service.impl.BookServiceImpl;
import com.epam.finaltask.library.model.service.impl.GenreServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ReplaceBookImageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookService bookService = BookServiceImpl.getInstance();
    private final GenreService genreService = GenreServiceImpl.getInstance();
    private static final int DEFAULT_PAGE = 1;
    private static final String REPLACE_BOOK_IMAGE_SUCCESS_MESSAGE = "book.image_replaced";

    private static final String UPLOAD_DIR = "images";
    private String dbFileName = "";
    @Override
    public Router execute(HttpServletRequest request) {
        int book_id = Integer.parseInt(request.getParameter(RequestParameter.BOOK_ID));

        try {
            saveImage(request);
            bookService.replaceBookImage(book_id,dbFileName);

            List<Genre> genres = genreService.getAllGenres();
            List<Book> books = bookService.getAllBooks(DEFAULT_PAGE);
            List<Book> nextBooks = bookService.getAllBooks(DEFAULT_PAGE + 1);
            request.setAttribute(RequestAttribute.GENRES, genres);
            request.setAttribute(RequestAttribute.BOOKS, books);
            request.setAttribute(RequestAttribute.PAGE, DEFAULT_PAGE);
            request.setAttribute(RequestAttribute.LAST, nextBooks.isEmpty());
            request.setAttribute(RequestAttribute.MESSAGE, REPLACE_BOOK_IMAGE_SUCCESS_MESSAGE);
            return new Router(PagePath.ALL_BOOKS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to all books in library page: " + e);
            return new Router(PagePath.ERROR_404, Router.RouterType.REDIRECT);
        }
    }
    private void saveImage(HttpServletRequest request) {
        Part part = null;
        try {
            part = request.getPart(RequestParameter.BOOK_IMAGE_UPLOAD);
            String fileName = extractFileName(part);//file name

            /**
             * *** Get The Absolute Path Of The Web Application ****
             */
            String applicationPath = request.getServletContext().getRealPath("");
            String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
            deleteOldImage(uploadPath+File.separator+request.getParameter(RequestParameter.OLD_IMAGE_PATH));
            File fileUploadDirectory = new File(uploadPath);
            if (!fileUploadDirectory.exists()) {
                fileUploadDirectory.mkdirs();
            }
            UUID randomUUID = UUID.randomUUID();
            String savePath = uploadPath + File.separator + randomUUID + fileName;
            System.out.println("savePath: " + savePath);
            String sRootPath = new File(savePath).getAbsolutePath();
            System.out.println("sRootPath: " + sRootPath);
            part.write(savePath + File.separator);
            dbFileName = UPLOAD_DIR + File.separator + randomUUID + fileName;
        } catch (IOException | ServletException e) {
            LOGGER.error("Error has occurred while redirecting to add book page: " + e);
            e.printStackTrace();
        }
    }

    private void deleteOldImage(String filePath){
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            try {
               fileToDelete.delete();
            } catch (SecurityException e) {
                LOGGER.error("Unable to delete the file due to security restrictions." + e);
                e.printStackTrace();
            }
        }
    }

    private String extractFileName(Part part) {//This method will print the file name.
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }
}
