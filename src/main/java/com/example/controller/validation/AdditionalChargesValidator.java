package com.example.controller.validation;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.dto.AdditionalChargesDTO;

public class AdditionalChargesValidator {

    public static boolean isValidData(AdditionalChargesDTO charges) throws ApplicationException {
        if(charges.getChargesName().isBlank()){
            throw new ApplicationException(Messages.ChargesError.EMPTY_CHARGES_NAME);
        }
        if(String.valueOf(charges.getCost()).isBlank()){
            throw new ApplicationException(Messages.ChargesError.EMPTY_CHARGES_COST);
        }
        if(charges.getCost() <= 0){
            throw new ApplicationException(Messages.ChargesError.INVALID_CHARGES_COST);
        }
        return true;
    }
}
