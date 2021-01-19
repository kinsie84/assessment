package com.kinsella.assessment.data.repository;

import com.kinsella.assessment.data.persistence.entity.CarResult;

import java.util.Collection;

public interface CarResultRepository {

    Collection<CarResult> getAll();

    Collection<CarResult> getAllUnique();
}
