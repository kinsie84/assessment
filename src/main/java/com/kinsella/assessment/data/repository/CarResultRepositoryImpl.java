package com.kinsella.assessment.data.repository;

import com.kinsella.assessment.data.persistence.dao.CarResultDao;
import com.kinsella.assessment.data.persistence.entity.CarResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CarResultRepositoryImpl implements CarResultRepository {

    @Autowired
    private CarResultDao carResultDao;

    @Override
    public Iterable<CarResult> findAll(){
        return carResultDao.findAll();

    }

}
