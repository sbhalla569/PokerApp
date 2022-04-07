package com.shivbhalla.comp3003_pokerapp_psysb7;

import org.junit.Test;

import static org.junit.Assert.*;

public class StatisticsTests {

    @Test
    public void StatisticsCreation(){
        Statistics statistics = new Statistics();
        assertEquals(0,statistics.getTimesFolded());
        assertEquals(0,statistics.getTimesLost());
        assertEquals(0,statistics.getTimesShouldHaveWon());
        assertEquals(0,statistics.getTimesWon());
        for(int i = 0; i<52; i++){
            assertEquals(0,(int)statistics.getCardRaiseValue().get(i));
        }
        assertEquals("",statistics.getEmail());
    }

    @Test
    public void StatisticGetSetTimesFolded(){
        Statistics statistics = new Statistics();
        statistics.setTimesFolded(5);
        assertEquals(5,statistics.getTimesFolded());
    }

    @Test
    public void StatisticGetSetTimesLost(){
        Statistics statistics = new Statistics();
        statistics.setTimesLost(7);
        assertEquals(7,statistics.getTimesLost());
    }

    @Test
    public void StatisticGetSetTimesShouldHaveWon(){
        Statistics statistics = new Statistics();
        statistics.setTimesShouldHaveWon(2);
        assertEquals(2,statistics.getTimesShouldHaveWon());
    }

    @Test
    public void StatisticGetSetTimesWon(){
        Statistics statistics = new Statistics();
        statistics.setTimesWon(13);
        assertEquals(13,statistics.getTimesWon());
    }

    @Test
    public void StatisticGetSetEmail(){
        Statistics statistics = new Statistics();
        statistics.setEmail("test@gmail.com");
        assertEquals("test@gmail.com",statistics.getEmail());
    }
}
