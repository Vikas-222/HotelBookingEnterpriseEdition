package com.example.dto;

public class AdditionalChargesDTO {
    private int Id;
    private String chargesName;
    private float cost;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getChargesName() {
        return chargesName;
    }

    public void setChargesName(String chargesName) {
        this.chargesName = chargesName;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return  "Id=" + Id +
                ", chargesName=" + chargesName +
                ", cost=" + cost;
    }
}
