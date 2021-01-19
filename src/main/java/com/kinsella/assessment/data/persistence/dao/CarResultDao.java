package com.kinsella.assessment.data.persistence.dao;

import com.kinsella.assessment.data.persistence.entity.CarResult;

public interface CarResultDao {

    Iterable<CarResult> findAll();

}
