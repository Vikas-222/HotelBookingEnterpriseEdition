package com.example.controller;

import com.example.model.Room;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet(name = "Test", value = "/test")
public class Test extends HttpServlet {

    private static final String IMAGE_UPLOAD_DIRECTORY = "D:\\Demo-Git\\HotelBookingEnterpriseEdition\\src\\main\\webapp\\Images";
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Room room = objectMapper.readValue(request.getReader(), Room.class);

        // Get image part
        Part filePart = request.getPart("image");
        String fileName = filePart.getSubmittedFileName();
        String imagePath = IMAGE_UPLOAD_DIRECTORY + fileName;

        // Save the image to disk
        File uploadDir = new File(IMAGE_UPLOAD_DIRECTORY);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        try (InputStream inputStream = filePart.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(imagePath)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }

        // Save room details with image path to database
        saveRoomToDatabase(room, imagePath);

        response.getWriter().write("{\"message\": \"Room added successfully!\"}");
    }

    private void saveRoomToDatabase(Room room, String imagePath) {
        String jdbcURL = "jdbc:mysql://localhost:3306/hotel_db";
        String dbUser = "root";
        String dbPassword = "password";

        String sql = "INSERT INTO room (room_number, room_type, price_per_night) VALUES (?, ?, ?)";
        String imagesql = "INSERT INTO roomimages (room_id, imagepath) VALUES (?, ?) ";

        try (Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             PreparedStatement pst = connection.prepareStatement(imagesql);) {
            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setString(2, room.getRoomType().toString());
            preparedStatement.setDouble(3, room.getPricePerNight());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if(rs.next()){
                pst.setInt(1,rs.getInt(1));
            }
            pst.setString(2, imagePath);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
