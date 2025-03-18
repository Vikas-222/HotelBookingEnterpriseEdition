package com.example.validation;

import com.example.common.Messages;
import com.example.dto.RoomDTO;
import com.example.dto.SignupRequestUserDTO;
import com.example.exception.ApplicationException;
import com.example.model.Room;

public class RoomValidator {

    public void roomValidate(RoomDTO room) throws ApplicationException {
        if (!isNullRoomValues(room)) {
            throw new ApplicationException(Messages.RoomError.INVALID_VALUES, ApplicationException.ErrorType.USER_ERROR);
        }
        if (checkZeros(room.getRoomNumber())) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER, ApplicationException.ErrorType.USER_ERROR);
        }
        if (checkZeros(room.getCapacity())) {
            throw new ApplicationException(Messages.RoomError.INVALID_CAPACITY, ApplicationException.ErrorType.USER_ERROR);
        }
        if (checkZeros(room.getPricePerNight())) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_PRICE, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!isValidCapacity(room.getCapacity())) {
            throw new ApplicationException(Messages.RoomError.INVALID_CAPACITY, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!isValidRoomPrice(room.getPricePerNight())) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_PRICE, ApplicationException.ErrorType.USER_ERROR);
        }
        if (!isValidRoomNumber(room.getRoomNumber())) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER, ApplicationException.ErrorType.USER_ERROR);
        }
    }

    public static boolean isValidRoomNumber(int roomNumber) {
        String roomNum = String.valueOf(roomNumber);
        if (roomNum.length() > 5) {
            return false;
        }
        if (!roomNum.chars().allMatch(Character::isDigit)) {
            return false;
        }
        return true;
    }

    public static boolean isValidCapacity(int capacity) {
        String cap = String.valueOf(capacity);
        if (cap.length() > 5) {
            return false;
        }
        if (!cap.chars().allMatch(Character::isDigit)) {
            return false;
        }
        return true;
    }

    public static boolean isValidRoomPrice(float price) {
        String roomPrice = String.valueOf(price);
        if (roomPrice.length() > 8) {
            return false;
        }
        if (!roomPrice.chars().allMatch(Character::isDigit)) {
            return false;
        }
        return true;
    }

    public static boolean isNullRoomValues(RoomDTO roomDTO) throws ApplicationException {
        if (String.valueOf(roomDTO.getRoomNumber()).isBlank() || String.valueOf(roomDTO.getCapacity()).isBlank() ||
                String.valueOf(roomDTO.getPricePerNight()).isBlank() || String.valueOf(roomDTO.getRoomType()).isBlank() ||
                  roomDTO.getImagePath().isBlank()) {
            return false;
        }
        return true;
    }

    public boolean checkZeros(int number) {
        while (number > 0) {
            int digit = number % 10;
            if (digit != 0) {
                return true;
            }
        }
        return false;
    }

    public boolean checkZeros(float number) {
        while (number > 0) {
            float digit = number % 10;
            if (digit != 0) {
                return true;
            }
        }
        return false;
    }
}
