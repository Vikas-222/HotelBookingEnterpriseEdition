package com.example.controller.validation;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.dto.BookingDTO;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BookingValidator {

    public static boolean validateBooking(BookingDTO bookingDTO) throws ApplicationException {
        try {
            isNullCheckUserValues(bookingDTO);
            isDateValid(bookingDTO.getCheckInTime(), bookingDTO.getCheckOutTime());
        }catch(ApplicationException e){
            throw e;
        }
        return true;
    }

    public static boolean isNullCheckUserValues(BookingDTO bookingDTO) throws ApplicationException {
         if(bookingDTO.getCheckInTime() == null || bookingDTO.getCheckOutTime() == null){
             throw new ApplicationException(Messages.BookingError.INVALID_DATE);
         }
        if(String.valueOf(bookingDTO.getRoomId()).isBlank() || bookingDTO.getRoomId() <= 0 ){
            throw new ApplicationException(Messages.BookingError.INVALID_ROOM_NUMBER);
         }
         if(String.valueOf(bookingDTO.getNumberOfGuests()).isBlank() || bookingDTO.getNumberOfGuests() <= 0){
             throw new ApplicationException(Messages.BookingError.INVALID_NUMBER_OF_GUEST);
         }
         return true;
    }

    public static boolean isDateValid(LocalDateTime from, LocalDateTime to) throws ApplicationException {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        if(to.isBefore(today)){
            throw new ApplicationException(Messages.BookingError.PAST_DATE_BOOKING_NOT_ALLOWED);
        }
        if (from.isAfter(to) || to.isBefore(from)) {
            throw new ApplicationException(Messages.BookingError.CHECK_OUT_BEFORE_CHECK_IN);
        }
        return true;
    }

    public static boolean isValidForModifyBooking(BookingDTO bookingDTO) throws ApplicationException {
        if(bookingDTO.getCheckInTime() == null || bookingDTO.getCheckOutTime() == null){
            throw new ApplicationException(Messages.BookingError.INVALID_DATE);
        }
        if(String.valueOf(bookingDTO.getTotalAmount()).isBlank() || bookingDTO.getTotalAmount() <= 0){
            throw new ApplicationException(Messages.BookingError.INVALID_TOTAL_AMOUNT);
        }
        if(String.valueOf(bookingDTO.getNumberOfGuests()).isBlank() || bookingDTO.getNumberOfGuests() <= 0){
            throw new ApplicationException(Messages.BookingError.INVALID_NUMBER_OF_GUEST);
        }
        return true;
    }


}
