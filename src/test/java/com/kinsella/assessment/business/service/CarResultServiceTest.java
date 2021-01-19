package com.kinsella.assessment.business.service;

import com.kinsella.assessment.business.domain.SegmentedCarResult;
import com.kinsella.assessment.business.domain.SegmentedCarResultAssembler;
import com.kinsella.assessment.business.domain.SegmentedCarResultAssemblerImpl;
import com.kinsella.assessment.data.persistence.entity.CarResult;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class CarResultServiceTest {

    @TestConfiguration
    static class CarResultServiceTestContextConfiguration {

        @Bean
        public CarResultService carResultService() {
            return new CarResultServiceImpl();
        }

        @Bean
        public SegmentedCarResultAssembler segmentedCarResultAssembler() {
            return new SegmentedCarResultAssemblerImpl();
        }
    }

    @Autowired
    private SegmentedCarResultAssembler segmentedCarResultAssembler;

    @Autowired
    private CarResultService carResultService;

    @MockBean
    private CarResultRepository carResultRepository;

    @Before
    public void setUp() {
        Mockito.when(carResultRepository.getAll())
                .thenReturn(null);
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

}