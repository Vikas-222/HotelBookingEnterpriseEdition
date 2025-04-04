package com.example.service;

import com.example.common.exception.ApplicationException;
import com.example.controller.validation.AdditionalChargesValidator;
import com.example.dao.IAdditionalChargesDAO;
import com.example.dao.impl.AdditionalChargesDAOImpl;
import com.example.dto.AdditionalChargesDTO;

public class AdditionalChargesService {

    IAdditionalChargesDAO charges = new AdditionalChargesDAOImpl();

    public boolean addCharges(AdditionalChargesDTO chargesDTO) throws ApplicationException {
        AdditionalChargesValidator.isValidData(chargesDTO);
        return charges.insertAdditionalCharges(chargesDTO.getChargesName(), chargesDTO.getCost());
    }
}
