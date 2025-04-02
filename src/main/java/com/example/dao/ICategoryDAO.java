package com.example.dao;

import com.example.common.exception.DBException;
import com.example.model.Category;

import java.util.List;

public interface ICategoryDAO {

    boolean addCategory(String categoryName) throws DBException;

//    List<Category> getAllCategories() throws DBException;

    boolean updateCategory(int id, String categoryName) throws DBException;

    boolean deleteCategory(int categoryId) throws DBException;

    boolean isValidId(int id) throws DBException;
}
