//package com.example.service;
//
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.time.format.DateTimeParseException;
//import java.util.HashMap;
//import java.util.Map;
//
//public class MyHotelService {
//
//    public static LocalTime extractTime(String dateTimeString) {
//        if (dateTimeString == null || dateTimeString.isEmpty()) {
//            return null;
//        }
//        try {
//            LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//            return localDateTime.toLocalTime();
//        } catch (DateTimeParseException e1) {
//            try {
//                LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//                return localDateTime.toLocalTime();
//            }catch(DateTimeParseException e2){
//                try {
//                    // Attempt to parse the string with  another common DateTime format
//                    LocalDateTime localDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
//                    return localDateTime.toLocalTime();
//                }catch(DateTimeParseException e3){
//                    throw new IllegalArgumentException("Invalid DateTime format.  Supported formats are: ISO_LOCAL_DATE_TIME, yyyy-MM-dd HH:mm:ss, yyyy-MM-dd'T'HH:mm:ss.SSS", e3);
//                }
//            }
//        }
//    }
//
//    // Method to process the hotel data, including time extraction
//    public static Map<String, Object> processHotelData(Map<String, Object> payload) {
//        // Create a map to hold the processed data
//        Map<String, Object> processedData = new HashMap<>();
//
//        // Basic validation: Check if the payload is null or empty
//        if (payload == null || payload.isEmpty()) {
//            throw new IllegalArgumentException("Payload cannot be null or empty");
//        }
//
//        // Extract hotel data from the payload
//        String hotelName = (String) payload.get("hotelName");
//        String address = (String) payload.get("address");
//        String contactNumber = (String) payload.get("contactNumber");
//        String email = (String) payload.get("email");
//        String checkInTimeStr = (String) payload.get("checkInTime");
//        String checkOutTimeStr = (String) payload.get("checkOutTime");
//
//        // Extract LocalTime from the DateTime strings
//        LocalTime checkInTime = extractTime(checkInTimeStr);
//        LocalTime checkOutTime = extractTime(checkOutTimeStr);
//
//        // Store the extracted data in the processedData map
//        processedData.put("hotelName", hotelName);
//        processedData.put("address", address);
//        processedData.put("contactNumber", contactNumber);
//        processedData.put("email", email);
//        processedData.put("checkInTime", checkInTime);
//        processedData.put("checkOutTime", checkOutTime);
//
//        return processedData;
//    }
//}
