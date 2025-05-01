package com.example.common.entitymapper;

import com.example.common.mapper.RoomMapper;
import com.example.dto.BookingDTO;
import com.example.dto.RoomDTO;
import com.example.dto.UsersDTO;
import com.example.entitymodal.Booking;

import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {

        public static Booking convertBookingDTOToEntity(BookingDTO bookingDTO, UsersDTO user, RoomDTO room) {
            return new Booking.Builder()
                    .setBookingId(bookingDTO.getBookingId())
                    .setUser(UserMap.dtoToUser(user))
                    .setRoom(RoomMapper.convertRoomDTOToRoom(room))
                    .setCheckInTime(bookingDTO.getCheckInTime())
                    .setCheckOutTime(bookingDTO.getCheckOutTime())
                    .setTotalAmount(bookingDTO.getTotalAmount())
                    .setGstRates(bookingDTO.getGstRates())
                    .setBookingStatus(bookingDTO.getBookingStatus())
                    .setCancellationDate(bookingDTO.getCancellationDate())
                    .setRefundAmount(bookingDTO.getRefundAmount())
                    .setNumberOfGuests(bookingDTO.getNumberOfGuests())
                    .build();
        }

        public static BookingDTO convertEntityToBookingDTO(Booking booking) {
            return new BookingDTO.Builder()
                    .setBookingId(booking.getBookingId())
                    .setUserId(booking.getUser().getUserId())
                    .setRoomId(booking.getRoom().getRoomId())
                    .setCheckInTime(booking.getCheckInTime())
                    .setCheckOutTime(booking.getCheckOutTime())
                    .setTotalAmount(booking.getTotalAmount())
                    .setGstRates(booking.getGstRates())
                    .setBookingStatus(booking.getBookingStatus())
                    .setCancellationDate(booking.getCancellationDate())
                    .setRefundAmount(booking.getRefundAmount())
                    .setNumberOfGuests(booking.getNumberOfGuests())
                    .build();
        }

        public static List<BookingDTO> convertEntityListToBookingDTOList(List<Booking> list) {
            return list.stream().map(booking -> new BookingDTO.Builder()
                    .setBookingId(booking.getBookingId())
                    .setUserId(booking.getUser().getUserId())
                    .setRoomId(booking.getRoom().getRoomId())
                    .setBookingStatus(booking.getBookingStatus())
                    .setCheckInTime(booking.getCheckInTime())
                    .setCheckOutTime(booking.getCheckOutTime())
                    .setCancellationDate(booking.getCancellationDate())
                    .setTotalAmount(booking.getTotalAmount())
                    .setGstRates(booking.getGstRates())
                    .setRefundAmount(booking.getRefundAmount())
                    .setNumberOfGuests(booking.getNumberOfGuests())
                    .build()).collect(Collectors.toList());
        }

}
