package com.example.dto;

import com.example.model.User;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String password;
    private String gender;
    private String profilePic;
    private String role;

    public UserDTO(Builder builder) {
        this.userId = builder.userId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.contactNumber = builder.contactNumber;
        this.password = builder.password;
        this.gender = builder.gender;
        this.profilePic = builder.profilePic;
        this.role = builder.role;
    }

    public UserDTO() {
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public static class Builder {
        private int userId;
        private String firstName;
        private String lastName;
        private String email;
        private String contactNumber;
        private String gender;
        private String profilePic;
        private String role;
        private String password;

        public Builder setUserId(int userId) {
            this.userId = userId;
            return this;
        }

        public Builder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setContactNumber(String contactNumber) {
            this.contactNumber = contactNumber;
            return this;
        }

        public Builder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setProfilePic(String profilePic) {
            this.profilePic = profilePic;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this);
        }
    }

    @Override
    public String toString() {
        return "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", role='" + role;
    }
}