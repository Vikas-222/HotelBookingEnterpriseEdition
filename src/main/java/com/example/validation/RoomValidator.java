package com.example.validation;

import com.example.common.Messages;
import com.example.dao.IRoomDAO;
import com.example.dao.RoomDAOImpl;
import com.example.dto.RoomDTO;
import com.example.common.exception.ApplicationException;
import com.example.service.RoomService;

import java.util.Map;
import java.util.TreeMap;

public class RoomValidator {

    IRoomDAO iRoomDAO = new RoomDAOImpl();
    RoomService roomService = new RoomService(iRoomDAO);

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
        if (roomService.isRoomNumberExists(room.getRoomNumber())) {
            System.out.println(roomService.isRoomNumberExists(room.getRoomNumber()));
            throw new ApplicationException(Messages.RoomError.ROOM_EXISTS);
        }
    }


    /**
     * @param room
     * @throws ApplicationException for validation of room object when updating room's status
     */
    public void ValidateForUpdate(RoomDTO room) throws ApplicationException {
        if (!isNullForUpdateRoomValues(room)) {
            System.out.println("validation " + isNullForUpdateRoomValues(room));
            throw new ApplicationException(Messages.RoomError.INVALID_VALUES);
        }
        if (!checkZeros(room.getRoomNumber())) {
            System.out.println("checkzeros");
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        if (!isValidRoomNumber(room.getRoomNumber())) {
            System.out.println("isValidRoomNumber");
            throw new ApplicationException(Messages.RoomError.INVALID_ROOM_NUMBER);
        }
        if (roomService.isRoomNumberExists(room.getRoomNumber())) {
            System.out.println(iRoomDAO.isRoomNumberExists(room.getRoomNumber()));
            throw new ApplicationException(Messages.RoomError.ROOM_EXISTS);
        }
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

    public static boolean isNullRoomValues(RoomDTO roomDTO) {

        return !String.valueOf(roomDTO.getRoomNumber()).isBlank() && !String.valueOf(roomDTO.getCapacity()).isBlank() &&
                !String.valueOf(roomDTO.getPricePerNight()).isBlank() && !String.valueOf(roomDTO.getRoomType()).isBlank();
    }

    public static boolean isNullForUpdateRoomValues(RoomDTO roomDTO) {
        if (String.valueOf(roomDTO.getRoomNumber()).isBlank() && String.valueOf(roomDTO.getIsActive()).isBlank()) {
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
