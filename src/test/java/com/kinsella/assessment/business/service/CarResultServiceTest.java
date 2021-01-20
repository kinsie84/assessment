package com.kinsella.assessment.business.service;

import com.kinsella.assessment.business.domain.*;
import com.kinsella.assessment.data.dto.CarResult;
import com.kinsella.assessment.data.repository.CarResultRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CarResultServiceTest {

    @TestConfiguration
    static class CarResultServiceTestContextConfiguration {
        @MockBean
        private CarResultRepository carResultRepository;

        @Bean
        public SegmentedCarResultAssembler segmentedCarResultAssembler() {
            return new SegmentedCarResultAssemblerImpl();
        }
        @Bean
        public CarResultServiceImpl carResultService() {
            return new CarResultServiceImpl(carResultRepository, segmentedCarResultAssembler());
        }

        @Bean
        public CarResultRepository carResultRepository() {
            return carResultRepository;
        }
    }

    @Autowired
    private SegmentedCarResultAssembler segmentedCarResultAssembler;
    @Autowired
    private CarResultService carResultService;
    @Autowired
    private CarResultRepository carResultRepository;

    @Before
    public void setUp() {
        Mockito.when(carResultRepository.findAll())
                .thenReturn(getTestList());
    }

    @Test
    public void testListIsSortedCorrectly() {
        List<SegmentedCarResult> expected = new ArrayList<>();
        expected.add(new SegmentedCarResult("Peugeot 107", "SIXT", "MCMR", 22.50d, FuelPolicy.FULLEMPTY, true, Category.MINI));
        expected.add(new SegmentedCarResult("Peugeot 107", "AVIS", "MCMR", 30.05d, FuelPolicy.FULLFULL, true, Category.MINI));
        expected.add(new SegmentedCarResult("Opel Corsa", "SIXT", "EDMN", 29.50d, FuelPolicy.FULLFULL, true, Category.ECONOMY));
        expected.add(new SegmentedCarResult("Volkswagen Golf", "FIREFLY", "CDMR", 48.00d, FuelPolicy.FULLFULL, true, Category.COMPACT));
        expected.add(new SegmentedCarResult("Mercedes A Class", "AVIS", "ICAV", 80.00d, FuelPolicy.FULLFULL, true, Category.OTHER));
        expected.add(new SegmentedCarResult("Volkswagen Up", "DEPLASO", "MDMR", 8.00d, FuelPolicy.FULLEMPTY,true, Category.MINI));
        expected.add(new SegmentedCarResult("Volkswagen Polo", "DELPASO", "EDMR", 10.00d, FuelPolicy.FULLFULL, true, Category.ECONOMY));
        expected.add(new SegmentedCarResult("Volkswagen Golf", "GOLDCAR", "CLMR", 18.00d, FuelPolicy.FULLEMPTY, true, Category.COMPACT));
        expected.add(new SegmentedCarResult("Citroen Berlingo", "GOLDCAR", "CMMV", 28.00d, FuelPolicy.FULLFULL, true, Category.COMPACT));
        expected.add(new SegmentedCarResult("Ford Galaxy", "RECORD", "FVAR", 65.00d, FuelPolicy.FULLEMPTY, true, Category.OTHER));

        Collection<SegmentedCarResult> carResults = carResultService.getSegmentedCarResults();

        Collection<SegmentedCarResult> actual = carResultService.sortByCorporateCategoryPrice(carResults);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testFilterListByGroupReturnsCorporateOnly() {

        Collection<SegmentedCarResult> carResults = carResultService.getSegmentedCarResults();
        Collection<SegmentedCarResult> corporateResults = carResultService.filterListByGroup(carResults, true);

        corporateResults.forEach(carResult -> assertThat(carResult.isCorporate()));
    }

    @Test
    public void testGetMedianPriceReturnsCorrectResultForEvenList() {

        List<SegmentedCarResult> segmentedCarResultList = new ArrayList<>();

        CarResult carResult1 = new CarResult("Test1", "Test", "AAA", 12.81d, "FULLFULL");
        CarResult carResult2 = new CarResult("Test2", "Test", "AAA", 9.78d, "FULLFULL");
        CarResult carResult3 = new CarResult("Test3", "Test", "AAA", 18.07d, "FULLFULL");
        CarResult carResult4 = new CarResult("Test4", "Test", "AAA", 41.16d, "FULLFULL");
        segmentedCarResultList.add(segmentedCarResultAssembler.assemble(carResult1));
        segmentedCarResultList.add(segmentedCarResultAssembler.assemble(carResult2));
        segmentedCarResultList.add(segmentedCarResultAssembler.assemble(carResult3));
        segmentedCarResultList.add(segmentedCarResultAssembler.assemble(carResult4));

        double expectedMedian = 15.44d;
        double actualMedian = carResultService.getMedianPrice(segmentedCarResultList);
        assertThat(actualMedian).isEqualTo(expectedMedian);
    }

    @Test
    public void testGetMedianPriceReturnsCorrectResultForOddList() {

        List<SegmentedCarResult> oddSegmentedCarResultList = new ArrayList<>();

        CarResult carResult1 = new CarResult("Test1", "Test", "AAA", 23.81d, "FULLFULL");
        CarResult carResult2 = new CarResult("Test2", "Test", "AAA", 91.38d, "FULLFULL");
        CarResult carResult3 = new CarResult("Test3", "Test", "AAA", 111.17d, "FULLFULL");
        CarResult carResult4 = new CarResult("Test4", "Test", "AAA", 21.26d, "FULLFULL");
        CarResult carResult5 = new CarResult("Test4", "Test", "AAA", 61.11d, "FULLFULL");
        oddSegmentedCarResultList.add(segmentedCarResultAssembler.assemble(carResult1));
        oddSegmentedCarResultList.add(segmentedCarResultAssembler.assemble(carResult2));
        oddSegmentedCarResultList.add(segmentedCarResultAssembler.assemble(carResult3));
        oddSegmentedCarResultList.add(segmentedCarResultAssembler.assemble(carResult4));
        oddSegmentedCarResultList.add(segmentedCarResultAssembler.assemble(carResult5));

        double expectedMedian = 61.11d;
        double actualMedian = carResultService.getMedianPrice(oddSegmentedCarResultList);
        assertThat(actualMedian).isEqualTo(expectedMedian);
    }

    @Test
    public void testGetMedianPriceReturnsCostOfSingleItemInList() {

        List<SegmentedCarResult> singleItemSegmentedCarResultList = new ArrayList<>();

        CarResult carResult = new CarResult("Test4", "Test", "AAA", 61.11d, "FULLFULL");
        singleItemSegmentedCarResultList.add(segmentedCarResultAssembler.assemble(carResult));

        double expectedMedian = 61.11d;
        double actualMedian = carResultService.getMedianPrice(singleItemSegmentedCarResultList);
        assertThat(actualMedian).isEqualTo(expectedMedian);
    }

    @Test
    public void testGetMedianPriceReturnsZeroWhenListIsEmpty() {

        List<SegmentedCarResult> emptySegmentedCarResultList = new ArrayList<>();

        double expectedMedian = 0d;
        double actualMedian = carResultService.getMedianPrice(emptySegmentedCarResultList);
        assertThat(actualMedian).isEqualTo(expectedMedian);
    }

    @Test
    public void testFilteredMedianReturnsCorrectValuesForCorporateAndNonCorporate() {

        Collection<SegmentedCarResult> carResults = carResultService.getSegmentedCarResults();

        Collection<SegmentedCarResult> corporateResults = carResultService.filterListByGroup(carResults, true);
        Collection<SegmentedCarResult> nonCorporateResults = carResultService.filterListByGroup(carResults, false);

        double expectedCorporateMedian = 30.05d;
        double corporateMedian = carResultService.getMedianPrice((List<SegmentedCarResult>)corporateResults);
        double expectedNonCorporateMedian = 18d;
        double nonCorporateMedian = carResultService.getMedianPrice((List<SegmentedCarResult>)nonCorporateResults);

        assertThat(corporateMedian).isEqualTo(expectedCorporateMedian);
        assertThat(nonCorporateMedian).isEqualTo(expectedNonCorporateMedian);
    }

    @Test
    public void testListExcludesValuesAboveGroupMedianWithFuelPolicyFULLFULL() {

        List<SegmentedCarResult> expected = new ArrayList<>();
        expected.add(new SegmentedCarResult("Peugeot 107", "SIXT", "MCMR", 22.50d, FuelPolicy.FULLEMPTY, true, Category.MINI));
        expected.add(new SegmentedCarResult("Opel Corsa", "SIXT", "EDMN", 29.50d, FuelPolicy.FULLFULL, true, Category.ECONOMY));
        expected.add(new SegmentedCarResult("Peugeot 107", "AVIS", "MCMR", 30.05d, FuelPolicy.FULLFULL, true, Category.MINI));
        expected.add(new SegmentedCarResult("Volkswagen Up", "DEPLASO", "MDMR", 8.00d, FuelPolicy.FULLEMPTY,true, Category.MINI));
        expected.add(new SegmentedCarResult("Volkswagen Polo", "DELPASO", "EDMR", 10.00d, FuelPolicy.FULLFULL, true, Category.ECONOMY));
        expected.add(new SegmentedCarResult("Volkswagen Golf", "GOLDCAR", "CLMR", 18.00d, FuelPolicy.FULLEMPTY, true, Category.COMPACT));
        expected.add(new SegmentedCarResult("Ford Galaxy", "RECORD", "FVAR", 65.00d, FuelPolicy.FULLEMPTY, true, Category.OTHER));

        Collection<SegmentedCarResult> carResults = carResultService.getSegmentedCarResults();
        Collection<SegmentedCarResult> actualResults = carResultService.removeResultsAboveMedian(carResults,FuelPolicy.FULLFULL);

        assertThat(actualResults).isEqualTo(expected);
    }


    private Iterable<CarResult> getTestList() {
        List<CarResult> carResults = new ArrayList<>();
        carResults.add(new CarResult("Ford Galaxy", "RECORD", "FVAR", 65.00d, "FULLEMPTY"));
        carResults.add(new CarResult("Peugeot 107", "AVIS", "MCMR", 30.05d, "FULLFULL"));
        carResults.add(new CarResult("Citroen Berlingo", "GOLDCAR", "CMMV", 28.00d, "FULLFULL"));
        carResults.add(new CarResult("Volkswagen Golf", "GOLDCAR", "CLMR", 18.00d, "FULLEMPTY"));
        carResults.add(new CarResult("Opel Corsa", "SIXT", "EDMN", 29.50d, "FULLFULL"));
        carResults.add(new CarResult("Mercedes A Class", "AVIS", "ICAV", 80.00d, "FULLFULL"));
        carResults.add(new CarResult("Volkswagen Polo", "DELPASO", "EDMR", 10.00d, "FULLFULL"));
        carResults.add(new CarResult("Peugeot 107", "SIXT", "MCMR", 22.50d, "FULLEMPTY"));
        carResults.add(new CarResult("Volkswagen Up", "DEPLASO", "MDMR", 8.00d, "FULLEMPTY"));
        carResults.add(new CarResult("Volkswagen Golf", "FIREFLY", "CDMR", 48.00d, "FULLFULL"));
        return carResults;
    }
}