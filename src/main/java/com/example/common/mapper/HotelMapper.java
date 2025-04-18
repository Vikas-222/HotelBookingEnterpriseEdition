package com.example.common.mapper;

import com.example.dto.HotelDTO;
import com.example.model.Hotel;

public class HotelMapper {

    public static Hotel convertHotelDTOToHotel(HotelDTO hotel){
        Hotel h1 = new Hotel();
        h1.setHotelName(hotel.getHotelName());
        h1.setAddress(hotel.getAddress());
        h1.setContactNumber(hotel.getContactNumber());
        h1.setEmail(hotel.getEmail());
        h1.setCheckInTime(hotel.getCheckInTime());
        h1.setCheckOutTime(hotel.getCheckOutTime());
        return h1;
    }

    //adding hotelId into Hotel object while converting
    public static Hotel convertHotelDTOToHotelForUpdate(HotelDTO hotel,int id){
        Hotel h1 = new Hotel();
        h1.setHotelId(id);
        h1.setHotelName(hotel.getHotelName());
        h1.setAddress(hotel.getAddress());
        h1.setContactNumber(hotel.getContactNumber());
        h1.setEmail(hotel.getEmail());
        h1.setCheckInTime(hotel.getCheckInTime());
        h1.setCheckOutTime(hotel.getCheckOutTime());
        return h1;
    }

    public static HotelDTO convertHotelToHotelDTO(Hotel hotel){
        HotelDTO hoteldto = new HotelDTO();
        hoteldto.setHotelId(hotel.getHotelId());
        hoteldto.setHotelName(hotel.getHotelName());
        hoteldto.setAddress(hotel.getAddress());
        hoteldto.setContactNumber(hotel.getContactNumber());
        hoteldto.setEmail(hotel.getEmail());
        hoteldto.setCheckInTime(hotel.getCheckInTime());
        hoteldto.setCheckOutTime(hotel.getCheckOutTime());
        return hoteldto;
    }
}
