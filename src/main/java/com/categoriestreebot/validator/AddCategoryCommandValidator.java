package com.categoriestreebot.validator;

import com.categoriestreebot.entity.Category;
import com.categoriestreebot.exception.ValidateException;
import com.categoriestreebot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Validator class for validating commands related to adding categories.
 */
@Component
@RequiredArgsConstructor
public class AddCategoryCommandValidator {
    private final CategoryRepository categoryRepository;


    /**
     * Validates whether a category already exists for the specified chat ID.
     *
     * @param categoryName the name of the category to check
     * @param chatId       the chat ID associated with the category
     * @throws ValidateException if the category already exists
     */
    public void validateCategoryExisting(String categoryName, long chatId) throws ValidateException {
        Category category = categoryRepository.findCategoryByNameAndChatId(categoryName, chatId);
        if (category != null) {
            String errorMessage = "This category already exists.";
            throw new ValidateException(errorMessage);
        }
    }

    /**
     * Validates the number of arguments passed to the command.
     *
     * @param args the arguments passed to the command
     * @throws ValidateException if the number of arguments is invalid
     */
    public void validateArgsCount(String[] args) throws ValidateException {
        if (args.length == 1 || args.length > 3) {
            String errorMessage = "Invalid command parameters.";
            throw new ValidateException(errorMessage);
        }
    }

    /**
     * Validates whether the specified parent category exists for the given chat ID.
     *
     * @param categoryName the name of the parent category to check
     * @param chatId       the chat ID associated with the category
     * @throws ValidateException if the parent category does not exist
     */
    public void validateParentCategoryExists(String categoryName, long chatId) throws ValidateException {
        Category category = categoryRepository.findCategoryByNameAndChatId(categoryName, chatId);
        if (category == null) {
            String errorMessage = "Parent category doesn't exist.";
            throw new ValidateException(errorMessage);
        }
    }
}
