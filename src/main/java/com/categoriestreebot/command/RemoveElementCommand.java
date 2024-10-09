package com.categoriestreebot.command;

import com.categoriestreebot.controller.CategoryBot;
import com.categoriestreebot.exception.GlobalExceptionHandler;
import com.categoriestreebot.exception.ValidateException;
import com.categoriestreebot.service.CategoryService;
import com.categoriestreebot.validator.RemoveElementCommandValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Class responsible for command /removeElement
 */
@Component
@RequiredArgsConstructor
public class RemoveElementCommand implements Command {
    private final RemoveElementCommandValidator removeElementCommandValidator;
    private final CategoryService categoryService;
    private final GlobalExceptionHandler globalExceptionHandler;

    @Override
    public String getCommandName() {
        return "/removeElement";
    }

    /**
     * Method processes the user's message, validates the input, and
     * calls the appropriate method to remove the specified category and its subcategories
     * @param update
     * @param categoryBot
     */
    @Override
    public void execute(Update update, CategoryBot categoryBot) {
        String[] text = update.getMessage().getText().split("\\s");
        long chatId = update.getMessage().getChatId();

        try {
            removeElementCommandValidator.validateArgsCount(text, chatId);
            removeElementCommandValidator.validateCategoryExists(text[1], chatId);
            categoryService.deleteCategory(text[1], chatId);
            categoryBot.sendMessage(chatId,
                    String.format("Successfully removed category %s and its subcategories.", text[1]));
        } catch (ValidateException e) {
            globalExceptionHandler.handleValidateException(chatId, e, categoryBot);
        }

    }
}
