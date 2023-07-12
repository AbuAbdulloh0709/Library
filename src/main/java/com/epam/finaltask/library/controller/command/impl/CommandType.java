package com.epam.finaltask.library.controller.command.impl;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.impl.book.AddBookCommand;
import com.epam.finaltask.library.controller.command.impl.orders.IssueBookByStudentCommand;
import com.epam.finaltask.library.controller.command.impl.orders.IssueBookCommand;
import com.epam.finaltask.library.controller.command.impl.orders.ReturnIssuedBookCommand;
import com.epam.finaltask.library.controller.command.impl.orders.SearchIssuedBooksCommand;
import com.epam.finaltask.library.controller.command.impl.genre.AddBookGenreCommand;
import com.epam.finaltask.library.controller.command.impl.genre.EditBookGenreCommand;
import com.epam.finaltask.library.controller.command.impl.go_to.*;
import com.epam.finaltask.library.controller.command.impl.signing.SignInCommand;
import com.epam.finaltask.library.controller.command.impl.signing.SignUpCommand;
import com.epam.finaltask.library.controller.command.impl.user.AddNewAdministratorCommand;
import com.epam.finaltask.library.controller.command.impl.user.AddNewLibrarianCommand;
import com.epam.finaltask.library.controller.command.impl.user.ChangeUserStatusCommand;
import com.epam.finaltask.library.controller.command.impl.user.SearchUsersCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    GO_TO_SIGN_UP(new GoToSignUpCommand()),
    GO_TO_SIGN_IN(new GoToSignInCommand()),
    GO_TO_ADMIN(new GoToSignInCommand()),

    GO_TO_DASHBOARD(new GoToDashboardCommand()),
    GO_TO_ADMINISTRATORS(new GoToAdministratorsCommand()),
    GO_TO_LIBRARIANS(new GoToLibrariansCommand()),
    GO_TO_ALL_WAITING_STUDENTS(new GoToAllWaitingStudentsCommand()),
    GO_TO_ALL_APPROVED_STUDENTS(new GoToApprovedStudentsCommand()),
    GO_TO_ALL_STUDENTS(new GoToAllStudentsCommand()),
    GO_TO_ALL_BOOKS(new GoToAllBooksCommand()),
    GO_TO_ADD_BOOK_GENRE(new GoToAddBookGenreCommand()),
    GO_TO_ADD_BOOKS(new GoToAddBookCommand()),
    GO_TO_STUDENT_REQUEST_BOOKS(new GoToStudentRequestBooksCommand()),
    GO_TO_ISSUED_BOOKS(new GoToIssuedBooksCommand()),
    GO_TO_SHOW_BOOKS_FOR_GUEST(new GoToShowBooksForGuestCommand()),
    GO_TO_BOOKS_TO_ISSUE(new GoToBooksToIssueCommand()),
    GO_TO_ISSUE_BOOK_BY_STUDENT(new GoToIssueBookByStudentCommand()),
    GO_TO_ISSUE_BOOK(new GoToIssueBookCommand()),



    SIGN_UP(new SignUpCommand()),
    SIGN_IN(new SignInCommand()),
    ADD_ADMINISTRATOR(new AddNewAdministratorCommand()),
    ADD_LIBRARIAN(new AddNewLibrarianCommand()),
    SEARCH_STUDENTS(new SearchUsersCommand()),
    CHANGE_USER_STATUS(new ChangeUserStatusCommand()),
    ADD_BOOK_GENRE(new AddBookGenreCommand()),
    EDIT_BOOK_GENRE(new EditBookGenreCommand()),
    ADD_BOOK(new AddBookCommand()),
    ISSUE_BOOK_BY_STUDENT(new IssueBookByStudentCommand()),
    ISSUE_BOOK(new IssueBookCommand()),
    SEARCH_ISSUED_BOOKS(new SearchIssuedBooksCommand()),
    RETURN_ISSUED_BOOK(new ReturnIssuedBookCommand()),
    DEFAULT(new DefaultCommand());


    private final Command command;
    private static final Logger LOGGER = LogManager.getLogger();

    CommandType(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    public static Command defineCommand(String commandType) {
        if (commandType == null || commandType.isEmpty()) {
            return CommandType.DEFAULT.getCommand();
        }
        try {
            return CommandType.valueOf(commandType.toUpperCase()).getCommand();
        } catch (IllegalArgumentException e) {
            LOGGER.error("Error has occurred while defining command: " + e);
            return CommandType.DEFAULT.getCommand();
        }
    }
}
