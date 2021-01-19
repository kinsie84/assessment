package com.kinsella.assessment.business.domain;

import com.kinsella.assessment.data.persistence.entity.CarResult;

public interface SegmentedCarResultAssembler {

    SegmentedCarResult assemble(CarResult carResult);
}
