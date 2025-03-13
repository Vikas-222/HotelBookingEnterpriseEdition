package com.example.model;

public class User {

    private int userId; //userId
    private String firstName; //firstName
    private String lastName; //lastName
    private String email;
    private String password;
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
        this.password = user.password;
        this.contact = user.contact;
        this.gender = user.gender;
        this.profilePic = user.profilePic;
        this.roles = user.roles;
        this.isActive = user.isActive;
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
        private int userId;
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private String contact;
        private String gender;
        private String profilePic;
        private String roles;
        private boolean isActive;

        public UserBuilder(String firstName, String email, String password, String contact) {
            this.firstName = firstName;
            this.email = email;
            this.password = password;
            this.contact = contact;
        }

        public UserBuilder setUserId(int userId) {
            this.userId = userId;
            return this;
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

    @Override
    public String toString() {
        return "userId='" + userId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", contact='" + contact + '\'' +
                ", gender='" + gender + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", roles='" + roles + '\'' +
                ", isActive=" + isActive;
    }
}
