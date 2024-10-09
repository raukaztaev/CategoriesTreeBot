package com.categoriestreebot.command;

import com.categoriestreebot.controller.CategoryBot;
import com.categoriestreebot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Class responsible for handling the /viewTree command.
 */
@Component
@RequiredArgsConstructor
public class ViewTreeCommand implements Command {
    private final CategoryService categoryService;

    @Override
    public String getCommandName() {
        return "/viewTree";
    }

    /**
     * This method retrieves the user's categories as a string and sends them back
     * to the user. If no categories are found, a message indicating the absence of categories is sent instead
     *
     * @param update      the message sent by the user, containing information about the command and chatId
     * @param categoryBot the bot instance that sends messages back to the user
     */
    @Override
    public void execute(Update update, CategoryBot categoryBot) {
        long chatId = update.getMessage().getChatId();
        String categories = categoryService.getAllCategoriesAsString(chatId);

        if (categories == null || categories.isBlank()) {
            categories = "You don't have any elements yet.";
        }

        categoryBot.sendMessage(chatId, categories);
    }
}
