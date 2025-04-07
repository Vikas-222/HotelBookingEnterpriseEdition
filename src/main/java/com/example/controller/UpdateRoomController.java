//package com.example.controller;
//
//import com.example.common.AppConstants;
//import com.example.common.utils.CustomObjectMapper;
//import com.example.model.Room;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//
//public class UpdateRoomController extends HttpServlet {
//
//    private static final String IMAGE_UPLOAD_DIRECTORY = "D:\\Demo-Git\\HotelBookingEnterpriseEdition\\src\\main\\webapp\\Images\\RoomImages";
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType(AppConstants.APPLICATION_JSON);
//        Room room = CustomObjectMapper.toObject(request.getReader(), Room.class);
//        String existingImagePath = getExistingImagePath(room.getRoomNumber());
//        Part filePart = request.getPart("image");
//        String imagePath = existingImagePath;
//
//        if (filePart != null && filePart.getSize() > 0) {
//            String fileName = filePart.getSubmittedFileName();
//            imagePath = IMAGE_UPLOAD_DIRECTORY + fileName;
//
//            if (existingImagePath != null && !existingImagePath.isEmpty()) {
//                File oldFile = new File(existingImagePath);
//                if (oldFile.exists()) {
//                    oldFile.delete();
//                }
//            }
//
//            // Save new image
//            try (InputStream inputStream = filePart.getInputStream();
//                 FileOutputStream outputStream = new FileOutputStream(imagePath)) {
//                byte[] buffer = new byte[1024];
//                int bytesRead;
//                while ((bytesRead = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//            }
//        }
//
//        // Update the room in the database
//        updateRoomInDatabase(room, imagePath);
//
//        response.getWriter().write("{\"message\": \"Room updated successfully!\"}");
//    }
//
//    // Get the existing image path from the database
//
//
//}
//}
