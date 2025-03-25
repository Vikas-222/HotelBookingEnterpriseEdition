package com.example.validation;

import com.example.common.Messages;
import com.example.common.exception.ApplicationException;
import com.example.dto.BookingDTO;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Stream;

public class BookingValidator {

    public static boolean validateBooking(BookingDTO bookingDTO) throws ApplicationException {
        if (isNullCheckUserValues(bookingDTO)) {
            throw new ApplicationException(Messages.Error.INVALID_VALUES);
        }
        if(!isDateValid(bookingDTO.getCheckInTime(),bookingDTO.getCheckOutTime())){
            throw new ApplicationException(Messages.BookingError.INVALID_DATE);
        }
        return true;
    }

    public static boolean isNullCheckUserValues(BookingDTO bookingDTO) {
        return  bookingDTO.getCheckInTime() == null ||
                bookingDTO.getCheckOutTime() == null ||
                String.valueOf(bookingDTO.getRoomNumber()) == null || bookingDTO.getRoomNumber() <= 0 ||
                String.valueOf(bookingDTO.getTotalAmount()) == null || bookingDTO.getTotalAmount() <= 0 ||
                String.valueOf(bookingDTO.getNumberOfGuests()) == null || bookingDTO.getNumberOfGuests() <= 0;
    }

    public static boolean isDateValid(LocalDateTime from, LocalDateTime to) {
        LocalDateTime today = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
        if(to.isBefore(today)){
            return false;
        }
        if (from.isAfter(to) || to.isBefore(from)) {
            return false;
        }
        return true;
    }


}
