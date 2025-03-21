package com.example.model;

import com.example.common.enums.BookingStatus;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class Hotel {

    private int hotelId;
    private String hotelName;
    private String address;
    private String contactNumber;
    private String email;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
