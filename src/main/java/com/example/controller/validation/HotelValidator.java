package com.example.controller.validation;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.dto.HotelDTO;
import com.mysql.cj.protocol.Message;

import java.time.LocalTime;
import java.util.regex.Pattern;

public class HotelValidator {

    public static void isValidDataToSave(HotelDTO hotel) throws ApplicationException {
        if(!isValidHotelName(hotel.getHotelName())){
            throw new ApplicationException(Messages.HotelError.INVALID_VALUES);
        }
        if(!isValidHotelAddress(hotel.getAddress())){
            throw new ApplicationException(Messages.HotelError.INVALID_VALUES);
        }
        if(!isValidHotelEmail(hotel.getEmail())){
            throw new ApplicationException(Messages.HotelError.INVALID_VALUES);
        }
        if(!isValidHotelContactNumber(hotel.getContactNumber())){
            throw new ApplicationException(Messages.HotelError.INVALID_VALUES);
        }
        if(!isValidTime(hotel.getCheckInTime(),hotel.getCheckOutTime())){
            throw new ApplicationException(Messages.HotelError.INVALID_VALUES);
        }
        if(!isValidCheckInTimeCheckOutTime(hotel.getCheckInTime(),hotel.getCheckOutTime())){
            throw new ApplicationException(Messages.HotelError.INVALID_TIMES);
        }
        if(!isValidPhoneNumber(hotel.getContactNumber())){
            throw new ApplicationException(Messages.HotelError.INVALID_HOTEL_CONTACT);
        }
        if(!isValidEmail(hotel.getEmail())){
            throw new ApplicationException(Messages.HotelError.INVALID_HOTEL_EMAIL);
        }
    }


    public static void isValidDataToUpdate(HotelDTO hotel) throws ApplicationException {
        if (hotel.getHotelName().length() > 50) {
            throw new ApplicationException(Messages.HotelError.HOTEL_NAME_SIZE_EXCEEDED);
        }
        if (hotel.getAddress().length() > 120) {
            throw new ApplicationException(Messages.HotelError.HOTEL_NAME_SIZE_EXCEEDED);
        }
        if (hotel.getContactNumber().length() > 12) {
            throw new ApplicationException(Messages.HotelError.HOTEL_CONTACT_SIZE_EXCEEDED);
        }
        if (!isValidCheckInTimeCheckOutTime(hotel.getCheckInTime(),hotel.getCheckOutTime())) {
            throw new ApplicationException(Messages.HotelError.INVALID_TIMES);
        }
    }


    private static boolean isValidHotelName(String hotelName) {
        if (hotelName.isBlank()) {
            return false;
        } else if (hotelName.length() > 50) {
            return false;
        }
        return true;
    }

    private static boolean isValidHotelAddress(String address) {
        if (address.isBlank()) {
            return false;
        } else if (address.length() > 120) {
            return false;
        }
        return true;
    }

    private static boolean isValidHotelContactNumber(String contactNumber) {
        if (contactNumber.isBlank() && contactNumber.length() > 12) {
            return false;
        }
        return true;
    }

    private static boolean isValidHotelEmail(String email) {
        if (email.isBlank() && email.length() > 50) {
            return false;
        }
        return true;
    }

    private static boolean isValidTime(LocalTime checkInTime, LocalTime checkOutTime) {
        if (checkInTime == null) {
            return false;
        }
        if (checkOutTime == null) {
            return false;
        }
        return true;
    }

    private static boolean isValidCheckInTimeCheckOutTime(LocalTime checkInTime, LocalTime checkOutTime) {
        if (checkOutTime.isBefore(checkInTime)) {
            return false;
        }
        return true;
    }


    private static boolean isValidPhoneNumber(String phoneNumber) {
        String phoneRegex = "^\\+?\\d{1,15}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phoneNumber).matches();
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
