package com.example.service;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.common.exception.DBException;
import com.example.controller.validation.AmenityValidator;
import com.example.dao.IAmenityDAO;
import com.example.dao.ICategoryDAO;
import com.example.dao.impl.AmenityDAOImpl;
import com.example.dao.impl.CategoryDAOImpl;
import com.example.dto.AmenitiesDTO;

import java.util.List;

public class AmenityService {

    IAmenityDAO iAmenityDAO = new AmenityDAOImpl();
    ICategoryDAO iCategoryDAO = new CategoryDAOImpl();

    public boolean addAmenity(AmenitiesDTO amenitiesDTO) throws ApplicationException {
        try {
            AmenityValidator.isValidValues(amenitiesDTO);
            if (iCategoryDAO.isValidId(amenitiesDTO.getCategoryId()) == false) {
                throw new ApplicationException(Messages.AmenityError.CATEGORY_NOT_FOUND);
            }
            iAmenityDAO.addAmenity(amenitiesDTO.getAmenityName(), amenitiesDTO.getCategoryId());
        } catch (DBException e) {
            throw e;
        } catch (ApplicationException e) {
            throw e;
        }
        return true;
    }

    public boolean updateAmenity(AmenitiesDTO amenitiesDTO) throws ApplicationException {
        try {
            AmenityValidator.isValidForUpdate(amenitiesDTO);
            if (iCategoryDAO.isValidId(amenitiesDTO.getCategoryId()) == false) {
                throw new ApplicationException(Messages.AmenityError.CATEGORY_NOT_FOUND);
            }
            iAmenityDAO.updateAmenity(amenitiesDTO.getAmenityId(), amenitiesDTO.getAmenityName(), amenitiesDTO.getCategoryId());
        } catch (DBException e) {
            throw e;
        } catch (ApplicationException e) {
            throw e;
        }
        return true;
    }

    public boolean deleteAmenity(int Id) throws ApplicationException {
        try {
            if (Id <= 0) {
                throw new ApplicationException(Messages.AmenityError.INVALID_CATEGORY);
            }
            if (iAmenityDAO.isValidAmenityId(Id) == false) {
                throw new ApplicationException(Messages.AmenityError.CATEGORY_NOT_FOUND);
            }
            iAmenityDAO.deleteAmenity(Id);
        } catch (DBException e) {
            throw e;
        } catch (ApplicationException e) {
            throw e;
        }
        return true;
    }

    public List<AmenitiesDTO> getAllAmenityInfo() throws DBException {
        return iAmenityDAO.getAmenitiesWithCategoryName();
    }

    public String getCategoryName(int amenityId) throws ApplicationException {
        try {
            if (amenityId <= 0) {
                throw new ApplicationException(Messages.AmenityError.INVALID_CATEGORY);
            }
            if (iAmenityDAO.isValidAmenityId(amenityId) == false) {
                throw new ApplicationException(Messages.AmenityError.CATEGORY_NOT_FOUND);
            }
            return iAmenityDAO.getCategoryName(amenityId);
        } catch (DBException e) {
            throw e;
        } catch (ApplicationException e) {
            throw e;
        }
    }
}
