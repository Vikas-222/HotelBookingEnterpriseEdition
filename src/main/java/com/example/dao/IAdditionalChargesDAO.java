package com.example.dao;

import com.example.common.exception.DBException;
import com.example.dto.AdditionalChargesDTO;

import java.sql.SQLException;
import java.util.List;

public interface IAdditionalChargesDAO {
    boolean insertAdditionalCharges(String name, float price) throws DBException;

    AdditionalChargesDTO getAdditionalChargeById(int id) throws DBException;

    List<AdditionalChargesDTO> getAllAdditionalCharges() throws DBException;

    boolean updateAdditionalCharge(int id, String name, float cost) throws DBException;

    boolean deleteAdditionalCharge(int id) throws SQLException, DBException;
}
