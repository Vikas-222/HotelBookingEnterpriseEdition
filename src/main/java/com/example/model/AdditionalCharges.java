package com.example.model;

import java.time.LocalDateTime;

public class AdditionalCharges {
    private int Id;
    private float chargesName;
    private float cost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public float getChargesName() {
        return chargesName;
    }

    public void setChargesName(float chargesName) {
        this.chargesName = chargesName;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "AdditionalCharges{" +
                "Id=" + Id +
                ", chargesName=" + chargesName +
                ", cost=" + cost +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
