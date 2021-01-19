package com.kinsella.assessment.data.persistence.dao;

import com.kinsella.assessment.data.persistence.entity.CarResult;

import java.util.Collection;

public interface CarResultDao {

    Collection<CarResult> getAll();
}
