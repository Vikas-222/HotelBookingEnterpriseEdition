package com.example.dao;

import com.example.common.exception.DBException;
import com.example.model.Hotel;

public interface IHotelDAO {

    Hotel saveHotel(Hotel hotel) throws DBException;

    Hotel findHotelById(int hotelId) throws DBException;

    void updateHotel(Hotel hotel) throws DBException;
}
