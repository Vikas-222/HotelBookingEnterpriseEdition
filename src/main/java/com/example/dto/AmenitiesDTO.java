package com.example.dto;

public class AmenitiesDTO {

    private int amenityId;
    private String amenityName;
    private int categoryId;
    private String categoryName;


    public AmenitiesDTO(Builder builder) {
        this.amenityId = builder.amenityId;
        this.amenityName = builder.amenityName;
        this.categoryId = builder.categoryId;
        this.categoryName = builder.categoryName;
    }

    public AmenitiesDTO() {
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

    public String getCategoryName() {
        return categoryName;
    }

    public static class Builder {
        private int amenityId;
        private String amenityName;
        private int categoryId;
        private String categoryName;

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

        public Builder setCategoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        public AmenitiesDTO build() {
            return new AmenitiesDTO(this);
        }
    }

    @Override
    public String toString() {
        return  "amenityId=" + amenityId +
                ", amenityName='" + amenityName + '\'' +
                ", categoryId=" + categoryId +
                ", categoryName=" + categoryName;
    }
}
