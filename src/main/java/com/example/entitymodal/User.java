package com.example.entitymodal;

import com.example.common.enums.Gender;
import com.example.common.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, length = 60, unique = true)
    private String email;

    @Column(name = "contact", nullable = false, length = 15)
    private String contactNumber;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "passwords", length = 15)
    private String password;

    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Column(name = "is_active", columnDefinition = "TINYINT default 1")
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private boolean isActive; //Default value

    @Column(name = "profilePic", length = 70)
    private String profilePic;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //Public constructor for Hibernate
    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(int id,String email, Role role) {
        this.userId = id;
        this.email = email;
        this.role = role;
    }

    // Private constructor for Builder pattern
    private User(Builder builder) {
        this.userId = builder.userId;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.contactNumber = builder.contactNumber;
        this.gender = builder.gender;
        this.password = builder.password;
        this.role = builder.role;
        this.isActive = builder.isActive;
        this.profilePic = builder.profilePic;
        this.createdAt = builder.createdAt;
        this.updatedAt = builder.updatedAt;
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

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static class Builder {
        private int userId;
        private String firstName;
        private String lastName;
        private String email;
        private String contactNumber;
        private Gender gender;
        private String password;
        private Role role;
        private boolean isActive;
        private String profilePic;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;

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

        public Builder setRole(Role role) {
            this.role = role;
            return this;
        }

        public Builder setIsActive(boolean isActive) {
            this.isActive = isActive;
            return this;
        }

        public Builder setProfilePic(String profilePic) {
            this.profilePic = profilePic;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Builder setUpdatedAt(LocalDateTime updatedAt) {
            this.updatedAt = updatedAt;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", gender=" + gender +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", isActive=" + isActive +
                ", profilePic='" + profilePic + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}

