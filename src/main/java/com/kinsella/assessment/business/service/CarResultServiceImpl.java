package com.kinsella.assessment.business.service;

import com.kinsella.assessment.business.domain.FuelPolicy;
import com.kinsella.assessment.business.domain.SegmentedCarResult;
import com.kinsella.assessment.business.domain.SegmentedCarResultAssembler;
import com.kinsella.assessment.data.persistence.entity.CarResult;
import com.kinsella.assessment.data.repository.CarResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CarResultServiceImpl implements CarResultService{

    private static final Logger LOGGER = LoggerFactory.getLogger(CarResultServiceImpl.class);

    @Autowired
    private CarResultRepository carResultRepository;

    @Autowired
    private SegmentedCarResultAssembler segmentedCarResultAssembler;

    private Collection<CarResult> getAll() {

        Collection<CarResult> carResultsList = new ArrayList<>();
        Iterable<CarResult> carResultsIterable = carResultRepository.findAll();
        carResultsIterable.iterator().forEachRemaining(carResultsList::add);

        return carResultsList;
    }

    @Override
    public Collection<SegmentedCarResult> getSegmentedCarResults() {

        Collection<CarResult> carResults = getAll();
        Collection<SegmentedCarResult> segmentedList = new ArrayList<>();
        carResults.forEach(carResult -> {
            try {
                SegmentedCarResult segmentedCarResult = segmentedCarResultAssembler.assemble(carResult);
                segmentedList.add(segmentedCarResult);
            } catch (IllegalArgumentException exception) {
                LOGGER.error(String.format("CarResult (%s) was not able to be segmented and not included in results due to : %s",
                        carResult.toString(), exception.getMessage()));
            }
        });
        return segmentedList;
    }

    @Override
    public Collection<SegmentedCarResult> sortByCorporateCategoryPrice(Collection<SegmentedCarResult> segmentedCarResults) {

        return segmentedCarResults.stream()
                .sorted(Comparator.comparing(SegmentedCarResult::isCorporate).reversed()
                        .thenComparing(SegmentedCarResult::getCategory)
                        .thenComparingDouble(SegmentedCarResult::getRentalCost)).collect(Collectors.toList());
    }

    @Override
    public Collection<SegmentedCarResult> removeResultsAboveMedian(Collection<SegmentedCarResult> segmentedCarResults) {

        Collection<SegmentedCarResult> corporateList = filterListByGroup(true, segmentedCarResults);
        Collection<SegmentedCarResult> nonCorporateList = filterListByGroup(false, segmentedCarResults);

        double corporateMedian = getMedianPrice((List<SegmentedCarResult>) corporateList);
        double nonCorporateMedian = getMedianPrice((List<SegmentedCarResult>) nonCorporateList);

        corporateList.removeIf(c -> c.getFuelPolicy() == FuelPolicy.FULLFULL && c.getRentalCost() > corporateMedian);
        nonCorporateList.removeIf(c -> c.getFuelPolicy() == FuelPolicy.FULLFULL && c.getRentalCost() > nonCorporateMedian);

        return Stream.of(corporateList, nonCorporateList).flatMap(Collection::stream).collect(Collectors.toList());
    }

    @Override
    public double getMedianPrice(List<SegmentedCarResult> segmentedCarResultList) {

        if (segmentedCarResultList.isEmpty()) {
            return 0d;
        }

        segmentedCarResultList.sort(Comparator.comparing(SegmentedCarResult::getRentalCost));
        double median = segmentedCarResultList.get(segmentedCarResultList.size() / 2).getRentalCost();

        if (segmentedCarResultList.size() % 2 == 0) {
            median = (median + segmentedCarResultList.get(segmentedCarResultList.size() / 2 - 1).getRentalCost()) / 2;
        }

        return Math.floor(median * 100) / 100;
    }

    private Collection<SegmentedCarResult> filterListByGroup(boolean corporate, Collection<SegmentedCarResult> segmentedCarResults) {

        return segmentedCarResults.stream()
                .filter((c) -> c.isCorporate() == corporate)
                .collect(Collectors.toList());
    }
}
