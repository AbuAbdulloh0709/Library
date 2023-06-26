package com.epam.finaltask.library.controller.command.impl;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.impl.administrator.AddNewAdministratorCommand;
import com.epam.finaltask.library.controller.command.impl.administrator.AddNewLibrarianCommand;
import com.epam.finaltask.library.controller.command.impl.go_to.*;
import com.epam.finaltask.library.controller.command.impl.signing.SignInCommand;
import com.epam.finaltask.library.controller.command.impl.signing.SignUpCommand;
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
    GO_TO_ALL_BOOKS(new GoToAllBooksCommand()),
    GO_TO_ADD_BOOK_GENRE(new GoToAddBookGenreCommand()),
    GO_TO_ADD_BOOKS(new GoToAddBookCommand()),
    GO_TO_ISSUE_RETURN_BOOKS(new GoToIssueReturnBooksCommand()),
    GO_TO_ISSUED_BOOKS(new GoToIssuedBooksCommand()),



    SIGN_UP(new SignUpCommand()),
    SIGN_IN(new SignInCommand()),
    ADD_ADMINISTRATOR(new AddNewAdministratorCommand()),
    ADD_LIBRARIAN(new AddNewLibrarianCommand()),
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
