package com.example.controller.validation;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.dto.CategoryDTO;

import java.util.List;

public class CategoryValidator {

    public static boolean isValidCategoryName(String name) throws ApplicationException {
        if(name.isBlank()){
            throw new ApplicationException(Messages.CategoryError.INVALID_CATEGORY_NAME);
        }
        return true;
    }

    public static boolean isValidCategoryList(List<CategoryDTO> list) throws ApplicationException {
        if(list.isEmpty()){
            throw new ApplicationException(Messages.CategoryError.CATEGORY_LIST_EMPTY);
        }
        return true;
    }
}
