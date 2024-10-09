package com.categoriestreebot.service;

import com.categoriestreebot.entity.Category;
import com.categoriestreebot.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Service class for managing categories.
 * <p>
 * Class provides methods to add, delete, and retrieve categories
 * associated with a specific chat ID.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * Adds a new category associated with the specified chat ID.
     *
     * @param category the name of the category to add
     * @param chatId   the chat ID associated with the category
     */
    public void addCategory(String category, long chatId) {
        Category categoryToSave = new Category();
        categoryToSave.setName(category);
        categoryToSave.setChatId(chatId);
        categoryRepository.save(categoryToSave);
    }

    /**
     * Adds a new child category under the specified parent category
     * associated with the specified chat ID.
     *
     * @param parent the name of the parent category
     * @param child  the name of the child category to add
     * @param chatId the chat ID associated with the categories
     */
    @Transactional
    public void addCategory(String parent, String child, long chatId) {
        Category parentCategory = categoryRepository.findCategoryByNameAndChatId(parent, chatId);
        Category childCategory = new Category();
        childCategory.setParent(parentCategory);
        childCategory.setName(child);
        childCategory.setChatId(chatId);

        categoryRepository.save(childCategory);
    }

    /**
     * Retrieves all categories as a formatted string for the specified chat ID.
     *
     * @param chatId the chat ID for which to retrieve categories
     * @return a string representation of all categories associated with the chat ID
     */
    @Transactional(readOnly = true)
    public String getAllCategoriesAsString(long chatId) {
        StringBuilder result = new StringBuilder();
        Set<String> addedCategories = new HashSet<>();
        List<Category> rootCategories = categoryRepository.findAllByChatId(chatId);

        rootCategories.forEach(category -> addCategoryToString(category, result, 0, addedCategories));

        return result.toString();
    }

    /**
     * Recursively adds category names to the provided StringBuilder.
     *
     * @param category        the category to add
     * @param result          the StringBuilder to append category names to
     * @param level           the current level of indentation
     * @param addedCategories a set of added category names to avoid duplicates
     */
    private void addCategoryToString(Category category, StringBuilder result, int level, Set<String> addedCategories) {
        if (addedCategories.add(category.getName())) {
            String indent = "  ".repeat(level);
            result.append(indent).append(category.getName()).append("\n");
        }

        if (category.getChildren() != null && !category.getChildren().isEmpty()) {
            category.getChildren().forEach(child -> addCategoryToString(child, result, level + 1, addedCategories));
        }
    }

    /**
     * Deletes a category associated with the specified chat ID.
     *
     * @param category the name of the category to delete
     * @param chatId   the chat ID associated with the category
     */
    @Transactional
    public void deleteCategory(String category, long chatId) {
        categoryRepository.deleteCategoryByNameAndChatId(category, chatId);
    }
}
