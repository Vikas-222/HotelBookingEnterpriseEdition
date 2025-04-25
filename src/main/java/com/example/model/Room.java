package com.example.model;

import com.example.common.enums.RoomStatus;
import com.example.common.enums.RoomType;
import com.example.entitymodal.RoomServiceCharge;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", unique = true)
    private int roomId;

    @Column(name = "room_number", unique = true, nullable = false)
    private int roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "price_per_night", nullable = false)
    private float pricePerNight;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_status", nullable = false)
    private RoomStatus roomStatus = RoomStatus.AVAILABLE;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<RoomImages> roomImages = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type", referencedColumnName = "room_type", insertable = false, updatable = false)
    private RoomServiceCharge serviceCharge;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Room() {
    }

    public Room(Builder builder) {
        this.roomNumber = builder.roomNumber;
        this.roomType = builder.roomType;
        this.capacity = builder.capacity;
        this.pricePerNight = builder.pricePerNight;
        this.serviceCharge = builder.serviceCharge;
    }

    public int getRoomId() {
        return roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getCapacity() {
        return capacity;
    }

    public float getPricePerNight() {
        return pricePerNight;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public Set<RoomImages> getRoomImages() {
        return roomImages;
    }

    public RoomServiceCharge getServiceCharge() {
        return serviceCharge;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public static class Builder {
        private int roomId;
        private int roomNumber;
        private RoomType roomType;
        private int capacity;
        private float pricePerNight;
        private RoomStatus roomStatus;
        private Set<RoomImages> roomImages;
        private RoomServiceCharge serviceCharge;

        public Builder setRoomId(int roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder setRoomNumber(int roomNumber) {
            this.roomNumber = roomNumber;
            return this;
        }

        public Builder setRoomType(RoomType roomType) {
            this.roomType = roomType;
            return this;
        }

        public Builder setCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder setPricePerNight(float pricePerNight) {
            this.pricePerNight = pricePerNight;
            return this;
        }

        public Builder setRoomStatus(RoomStatus roomStatus) {
            this.roomStatus = roomStatus;
            return this;
        }

        public void setRoomImages(Set<RoomImages> roomImages) {
            this.roomImages = roomImages;
        }

        public void addImage(RoomImages image) {
            roomImages.add(image);
            image.setRoom(this.build()); // Set the relationship on the other side
        }

        public void removeImage(RoomImages image) {
            roomImages.remove(image);
            image.setRoom(null); // Remove the relationship on the other side
        }

        public void setServiceCharge(RoomServiceCharge serviceCharge) {
            this.serviceCharge = serviceCharge;
        }

        public Room build() {
            return new Room(this);
        }
    }

    @Override
    public String toString() {
        return  "roomId=" + roomId +
                ", roomNumber=" + roomNumber +
                ", roomType=" + roomType +
                ", capacity=" + capacity +
                ", pricePerNight=" + pricePerNight +
                ", roomStatus=" + roomStatus +
                ", roomImages=" + roomImages +
                ", serviceCharge=" + serviceCharge +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt;
    }
}
