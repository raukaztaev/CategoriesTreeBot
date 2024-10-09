package com.categoriestreebot.command;

import com.categoriestreebot.controller.CategoryBot;
import com.categoriestreebot.exception.GlobalExceptionHandler;
import com.categoriestreebot.exception.ValidateException;
import com.categoriestreebot.service.CategoryService;
import com.categoriestreebot.validator.AddCategoryCommandValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Class responsible for command /addElement 'Element'
 * and /addElement 'ParentElement' 'ChildElement'
 */
@Component
@RequiredArgsConstructor
public class AddCategoryCommand implements Command {
    private final AddCategoryCommandValidator addCategoryCommandValidator;
    private final CategoryService categoryService;
    private final GlobalExceptionHandler globalExceptionHandler;

    @Override
    public String getCommandName() {
        return "/addElement";
    }

    /**
     * Method adds new category or subcategory to existing category depending on params length
     *
     * @param update
     * @param categoryBot
     */
    @Override
    public void execute(Update update, CategoryBot categoryBot) {
        String[] text = update.getMessage().getText().split("\\s");
        long chatId = update.getMessage().getChatId();

        try {
            addCategoryCommandValidator.validateArgsCount(text);
            if (text.length == 2) {
                addNewCategory(text[1], categoryBot, chatId);
            } else {
                addChildCategory(text[1], text[2], categoryBot, chatId);
            }
        } catch (ValidateException e) {
            globalExceptionHandler.handleValidateException(chatId, e, categoryBot);
        }

    }

    /**
     * Adds new category default as root. Checks if that category already exists.
     *
     * @param categoryName name of category to be added
     * @param categoryBot  bot instance that sends response to user
     * @param chatId       the ID of chat where command was issued
     */
    private void addNewCategory(String categoryName, CategoryBot categoryBot, long chatId) {
        try {
            addCategoryCommandValidator.validateCategoryExisting(categoryName, chatId);
            categoryService.addCategory(categoryName, chatId);
            categoryBot.sendMessage(chatId, String.format("Category %s successfully added.", categoryName));
        } catch (ValidateException e) {
            globalExceptionHandler.handleValidateException(chatId, e, categoryBot);
        }
    }

    /**
     * Adds child category specified parent category
     *
     * @param parent      name of parent category
     * @param child       name of child category to be added
     * @param categoryBot bot instance that sends response to user
     * @param chatId      the ID of chat where command was issued
     */
    private void addChildCategory(String parent, String child, CategoryBot categoryBot, long chatId) {
        try {
            addCategoryCommandValidator.validateParentCategoryExists(parent, chatId);
            addCategoryCommandValidator.validateCategoryExisting(child, chatId);
            categoryService.addCategory(parent, child, chatId);
            categoryBot.sendMessage(chatId,
                    String.format("Category %s successfully added as subcategory to %s", child, parent));
        } catch (ValidateException e) {
            globalExceptionHandler.handleValidateException(chatId, e, categoryBot);
        }
    }
}
