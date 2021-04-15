package com.pismo.interview.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BigDecimalUtilsTest {

    @Test
    void givenPositiveValue_whenPositiveValidation_thenTrue() {
        assertThat(BigDecimalUtils.isPositive(BigDecimal.TEN)).isTrue();
    }

    @Test
    void givenZero_whenPositiveValidation_thenTrue() {
        assertThat(BigDecimalUtils.isPositive(BigDecimal.ZERO)).isTrue();
    }

    @Test
    void givenNegativeValue_whenPositiveValidation_thenFalse() {
        assertThat(BigDecimalUtils.isPositive(BigDecimal.valueOf(-10.00))).isFalse();
    }

    @Test
    void givenPositiveValue_whenNegativeValidation_thenFalse() {
        assertThat(BigDecimalUtils.isNegative(BigDecimal.TEN)).isFalse();
    }

    @Test
    void givenZero_whenNegativeValidation_thenFalse() {
        assertThat(BigDecimalUtils.isNegative(BigDecimal.ZERO)).isFalse();
    }

    @Test
    void givenNegativeValue_whenNegativeValidation_thenTrue() {
        assertThat(BigDecimalUtils.isNegative(BigDecimal.valueOf(-10.00))).isTrue();
    }
}
