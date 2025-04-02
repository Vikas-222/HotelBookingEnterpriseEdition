package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.controller.validation.CategoryValidator;
import com.example.dao.impl.CategoryDAOImpl;
import com.example.dao.ICategoryDAO;
import com.example.dto.CategoryDTO;

import java.util.List;

public class CategoryService {
    ICategoryDAO iCategoryDAO = new CategoryDAOImpl();

    public boolean addCategory(List<CategoryDTO> list) throws ApplicationException {
        try {
            CategoryValidator.isValidCategoryList(list);
            for (CategoryDTO str : list) {
                CategoryValidator.isValidCategoryName(str.getCategoryName());
                iCategoryDAO.addCategory(str.getCategoryName());
            }
        } catch (ApplicationException e) {
            throw e;
        }
        return true;
    }

    public boolean updateCategory(int id, String name) throws ApplicationException {
        try {
            CategoryValidator.isValidCategoryName(name);
            if (iCategoryDAO.isValidId(id) == false) {
                throw new ApplicationException(Messages.CategoryError.CATEGORY_NOT_FOUND);
            }
            iCategoryDAO.updateCategory(id, name);
        } catch (ApplicationException e) {
            throw e;
        }
        return true;
    }

    public boolean deleteCategory(int id) throws ApplicationException {
        try {
            if (iCategoryDAO.isValidId(id) == false) {
                throw new ApplicationException(Messages.CategoryError.CATEGORY_NOT_FOUND);
            }
            iCategoryDAO.deleteCategory(id);
        } catch (ApplicationException e) {
            throw e;
        }
        return true;
    }
}
