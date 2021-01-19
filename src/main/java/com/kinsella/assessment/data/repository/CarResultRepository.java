package com.kinsella.assessment.data.repository;

import com.kinsella.assessment.data.persistence.entity.CarResult;

public interface CarResultRepository {

    Iterable<CarResult> findAll();

}
