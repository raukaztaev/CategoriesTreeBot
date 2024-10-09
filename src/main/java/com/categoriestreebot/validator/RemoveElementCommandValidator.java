package com.categoriestreebot.validator;

import com.categoriestreebot.entity.Category;
import com.categoriestreebot.exception.ValidateException;
import com.categoriestreebot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Validator class for validating commands related to removing categories.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RemoveElementCommandValidator {
    private final CategoryRepository categoryRepository;

    /**
     * Validates the number of arguments passed to the command for removing a category.
     *
     * @param args   the arguments passed to the command
     * @param chatId the chat ID associated with the command
     * @throws ValidateException if the number of arguments is not exactly 2
     */
    public void validateArgsCount(String[] args, long chatId) throws ValidateException {
        if (args.length != 2) {
            String errorMessage = "Invalid command parameters.";
            log.error(errorMessage);
            throw new ValidateException(errorMessage);
        }
    }

    /**
     * Validates whether the specified category exists for the given chat ID.
     *
     * @param categoryName the name of the category to check
     * @param chatId       the chat ID associated with the category
     * @throws ValidateException if the category does not exist
     */
    public void validateCategoryExists(String categoryName, long chatId) throws ValidateException {
        Category category = categoryRepository.findCategoryByNameAndChatId(categoryName, chatId);
        if (category == null) {
            String errorMessage = "Category doesn't exist.";
            log.error(errorMessage);
            throw new ValidateException(errorMessage);
        }
    }
}
