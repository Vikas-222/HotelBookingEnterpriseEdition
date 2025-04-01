package com.example.dao;

import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.model.Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAOImpl implements IServicesDAO {

    @Override
    public boolean addService(String name, float cost) throws DBException {
        String sql = "insert into services(service_description,service_cost) values (?,?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, name);
            pst.setFloat(2, cost);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean deleteService(int id) throws DBException {
        String sql = "delete from services where service_id =?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setInt(1, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean updateService(int id, String name, float cost) throws DBException {
        String sql = "update services set service_description = ? and service_cost = ? where service_id =?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);) {
            pst.setString(1, name);
            pst.setFloat(2, cost);
            pst.setInt(3, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }


    @Override
    public List<Services> getService() throws DBException {
        String sql = "select * from services";
        ResultSet rs = null;
        List<Services> list = new ArrayList<>();
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql);) {
            rs = pst.executeQuery();
            while (rs.next()) {
                Services services = new Services(rs.getInt("service_id"), rs.getString("service_description"), rs.getFloat("service_cost"));
                list.add(services);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
        return list;
    }
}
