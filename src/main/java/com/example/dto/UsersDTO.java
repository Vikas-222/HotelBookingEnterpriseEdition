package com.example.dto;

import com.example.common.enums.Gender;
import com.example.common.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UsersDTO {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNumber;
    private String newPassword;
    private String password;
    private Gender gender;
    private String profilePic;
    private boolean isActive;
    private Role role;

    public UsersDTO(Builder builder) {
        this.userId = builder.userId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.contactNumber = builder.contactNumber;
        this.password = builder.password;
        this.newPassword = builder.newPassword;
        this.gender = builder.gender;
        this.profilePic = builder.profilePic;
        this.role = builder.role;
        this.isActive = builder.isActive;
    }

    public UsersDTO() {
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

    public Gender getGender() {
        return gender;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

//    @JsonIgnore
    public boolean getIsActive() {
        return isActive;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public static class Builder {
        private int userId;
        private String firstName;
        private String lastName;
        private String email;
        private String contactNumber;
        private Gender gender;
        private String profilePic;
        private Role role = Role.USER;
        private String password;
        private String newPassword;
        private boolean isActive = true;

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

        public Builder setGender(Gender gender) {
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

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder setIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder setNewPassword(String newPassword){
            this.newPassword = newPassword;
            return this;
        }

        public UsersDTO build() {
            return new UsersDTO(this);
        }
    }

    @Override
    public String toString() {
        return  "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", password='" + password + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", gender='" + gender + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive;
    }
}