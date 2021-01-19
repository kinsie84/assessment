package com.kinsella.assessment.presentation;

import com.kinsella.assessment.business.domain.SegmentedCarResult;
import com.kinsella.assessment.business.service.CarResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ConsoleInterface implements CommandLineRunner {

    @Autowired
    private CarResultService carResultService;

    @Override
    public void run(String... args) {

        System.out.println();
        System.out.println("*********************************************************");
        System.out.println();
        System.out.println("           Welcome to the Cars Application             ");
        System.out.println();
        System.out.println("*********************************************************");
        System.out.println();

        Collection<SegmentedCarResult> segmentedCarResults = carResultService.getSegmentedCarResults();
        Collection<SegmentedCarResult> filteredSegmentedCarResults = carResultService.removeResultsAboveMedian(segmentedCarResults);
        Collection<SegmentedCarResult> sortedSegmentedCarResults = carResultService.sortByCorporateCategoryPrice(filteredSegmentedCarResults);
        sortedSegmentedCarResults.forEach(System.out::println);

        System.out.println();
        System.out.println("*********************************************************");
        System.out.println();
        System.out.println("       You are now exiting the Cars Application       ");
        System.out.println();
        System.out.println("*********************************************************");

    }
}