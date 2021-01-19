package com.kinsella.assessment.business.service;


import com.kinsella.assessment.business.domain.SegmentedCarResult;

import java.util.Collection;
import java.util.List;

public interface CarResultService {

    Collection<SegmentedCarResult> getSegmentedCarResults();

    Collection<SegmentedCarResult> sortByCorporateCategoryPrice(Collection<SegmentedCarResult> segmentedCarResults);

    Collection<SegmentedCarResult> removeResultsAboveMedian(Collection<SegmentedCarResult> segmentedCarResults);

    double getMedianPrice(List<SegmentedCarResult> segmentedCarResultList);
}
