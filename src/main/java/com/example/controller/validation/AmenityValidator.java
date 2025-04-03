package com.example.controller.validation;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.dto.AmenitiesDTO;

public class AmenityValidator {

    public static boolean isValidValues(AmenitiesDTO amenitiesDTO) throws ApplicationException {
        if(amenitiesDTO.getAmenityName().isBlank() || String.valueOf(amenitiesDTO.getCategoryId()).isBlank()){
            throw new ApplicationException(Messages.AmenityError.INVALID_VALUES);
        }

        if(amenitiesDTO.getCategoryId() <= 0){
            throw new ApplicationException(Messages.AmenityError.INVALID_CATEGORY);
        }
        return true;
    }

    public static boolean isValidForUpdate(AmenitiesDTO amenitiesDTO) throws ApplicationException {
        if(amenitiesDTO.getAmenityName().isBlank() && String.valueOf(amenitiesDTO.getCategoryId()).isBlank()){
            throw new ApplicationException(Messages.AmenityError.INVALID_VALUES);
        }

        if(amenitiesDTO.getCategoryId() <= 0){
            throw new ApplicationException(Messages.AmenityError.INVALID_CATEGORY);
        }
        return true;
    }
}
