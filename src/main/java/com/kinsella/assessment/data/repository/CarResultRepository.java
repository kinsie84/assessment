package com.kinsella.assessment.data.repository;

import com.kinsella.assessment.data.dto.CarResult;

public interface CarResultRepository {

    Iterable<CarResult> findAll();

}
