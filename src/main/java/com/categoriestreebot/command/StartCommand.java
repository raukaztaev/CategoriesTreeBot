package com.categoriestreebot.command;

import com.categoriestreebot.controller.CategoryBot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Class responsible for handling the /start command.
 * Command is invoked when a user starts a conversation with the bot.
 */
@Component
public class StartCommand implements Command {
    @Override
    public String getCommandName() {
        return "/start";
    }

    /**
     * Sends greeting message to user with advice to invoke /help command
     * @param update        the message sent by the user, containing information about the command and chatId
     * @param categoryBot   the bot instance that sends messages back to the user
     */
    @Override
    public void execute(Update update, CategoryBot categoryBot) {
        categoryBot.sendMessage(update.getMessage().getChatId(),
                "Hi! I'll help you to manage your elements. Type /help to see my commands.");
    }
}
