package com.kinsella.assessment.data.repository;

import com.kinsella.assessment.data.persistence.dao.CarResultDao;
import com.kinsella.assessment.data.persistence.entity.CarResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class CarResultRepositoryImpl implements CarResultRepository {

    @Autowired
    private CarResultDao carResultDao;

    @Override
    public Collection<CarResult> getAll(){
        return carResultDao.getAll();

    }

    @Override
    public Collection<CarResult> getAllUnique(){
        return getAll().stream()
                .distinct()
                .collect(Collectors.toList());
    }

}
