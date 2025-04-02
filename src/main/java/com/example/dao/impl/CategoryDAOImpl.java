package com.example.dao.impl;

import com.example.common.exception.DBException;
import com.example.config.DbConnect;
import com.example.dao.ICategoryDAO;
import com.example.model.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements ICategoryDAO {

    @Override
    public boolean addCategory(String categoryName) throws DBException {
        String sql = "INSERT INTO category (category_name) VALUES (?)";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, categoryName);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

//    public String getCategoryById(int categoryId) {
//        String sql = "SELECT category_name FROM category WHERE category_id = ?";
//        ResultSet rs = null;
//        try (Connection connection = DbConnect.instance.getConnection();
//             PreparedStatement pst = connection.prepareStatement(sql)) {
//            pst.setInt(1, categoryId);
//            rs = pst.executeQuery();
//            if (rs.next()) {
//                category.setCategoryId(resultSet.getInt("categoryId"));
//                category.setCategoryName(resultSet.getString("categoryName"));
//                return category;
//            }
//        } catch (SQLException | ClassNotFoundException e) {
//
//        }
//        return null;
//    }

//    @Override
//    public List<Category> getAllCategories() throws DBException {
//        List<Category> categories = new ArrayList<>();
//        ResultSet rs = null;
//        String sql = "SELECT category_id, category_name FROM category";
//        try (Connection connection = DbConnect.instance.getConnection();
//             PreparedStatement pst = connection.prepareStatement(sql);) {
//            rs = pst.executeQuery();
//            while (rs.next()) {
//                Category category = new Category();
//                category.setCategoryId(rs.getInt("category_id"));
//                category.setCategoryName(rs.getString("category_name"));
//                categories.add(category);
//            }
//        } catch (SQLException | ClassNotFoundException e) {
//            throw new DBException(e);
//        }
//        return categories;
//    }

    @Override
    public boolean updateCategory(int id, String categoryName) throws DBException {
        String sql = "UPDATE category SET category_name = ? WHERE category_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, categoryName);
            pst.setInt(2, id);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean deleteCategory(int categoryId) throws DBException {
        String sql = "DELETE FROM category WHERE category_id = ?";
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, categoryId);
            return pst.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }

    @Override
    public boolean isValidId(int id) throws DBException {
        String sql = "Select * from category where category_id = ?";
        ResultSet rs = null;
        try (Connection connection = DbConnect.instance.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException | ClassNotFoundException e) {
            throw new DBException(e);
        }
    }
}
