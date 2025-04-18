package com.example.common;

public class Messages {

    public static final String ACCOUNT_CREATED = "Account created successfully";
    public static final String LOGIN_SUCCESSFUL = "Login successful";
    public static final String LOGOUT_SUCCESSFUL = "Logout successful";
    public static final String ROOM_ADDED = "Room added successfully";
    public static final String IMAGE_UPLOADED = "Image uploaded successfully";
    public static final String ROOM_UPDATED = "Room updated successfully";
    public static final String BOOKING_SUCCESS = "Booking successful";
    public static final String PASSWORD_UPDATED = "Password updated successful";
    public static final String AMENITY_ADDED = "Amenity added successfully";
    public static final String AMENITY_UPDATED = "Amenity updation successful";
    public static final String AMENITY_DELETED = "Amenity deletion successful";
    public static final String BOOKING_UPDATED = "Booking modify successful";
    public static final String BOOKING_CANCELLATION = "Booking cancellation successful";

    public static class Error {
        public static final String ALREADY_EXISTS = "A user with this email already exists";
        public static final String INVALID_VALUES = "Please fill all required fields";
        public static final String INVALID_USER_ID = "Invalid UserId";
        public static final String INVALID_EMAIL = "Please enter a valid email address.";
        public static final String FAILED = "Oops! Something went wrong! Please try again later.";
        public static final String WEAK_PASSWORD = "Password must meet the strength requirements.";
        public static final String SAME_PASSWORDS = "New password should be different from Old password.";
        public static final String INVALID_CURRENT_PASSWORD = "Current password is wrong.";
        public static final String INVALID_CONTACT = "Please enter a valid contact number";
        public static final String INVALID_CREDENTIALS = "Invalid credentials";
        public static final String UNAUTHORIZED_ACCESS = "Access denied";
        public static final String USER_NOT_FOUND = "User not found";
        public static final String INVALID_EMAIL_LENGTH = "Email address is too long.";
        public static final String INVALID_NAME_LENGTH = "Name is too long.";
        public static final String INVALID_PASSWORD_LENGTH = "Password is too long.";
        public static final String LOGIN_FIRST = "Please log in to continue";
    }

    public static class RoomError {
        public static final String ROOM_NUMBER_EXISTS = "Sorry! Room number already exists";
        public static final String INVALID_CAPACITY = "Please enter valid room capacity";
        public static final String INVALID_ROOM_PRICE = "Please enter valid room price";
        public static final String INVALID_ROOM_NUMBER = "Please enter valid room number";
        public static final String INVALID_VALUES = "Please provide all required room details.";
        public static final String ROOM_ID_NOT_FOUND = "Room not found";
        public static final String INVALID_IMAGE_PATH = "Invalid Image path";
        public static final String IMAGE_NOT_FOUND = "Please upload an image.";
    }

    public static class BookingError {
        public static final String BOOKING_NOT_FOUND = "Booking not found!";
        public static final String INVALID_ROOM_NUMBER = "Please enter valid room number";
        public static final String INVALID_BOOKING_ID = "Invalid Booking ID";
        public static final String CAPACITY_EXCEEDED = "The number of guests exceeds the capacity of the selected room(s)";
        public static final String INVALID_DATE = "Please select correct date";
        public static final String CHECK_OUT_BEFORE_CHECK_IN = "Check-out date cannot be before check-in date.";
        public static final String PAST_DATE_BOOKING_NOT_ALLOWED = "Bookings cannot be made for past dates.";
        public static final String INVALID_TOTAL_AMOUNT = "Total amount value is invalid";
        public static final String INVALID_NUMBER_OF_GUEST = "Please enter valid number of guest";
        public static final String ACCOUNT_DEACTIVATE = "Your account is deactivated! Please contact to admin";
        public static final String CANNOT_CANCEL_PREVIOUS_BOOKING = "Bookings cannot be cancelled once completed";
        public static final String CANNOT_MODIFY_CANCELLED_BOOKING = "Bookings cannot be modified once cancelled.";
    }

    public static class ReviewError {
        public static final String INVALID_USERID_BOOKINGID = "You can only review bookings you have completed.";
        public static final String FEEDBACK_EMPTY = "Please give us your valuable feedback";
        public static final String RATING_EMPTY = "Rating is mandatory";
        public static final String INVALID_REVIEW_ID = "Review not found";
    }

    public static class CategoryError {
        public static final String INVALID_CATEGORY_NAME = "Please enter valid category name";
        public static final String CATEGORY_NOT_FOUND = "Category not found";
        public static final String INVALID_CATEGORY = "Invalid Category Id";
        public static final String CATEGORY_LIST_EMPTY = "Category list is empty";
    }

    public static class AmenityError {
        public static final String INVALID_VALUES = "Please fill all required fields.";
        public static final String CATEGORY_NOT_FOUND = "Category not found";
        public static final String INVALID_CATEGORY = "Invalid Category Id";
        public static final String CATEGORY_LIST_EMPTY = "Category list is empty";
    }

    public static class ChargesError {
        public static final String EMPTY_CHARGES_NAME = "Please fill charges name field.";
        public static final String EMPTY_CHARGES_COST = "Please fill charges cost field.";
        public static final String INVALID_CHARGES_COST = "Please enter valid charges cost.";
    }

    public static class HotelError {
        public static final String INVALID_VALUES = "Please fill all required fields.";
        public static final String INVALID_TIMES = "Invalid check-in-time/check-out-time.";
        public static final String INVALID_HOTEL_EMAIL = "Please fill valid email.";
        public static final String INVALID_HOTEL_CONTACT = "Please fill valid contact number.";
        public static final String HOTEL_ID_NOT_FOUND = "Hotel not found";
        public static final String INVALID_HOTEL_ID = "Invalid Hotel ID";
        public static final String HOTEL_NAME_SIZE_EXCEEDED = "Hotel name size cannot exceeds 50 characters";
        public static final String HOTEL_ADDRESS_SIZE_EXCEEDED = "Hotel Address size cannot exceeds 120 characters";
        public static final String HOTEL_EMAIL_SIZE_EXCEEDED = "Hotel Name size cannot exceeds 50 characters";
        public static final String HOTEL_CONTACT_SIZE_EXCEEDED = "Contact number should be of 12 digits";
    }
}

