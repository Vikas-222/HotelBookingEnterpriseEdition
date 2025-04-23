package com.example.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "amenities")
public class Amenities {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "amenity_id")
    private int amenityId;

    @Column(name = "amenity_name", length = 40)
    private String amenityName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Amenities() {
    }

    private Amenities(Builder builder) {
        this.amenityId = builder.amenityId;
        this.amenityName = builder.amenityName;
        this.category = builder.category;
        this.createdAt = builder.createdAt;
    }

    public int getAmenityId() {
        return amenityId;
    }

    public String getAmenityName() {
        return amenityName;
    }

    public Category getCategory() {
        return category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static class Builder {
        private LocalDateTime createdAt;
        private int amenityId;
        private String amenityName;
        private Category category;

        public Builder setAmenityId(int amenityId) {
            this.amenityId = amenityId;
            return this;
        }

        public Builder setAmenityName(String amenityName) {
            this.amenityName = amenityName;
            return this;
        }

        public void setCategory(Category category) {
            this.category = category;
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
                    ", amenityName='" + amenityName + '\'' ;
//                    ", categoryId=" + categoryId;
        }
    }
}
