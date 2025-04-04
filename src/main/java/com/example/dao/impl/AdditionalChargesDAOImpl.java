package com.example.dao.impl;

import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.dao.IAdditionalChargesDAO;
import com.example.dto.AdditionalChargesDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdditionalChargesDAOImpl implements IAdditionalChargesDAO {

    @Override
    public boolean insertAdditionalCharges(String name, float price) throws DBException {
        String sql = "INSERT INTO additionalcharges (chargesName,cost) VALUES (?, ?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setFloat(2, price);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public AdditionalChargesDTO getAdditionalChargeById(int id) throws DBException {
        String sql = "SELECT chargesName, cost FROM additionalcharges where id = ?";
        AdditionalChargesDTO charges = null;
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            rs = pst.executeQuery();
            while (rs.next()) {
                charges = new AdditionalChargesDTO();
                charges.setId(rs.getInt("id"));
                charges.setChargesName(rs.getString("chargesName"));
                charges.setCost(rs.getFloat("cost"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
        }
        return charges;
    }

    @Override
    public List<AdditionalChargesDTO> getAllAdditionalCharges() throws DBException {
        String sql = "SELECT * FROM additionalcharges";
        List<AdditionalChargesDTO> chargesList = new ArrayList<>();
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            rs = pst.executeQuery();
            while (rs.next()) {
                AdditionalChargesDTO charge = new AdditionalChargesDTO();
                charge.setId(rs.getInt("id"));
                charge.setChargesName(rs.getString("charges_name"));
                charge.setCost(rs.getFloat("cost"));
                chargesList.add(charge);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DBException(e);
                }
            }
        }
        return chargesList;
    }

    @Override
    public boolean updateAdditionalCharge(int id, String name, float cost) throws DBException {
        String sql = "UPDATE additionalcharges set chargesName = ?, cost = ? where id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setFloat(2, cost);
            pst.setInt(3, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean deleteAdditionalCharge(int id) throws DBException {
        String sql = "DELETE FROM additionalcharges WHERE id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }
}
