package com.example.model;

import java.time.LocalDateTime;

public class Services {

    private int serviceId;
    private String serviceDescription;
    private float serviceCost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Services(int serviceId, String serviceDescription, float serviceCost) {
        this.serviceId = serviceId;
        this.serviceDescription = serviceDescription;
        this.serviceCost = serviceCost;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public float getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(float serviceCost) {
        this.serviceCost = serviceCost;
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
        return "serviceId=" + serviceId +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", serviceCost=" + serviceCost +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt;
    }
}
