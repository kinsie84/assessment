package com.kinsella.assessment.business.domain;

import com.kinsella.assessment.data.dto.CarResult;

public interface SegmentedCarResultAssembler {

    SegmentedCarResult assemble(CarResult carResult) throws IllegalArgumentException;
}
