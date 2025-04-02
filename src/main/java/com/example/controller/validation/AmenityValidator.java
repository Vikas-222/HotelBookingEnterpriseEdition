package com.example.controller.validation;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.dto.AmenitiesDTO;

public class AmenityValidator {

    public boolean isValidValues(AmenitiesDTO amenitiesDTO) throws ApplicationException {
        if(amenitiesDTO.getAmenityName().isBlank() || String.valueOf(amenitiesDTO.getCategoryId()).isBlank()){
            throw new ApplicationException(Messages.AmenityError.INVALID_VALUES);
        }
        return true;
    }
}
