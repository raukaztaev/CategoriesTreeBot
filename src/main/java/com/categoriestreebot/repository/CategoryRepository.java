package com.categoriestreebot.repository;

import com.categoriestreebot.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findCategoryByNameAndChatId(String name, long chatId);

    void deleteCategoryByNameAndChatId(String name, long chatId);

    List<Category> findAllByChatId(long chatId);
}