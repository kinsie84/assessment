package com.kinsella.assessment.business.domain;

import com.kinsella.assessment.data.persistence.entity.CarResult;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SegmentedCarResultAssemblerImpl implements SegmentedCarResultAssembler {

    private static final Map<String, Boolean> CORPORATE_MAP;
    private static final Map<Character, Category> CATEGORY_MAP;

    static {
        CORPORATE_MAP = Map.of("AVIS", true, "BUDGET", true, "ENTERPRISE", true, "FIREFLY", true, "HERTZ", true, "SIXT", true, "THRIFTY", true);
        CATEGORY_MAP = Map.of('M', Category.MINI, 'E', Category.ECONOMY, 'C', Category.COMPACT);
    }

    @Override
    public SegmentedCarResult assemble(CarResult carResult) {

        String description = carResult.getDescription();
        String supplier = carResult.getSupplierName();
        String sippCode = carResult.getSippCode();
        double rentalCost = carResult.getRentalCost();
        FuelPolicy fuelPolicy = getFuelPolicy(carResult.getFuelPolicy());
        boolean corporate = getCorporateFromSupplier(carResult.getSupplierName());
        Category category = getCategoryFromSippCode(carResult.getSippCode());

        return new SegmentedCarResult(description, supplier, sippCode, rentalCost, fuelPolicy, corporate, category);
    }

    private FuelPolicy getFuelPolicy(String fuelPolicy) {
        return FuelPolicy.valueOf(fuelPolicy);
    }

    private boolean getCorporateFromSupplier(String supplierName) {
        if (supplierName.isBlank()) {
            throw new IllegalArgumentException("supplierName is blank, supplierName cannot be blank");
        }
        return CORPORATE_MAP.getOrDefault(supplierName, false);
    }

    private Category getCategoryFromSippCode(String sippCode) {
        if (sippCode.isBlank()) {
            throw new IllegalArgumentException("sippCode is blank, sippCode cannot be blank");
        }
        Character leadingCharacter = sippCode.charAt(0);
        return CATEGORY_MAP.getOrDefault(leadingCharacter, Category.OTHER);
    }
}
