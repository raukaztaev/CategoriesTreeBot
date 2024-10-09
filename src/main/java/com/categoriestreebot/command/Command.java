package com.categoriestreebot.command;

import com.categoriestreebot.controller.CategoryBot;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Interface with basic methods for all commands that can bot do.
 * Implementing classes should define specific commands that the bot can handle,
 * including how the bot should respond to user messages.
 */
public interface Command {

    /**
     * Gets the name on command
     * @return the command name as String
     */
    String getCommandName();

    /**
     * Executes command based on users message
     * @param update        the message sent by the user, containing information about the command and chatId
     * @param categoryBot   the bot instance that sends messages back to the user
     */
    void execute(Update update, CategoryBot categoryBot);
}
