package com.example.dao.impl;

import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.dao.IAmenityDAO;
import com.example.dto.AmenitiesDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AmenityDAOImpl implements IAmenityDAO {

    @Override
    public boolean addAmenity(String amenityName, int categoryId) throws DBException {
        String sql = "INSERT INTO amenities (amenity_name, category_id) VALUES (?,?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, amenityName);
            pst.setInt(2, categoryId);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean updateAmenity(int amenityId, String amenityName, int categoryId) throws DBException {
        String sql = "update amenities set amenity_name = ? and category_id = ? where amenity_id =?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, amenityName);
            pst.setInt(2, categoryId);
            pst.setInt(3, amenityId);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }


    @Override
    public boolean deleteAmenity(int id) throws DBException {
        String sql = "delete from amenities where amenity_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean isValidAmenityId(int id) throws DBException {
        String sql = "select * from amenities where amenity_id = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            rs = pst.executeQuery();
            return rs.next();
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
    }

    @Override
    public List<AmenitiesDTO> getAmenitiesWithCategoryName() throws DBException {
        String sql = "select a.amenity_id,a.amenity_name,c.category_name from amenities as a,category as c where a.category_id = c.category_id";
        ResultSet rs = null;
        List<AmenitiesDTO> list = new ArrayList<>();
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            rs = pst.executeQuery();
            while(rs.next()){
                AmenitiesDTO amenitiesDTO = new AmenitiesDTO.Builder()
                        .setAmenityId(rs.getInt("amenity_id"))
                        .setAmenityName(rs.getString("amenity_name"))
                        .setCategoryName(rs.getString("category_name")).build();
                list.add(amenitiesDTO);
            }
            return list;
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
    }
}
