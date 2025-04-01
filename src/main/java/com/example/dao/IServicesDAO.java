package com.example.dao;

import com.example.common.exception.DBException;
import com.example.model.Services;

import java.util.List;

public interface IServicesDAO {

    boolean addService(String name, float cost) throws DBException;

    boolean deleteService(int id) throws DBException;

    boolean updateService(int id, String name, float cost) throws DBException;

    List<Services> getService() throws DBException;
}
