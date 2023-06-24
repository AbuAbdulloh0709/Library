package com.epam.finaltask.library.controller.command.impl;

import com.epam.finaltask.library.controller.command.Command;
import com.epam.finaltask.library.controller.command.impl.go_to.GoToSignInCommand;
import com.epam.finaltask.library.controller.command.impl.go_to.GoToSignUpCommand;
import com.epam.finaltask.library.controller.command.impl.signing.SignInCommand;
import com.epam.finaltask.library.controller.command.impl.signing.SignUpCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public enum CommandType {
    GO_TO_SIGN_UP(new GoToSignUpCommand()),
    GO_TO_SIGN_IN(new GoToSignInCommand()),
    GO_TO_ADMIN(new GoToSignInCommand()),

    SIGN_UP(new SignUpCommand()),
    SIGN_IN(new SignInCommand()),
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
