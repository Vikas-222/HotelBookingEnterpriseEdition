package com.example.entity;

public class User {

    private String userId; //userId
    private String firstName; //firstName
    private String lastName; //lastName
    private String email;
    private String passwords;
    private String contact;
    private String gender;
    private String profilePic; //profilePic
    private String roles;
    private boolean isActive; //isActive

    public User(){}

    public User(UserBuilder user) {
        this.userId = user.userId;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.email = user.email;
        this.passwords = user.passwords;
        this.contact = user.contact;
        this.gender = user.gender;
        this.profilePic = user.profilePic;
        this.roles = user.roles;
        this.isActive = user.isActive;
    }

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswords() {
        return passwords;
    }

    public String getLastName() {
        return lastName;
    }

    public String getContact() {
        return contact;
    }

    public String getGender() {
        return gender;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public String getRoles() {
        return roles;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public static class UserBuilder {
        private String userId;
        private String firstName;
        private String lastName;
        private String email;
        private String passwords;
        private String contact;
        private String gender;
        private String profilePic;
        private String roles;
        private boolean isActive;

        public UserBuilder(String firstName, String email, String passwords, String contact) {
            this.firstName = firstName;
            this.email = email;
            this.passwords = passwords;
            this.contact = contact;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        public UserBuilder setProfilePic(String profilePic) {
            this.profilePic = profilePic;
            return  this;
        }

        public UserBuilder setRoles(String roles) {
            this.roles = roles;
            return  this;
        }

        public UserBuilder setIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public User build(){
            return new User(this);
        }
    }

}
