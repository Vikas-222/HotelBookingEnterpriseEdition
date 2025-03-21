package com.example.common.utils;

import com.example.common.enums.RoomType;
import com.example.dto.RoomDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageHandler {

    private static final String IMAGE_UPLOAD_PATH = "/webapp/Images/RoomImages/";

    public static RoomDTO parseRoomFromRequest(HttpServletRequest request) {
        return new RoomDTO.Builder()
                .setRoomNumber(Integer.parseInt(request.getParameter("roomNumber")))
                .setRoomType(RoomType.fromString(request.getParameter("roomType")))
                .setCapacity(Integer.parseInt(request.getParameter("capacity")))
                .setPricePerNight(Float.parseFloat(request.getParameter("pricePerNight"))).build();
    }

    public static List<String> handleImageUpload(HttpServletRequest request) throws IOException, ServletException {
        List<String> imagePaths = new ArrayList<>();
        Part[] fileParts = request.getParts().stream().filter(part -> "image".equals(part.getName())).toArray(Part[]::new);

        for (Part part : fileParts) {
            String fileName = extractFileName(part);
            String filePath = IMAGE_UPLOAD_PATH + fileName;

            // Save file to the server
            part.write(filePath);

            // Store the file path for database insertion
            imagePaths.add(filePath);
        }
        return imagePaths;
    }

    public static String extractFileName(Part part) {
        for (String contentDisposition : part.getHeader("Content-Disposition").split(";")) {
            if (contentDisposition.trim().startsWith("filename")) {
                return contentDisposition.substring(contentDisposition.indexOf('=') + 2, contentDisposition.length() - 1);
            }
        }
        return null;
    }
}
