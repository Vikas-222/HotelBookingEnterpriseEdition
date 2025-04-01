package com.example.controller.validation;

import com.example.common.Messages;
import com.example.dto.RoomDTO;
import com.example.common.exception.ApplicationException;

public class RoomValidator {
    /**
     * @param room
     * @throws ApplicationException for validation of room object when inserting new room
     */
    public void roomValidate(RoomDTO room) throws ApplicationException {
        if (!isNullRoomValues(room)) {
            throw new ApplicationException(Messages.RoomError.INVALID_VALUES);
        }
        if (checkZeros(room.getRoomNumber())) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        if (checkZeros(room.getCapacity())) {
            throw new ApplicationException(Messages.RoomError.INVALID_CAPACITY);
        }
        if (checkZeros(room.getPricePerNight())) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_PRICE);
        }
        if (!isValidCapacity(room.getCapacity())) {
            throw new ApplicationException(Messages.RoomError.INVALID_CAPACITY);
        }
        if (!isValidRoomPrice(room.getPricePerNight())) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_PRICE);
        }
        if (!isValidRoomNumber(room.getRoomNumber())) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
    }


    /**
     * @param room
     * @throws ApplicationException for validation of room object when updating room's status
     */
    public boolean ValidateForUpdate(RoomDTO room) throws ApplicationException {
        if (!isNullForUpdateRoomValues(room)) {
            System.out.println("validation " + isNullForUpdateRoomValues(room));
            throw new ApplicationException(Messages.RoomError.INVALID_VALUES);
        }
        if (!checkZeros(room.getRoomNumber())) {
            System.out.println("checkzeros");
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        if (!isValidRoomNumber(room.getRoomNumber())) {
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        return true;
    }

    public static boolean isValidRoomNumber(int roomNumber) {
        String roomNum = String.valueOf(roomNumber);
        if (roomNum.length() > 5) {
            return false;
        }
        return roomNum.chars().allMatch(Character::isDigit);
    }

    public static boolean isValidCapacity(int capacity) {
        String cap = String.valueOf(capacity);
        if (cap.length() > 5) {
            return false;
        }
        return cap.chars().allMatch(Character::isDigit);
    }

    public static boolean isValidRoomPrice(float price) {
        String roomPrice = String.valueOf(price);
        if (roomPrice.length() > 8) {
            return false;
        }
        return roomPrice.chars().allMatch(Character::isDigit);
    }

    public static boolean isNullRoomValues(RoomDTO room) {

        return !String.valueOf(room.getRoomNumber()).isBlank() && !String.valueOf(room.getCapacity()).isBlank() &&
                !String.valueOf(room.getPricePerNight()).isBlank() && !String.valueOf(room.getRoomType()).isBlank();
    }

    public static boolean isNullForUpdateRoomValues(RoomDTO room) {
        if (String.valueOf(room.getRoomNumber()).isBlank() && String.valueOf(room.getIsActive()).isBlank()) {
            System.out.println("roomvalidation ");
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
