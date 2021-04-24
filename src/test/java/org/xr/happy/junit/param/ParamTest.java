package org.xr.happy.junit.param;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParamTest {

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 5, 100 })
    void testAbs(int x) {
        assertEquals(x, Math.abs(x));
        System.out.println(x);
    }


    @ParameterizedTest
    @ValueSource(ints = { -1, -5, -100 })
    void testAbsNegative(int x) {
        assertEquals(-x, Math.abs(x));
    }

    @ParameterizedTest
    @CsvSource({ "abc, Abc", "APPLE, Apple", "gooD, Good" })
    void testCapitalize(String input, String result) {
        System.out.println(result);
        assertEquals(result, StringUtils.capitalize(input));
    }


    @ParameterizedTest
    @MethodSource(value ={"testCapitalize"} )
    void testCapitaliz(String input, String result) {
        System.out.println("-----:"+input);
        System.out.println("*****:"+result);
        assertEquals(result, StringUtils.capitalize(input));
    }

    static List<Arguments> testCapitalize() {
        List<Arguments> arguments = new ArrayList<>();
        Collections.addAll(arguments,
                Arguments.arguments("abc", "Abc"),
                Arguments.arguments("APPLE", "Apple"),
                Arguments.arguments("gooD", "Good"));

        return arguments;
    }
}
