package com.example.dao;

import com.example.common.exception.DBException;
import com.example.dto.AmenitiesDTO;

import java.util.List;

public interface IAmenityDAO {
    boolean addAmenity(String amenityName, int categoryId) throws DBException;

    boolean updateAmenity(int amenityId, String amenityName, int categoryId) throws DBException;

    boolean deleteAmenity(int id) throws DBException;

    boolean isValidAmenityId(int id) throws DBException;

    List<AmenitiesDTO> getAmenitiesWithCategoryName() throws DBException;

    String getCategoryName(int id) throws DBException;
}
