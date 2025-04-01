package com.example.model;

public class BookingServices {
    private int bookingId;
    private int serviceId;

    public BookingServices(int bookingId, int serviceId) {
        this.bookingId = bookingId;
        this.serviceId = serviceId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "bookingId=" + bookingId +
                ", serviceId=" + serviceId;
    }
}
