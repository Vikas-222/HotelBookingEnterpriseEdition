package com.example.model;

import java.time.LocalDateTime;

public class Services {

    private int service_id;
    private String serviceDescription;
    private float serviceCost;
    private LocalDateTime createdAt;
    private LocalDateTime updated_at;

    public Services(int service_id, String serviceDescription, float serviceCost) {
        this.service_id = service_id;
        this.serviceDescription = serviceDescription;
        this.serviceCost = serviceCost;
    }

    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
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

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "service_id=" + service_id +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", serviceCost=" + serviceCost +
                ", createdAt=" + createdAt +
                ", updated_at=" + updated_at;
    }
}
