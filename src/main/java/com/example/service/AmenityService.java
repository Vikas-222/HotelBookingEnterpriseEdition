package com.example.service;

import com.example.common.exception.DBException;
import com.example.dao.IAmenityDAO;
import com.example.dao.impl.AmenityDAOImpl;

public class AmenityService {

    IAmenityDAO iAmenityDAO = new AmenityDAOImpl();

    public boolean addAmenity(String name,int categoryId) throws DBException {
        iAmenityDAO.addAmenity(name,categoryId);
    }
}
