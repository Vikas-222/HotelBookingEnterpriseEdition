package com.example.dto;

public class AmenitiesDTO {

    private int amenityId;
    private String amenityName;
    private int categoryId;

    private AmenitiesDTO(Builder builder) {
        this.amenityId = builder.amenityId;
        this.amenityName = builder.amenityName;
        this.categoryId = builder.categoryId;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int amenityId;
        private String amenityName;
        private int categoryId;

        private Builder() {
        }

        public Builder amenityId(int amenityId) {
            this.amenityId = amenityId;
            return this;
        }

        public Builder amenityName(String amenityName) {
            this.amenityName = amenityName;
            return this;
        }

        public Builder categoryId(int categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public AmenitiesDTO build() {
            return new AmenitiesDTO(this);
        }
    }

    @Override
    public String toString() {
        return "AmenitiesDTO{" +
                "amenityId=" + amenityId +
                ", amenityName='" + amenityName + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
