package com.categoriestreebot.exception;

import com.categoriestreebot.controller.CategoryBot;
import org.springframework.stereotype.Component;

/**
 * Global exception handler for managing exceptions in the bot.
 * <p>
 * This class is responsible for handling validation exceptions and sending
 * appropriate messages to the user through the bot.
 * </p>
 */
@Component
public class GlobalExceptionHandler {

    /**
     * Handles validation exceptions and sends a message to the user.
     *
     * @param chatId      the ID of the chat where the message should be sent
     * @param e           the validation exception that occurred
     * @param categoryBot the bot instance used to send messages
     */
    public void handleValidateException(long chatId, ValidateException e, CategoryBot categoryBot) {
        categoryBot.sendMessage(chatId, e.getMessage() + " Please make sure you used the command correctly.");
    }
}
