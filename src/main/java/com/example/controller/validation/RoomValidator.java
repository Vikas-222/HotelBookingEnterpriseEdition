package com.example.controller.validation;

import com.example.common.Messages;
import com.example.dto.RoomDTO;
import com.example.common.exception.ApplicationException;

public class RoomValidator {

    public static void roomValidate(RoomDTO room) throws ApplicationException {
        if (!isNullRoomValues(room)) {
            throw new ApplicationException(Messages.RoomError.INVALID_VALUES);
        }
        if (checkZeros(room.getRoomNumber()) == false) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        if (checkZeros(room.getCapacity()) == false) {
            throw new ApplicationException(Messages.RoomError.INVALID_CAPACITY);
        }
        if (checkZeros(room.getPricePerNight()) == false) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_PRICE);
        }
        if (!isValidCapacity(room.getCapacity())) {
            throw new ApplicationException(Messages.RoomError.INVALID_CAPACITY);
        }
        if (isValidRoomPrice(room.getPricePerNight()) == false) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_PRICE);
        }
        if (!isValidNumber(room.getRoomNumber())) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
    }


    public static boolean isValidForUpdate(int roomId,String status) throws ApplicationException {
        if (!isNullForUpdateRoomValues(roomId,status)) {
            throw new ApplicationException(Messages.RoomError.INVALID_VALUES);
        }
        if (!checkZeros(roomId)) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        if (!isValidNumber(roomId)) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        return true;
    }

    public static boolean isValidNumber(int number) {
        String roomNum = String.valueOf(number);
        if (roomNum.length() > 5) {
            return false;
        }
        return roomNum.chars().allMatch(Character::isDigit);
    }

    public static boolean isValidCapacity(int capacity) {
        String cap = String.valueOf(capacity);
        if (cap.length() > 2) {
            return false;
        }
        return cap.chars().allMatch(Character::isDigit);
    }

    public static boolean isValidRoomPrice(float price) {
        if (!Float.isFinite(price)) {
            return false;
        }
        if (price <= 0) {
            return false;
        }
        return true;
    }

    public static boolean isNullRoomValues(RoomDTO room) {
        return !String.valueOf(room.getRoomNumber()).isBlank() && !String.valueOf(room.getCapacity()).isBlank() &&
                !String.valueOf(room.getPricePerNight()).isBlank() && !String.valueOf(room.getRoomType()).isBlank();
    }

    public static boolean isNullForUpdateRoomValues(int roomId,String status) {
        return !String.valueOf(roomId).isBlank() && !String.valueOf(status).isBlank();
    }

    public static boolean checkZeros(int number) {
        if (number == 0) {
            return false;
        }
        while (number != 0) {
            int digit = number % 10;
            if (digit != 0) {
                return true;
            }
            number /= 10;
        }
        return false;
    }

    public static boolean checkZeros(float number) {
        if (number == 0.0f) {
            return false;
        }
        String numberStr = Float.toString(number);
        for (char c : numberStr.toCharArray()) {
            if (Character.isDigit(c) && c != '0') {
                return true;
            }
        }
        return false;
    }
}