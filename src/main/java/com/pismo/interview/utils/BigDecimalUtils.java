package com.pismo.interview.utils;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class BigDecimalUtils {

    public static boolean isNegative(BigDecimal value) {
        return BigDecimal.ZERO.compareTo(value) == 1;
    }

    public static boolean isPositive(BigDecimal value) {
        return !isNegative(value);
    }
}
