package com.kinsella.assessment.business.domain;

import com.kinsella.assessment.data.persistence.entity.CarResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class SegmentedCarResultAssemblerTest {

    @TestConfiguration
    static class SegmentedCarResultAssemblerTestContextConfiguration {

        @Bean
        public SegmentedCarResultAssembler segmentedCarResultAssembler() {
            return new SegmentedCarResultAssemblerImpl();
        }
    }

    @Autowired
    private SegmentedCarResultAssembler segmentedCarResultAssembler;

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentExceptionIsThrownForInvalidFuelPolicy() {

        CarResult carResult = new CarResult("Test1","Test", "AAA", 12.81d,"Invalid" );
        segmentedCarResultAssembler.assemble(carResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentExceptionIsThrownForEmptySIPP() {

        CarResult carResult = new CarResult("Test1","Test", "", 12.81d,"FULLFULL" );
        segmentedCarResultAssembler.assemble(carResult);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalArgumentExceptionIsThrownForEmptySupplierName() {

        CarResult carResult = new CarResult("Test1","", "AAA", 12.81d,"FULLFULL" );
        segmentedCarResultAssembler.assemble(carResult);
    }
}
