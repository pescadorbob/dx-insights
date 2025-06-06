package com.dx.insights.core.usecases.start;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.dx.insights.core.usecases.start.DailyStatsTestBuilder.aDailyStats;
import static org.assertj.core.api.Assertions.assertThat;

public abstract class StatsRepositoryShould {
    protected ForStoringStats statsRepository;

    @BeforeEach
    public void setupRepository() {
        statsRepository = getSomethingForStoringStats();
    }

    protected abstract ForStoringStats getSomethingForStoringStats();

    @Test
    public void storeDailyStats_givenADailyStats() {
        // Given
        var date = LocalDate.parse("2025-03-29");
        var dailyStats = aDailyStats()
                .withTestExecutions(5)
                .withSuccessfulTests(3)
                .withFailedTests(2)
                .withTotalDuration(1000)
                .build();

        // When
        statsRepository.save( dailyStats);
        var actualStats = statsRepository.getDailyStats(date);

        // Then
        assertThat(actualStats)
                .usingRecursiveComparison()
                .isEqualTo(dailyStats);
    }


    @Test
    public void updateExistingStats_whenSavingForSameDate() {
        // Given
        var date = LocalDate.parse("2025-03-29");
        var initialStats = aDailyStats()
                .withTestExecutions(5)
                .withSuccessfulTests(3)
                .build();
        statsRepository.save( initialStats);

        var updatedStats = aDailyStats()
                .withTestExecutions(7)
                .withSuccessfulTests(4)
                .build();

        // When
        statsRepository.save( updatedStats);
        var actualStats = statsRepository.getDailyStats(date);

        // Then
        assertThat(actualStats)
                .usingRecursiveComparison()
                .isEqualTo(updatedStats);
    }
}