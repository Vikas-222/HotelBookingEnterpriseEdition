package com.example.common.mapper;


import com.example.dto.BookingDTO;
import com.example.model.Booking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {

    public static Booking convertBookingDTOToEntity(BookingDTO bookingDTO){
        return new Booking.Builder()
                .setBookingId(bookingDTO.getBookingId())
                .setUserId(bookingDTO.getUserId())
                .setRoomNumber(bookingDTO.getRoomNumber())
                .setCheckInTime(bookingDTO.getCheckInTime())
                .setCheckOutTime(bookingDTO.getCheckOutTime())
                .setTotalAmount(bookingDTO.getTotalAmount())
                .setBookingStatus(bookingDTO.getBookingStatus())
                .setCancellationDate(bookingDTO.getCancellationDate())
                .setRefundAmount(bookingDTO.getRefundAmount())
                .build();
    }

    public static BookingDTO convertEntityToBookingDTO(Booking booking){
        return new BookingDTO.Builder()
                .setBookingId(booking.getBookingId())
                .setUserId(booking.getUserId())
                .setRoomNumber(booking.getRoomNumber())
                .setCheckInTime(booking.getCheckInTime())
                .setCheckOutTime(booking.getCheckOutTime())
                .setTotalAmount(booking.getTotalAmount())
                .setBookingStatus(booking.getBookingStatus())
                .setCancellationDate(booking.getCancellationDate())
                .setRefundAmount(booking.getRefundAmount())
                .build();
    }

    public static List<BookingDTO> convertEntityListToBookingDTOList(List<Booking> list){
        return list.stream().map(bookingDTO -> new BookingDTO.Builder()
                .setBookingId(bookingDTO.getBookingId())
                .setUserId(bookingDTO.getUserId())
                .setRoomNumber(bookingDTO.getRoomNumber())
                .setBookingStatus(bookingDTO.getBookingStatus())
                .setCheckInTime(bookingDTO.getCheckInTime())
                .setCheckOutTime(bookingDTO.getCheckOutTime())
                .setCancellationDate(bookingDTO.getCancellationDate())
                .setTotalAmount(bookingDTO.getTotalAmount())
                .setRefundAmount(bookingDTO.getRefundAmount())
                .build()).collect(Collectors.toList());
    }
}
