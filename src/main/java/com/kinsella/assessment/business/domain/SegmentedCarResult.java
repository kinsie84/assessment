package com.kinsella.assessment.business.domain;

import java.util.Objects;
import java.util.StringJoiner;

public class SegmentedCarResult {

    private final String description;
    private final String supplierName;
    private final String sippCode;
    private final double rentalCost;
    private final FuelPolicy fuelPolicy;
    private final boolean corporate;
    private final Category category;

    public SegmentedCarResult(String description, String supplierName, String sippCode, double rentalCost, FuelPolicy fuelPolicy, boolean corporate, Category category) {
        this.description = description;
        this.supplierName = supplierName;
        this.sippCode = sippCode;
        this.rentalCost = rentalCost;
        this.fuelPolicy = fuelPolicy;
        this.corporate = corporate;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getSippCode() {
        return sippCode;
    }

    public double getRentalCost() {
        return rentalCost;
    }

    public FuelPolicy getFuelPolicy() {
        return fuelPolicy;
    }

    public boolean isCorporate() {
        return corporate;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" : ");
        joiner.add(supplierName).add(description).add(sippCode).add(rentalCost +"").add(fuelPolicy.toString());
        return joiner.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SegmentedCarResult carResult = (SegmentedCarResult) obj;
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
