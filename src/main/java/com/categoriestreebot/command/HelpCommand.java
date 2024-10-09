package com.categoriestreebot.command;

import com.categoriestreebot.controller.CategoryBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Class responsible for command /help.
 * Shows string of all commands which stored on application.yaml
 */
@Component
public class HelpCommand implements Command {

    @Value("${telegram.bot.all_commands}")
    private String ALL_COMMANDS;

    @Override
    public String getCommandName() {
        return "/help";
    }

    /**
     * Sends string from config-file
     * @param update        the message sent by the user, containing information about the command and chatId
     * @param categoryBot   the bot instance that sends messages back to the user
     */
    @Override
    public void execute(Update update, CategoryBot categoryBot) {
        categoryBot.sendMessage(update.getMessage().getChatId(), ALL_COMMANDS);
    }
}
