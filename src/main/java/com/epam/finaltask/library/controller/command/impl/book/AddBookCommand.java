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

public class AddBookCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    private final BookService bookService = BookServiceImpl.getInstance();
    private final GenreService genreService = GenreServiceImpl.getInstance();
    private static final String ADD_BOOK_SUCCESS_MESSAGE = "book.added";
    private static final String ADD_BOOK_AVAILABLE_TITLE_MESSAGE = "book.title.occupied";

    private static final String UPLOAD_DIR = "images";
    private String dbFileName = "";

    @Override
    public Router execute(HttpServletRequest request) {
        String title = request.getParameter(RequestParameter.BOOK_TITLE);
        String description = request.getParameter(RequestParameter.BOOK_DESCRIPTION);
        String author = request.getParameter(RequestParameter.BOOK_AUTHOR);
        int genre_id = Integer.parseInt(request.getParameter(RequestParameter.BOOK_GENRE_ID));
        int book_copies = Integer.parseInt(request.getParameter(RequestParameter.BOOK_COPIES));
        Book book = new Book();
        book.setTitle(title);
        book.setDescription(description);
        book.setAuthor(author);
        book.setBookCopies(book_copies);
        book.setGenre(new Genre(genre_id));
        try {
            if (bookService.isBookTitleOccupied(title)) {
                List<Genre> genres = genreService.getAllGenres();
                request.setAttribute(RequestAttribute.GENRES, genres);
                request.setAttribute(RequestAttribute.MESSAGE, ADD_BOOK_AVAILABLE_TITLE_MESSAGE);
                return new Router(PagePath.ADD_BOOKS, Router.RouterType.FORWARD);
            }
            saveImage(request);
            bookService.addBook(book, dbFileName);
            List<Genre> genres = genreService.getAllGenres();
            request.setAttribute(RequestAttribute.GENRES, genres);
            request.setAttribute(RequestAttribute.MESSAGE, ADD_BOOK_SUCCESS_MESSAGE);
            return new Router(PagePath.ADD_BOOKS, Router.RouterType.FORWARD);
        } catch (ServiceException e) {
            LOGGER.error("Error has occurred while redirecting to add book page: " + e);
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
            System.out.println("applicationPath:" + applicationPath);
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
