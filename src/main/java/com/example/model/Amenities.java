package com.example.model;

import java.time.LocalDateTime;

public class Amenities {
    private int amenityId;
    private String amenityName;
    private int categoryId;
    private LocalDateTime createdAt;

    private Amenities(Builder builder) {
        this.amenityId = builder.amenityId;
        this.amenityName = builder.amenityName;
        this.categoryId = builder.categoryId;
        this.createdAt = builder.createdAt;
    }

    public int getAmenityId() {
        return amenityId;
    }

    public String getAmenityName() {
        return amenityName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public static class Builder {
        private LocalDateTime createdAt;
        private int amenityId;
        private String amenityName;
        private int categoryId;

        public Builder setAmenityId(int amenityId) {
            this.amenityId = amenityId;
            return this;
        }

        public Builder setAmenityName(String amenityName) {
            this.amenityName = amenityName;
            return this;
        }

        public Builder setCategoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public Builder setCreatedAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Amenities build() {
            return new Amenities(this);
        }

        @Override
        public String toString() {
            return  "createdAt=" + createdAt +
                    ", amenityId=" + amenityId +
                    ", amenityName='" + amenityName + '\'' +
                    ", categoryId=" + categoryId;
        }
    }
}
