package com.example.model;

public class BookingServices {
    private int bookingId;
    private int service_Id;

    public BookingServices(int bookingId, int service_Id) {
        this.bookingId = bookingId;
        this.service_Id = service_Id;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getService_Id() {
        return service_Id;
    }

    public void setService_Id(int service_Id) {
        this.service_Id = service_Id;
    }

    @Override
    public String toString() {
        return "bookingId=" + bookingId +
                ", service_Id=" + service_Id;
    }
}
