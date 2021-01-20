package com.kinsella.assessment.data.dto;

import java.util.Objects;

public class CarResult {
    private final String description;
    private final String supplierName;
    private final String sippCode;
    private final double rentalCost;
    private final String fuelPolicy;

    public CarResult(String description, String supplierName, String sipp, double cost, String fuelPolicy) {
        this.description = description;
        this.supplierName = supplierName;
        this.sippCode = sipp;
        this.rentalCost = cost;
        this.fuelPolicy = fuelPolicy;
    }

    public String getDescription() {
        return this.description;
    }

    public String getSupplierName() {
        return this.supplierName;
    }

    public String getSippCode() {
        return this.sippCode;
    }

    public double getRentalCost() {
        return this.rentalCost;
    }

    public String getFuelPolicy() {
        return this.fuelPolicy;
    }

    public String toString() {
        return this.supplierName + " : " +
                this.description + " : " +
                this.sippCode + " : " +
                this.rentalCost + " : " +
                this.fuelPolicy;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CarResult carResult = (CarResult) obj;
        return Objects.equals(this.getDescription(), carResult.getDescription())
                && Objects.equals(this.getSupplierName(), carResult.getSupplierName())
                && Objects.equals(this.getSippCode(), carResult.getSippCode())
                && Objects.equals(this.getFuelPolicy(), carResult.getFuelPolicy());
    }

    @Override
    public int hashCode() {
        return Objects.hash(description,supplierName,sippCode,fuelPolicy);
    }
}
