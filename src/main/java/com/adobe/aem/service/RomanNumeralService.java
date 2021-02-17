package com.adobe.aem.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * This is the service class that handles the business logic of conversion from number to Roman numeral.
 * Range is currently set to 1-3999.
 *
 * @author  Clarence Siu
 * @version 1.0
 * @since   2021-02-15
 * @see https://www.mathsisfun.com/roman-numerals.html
 */
@Component
public class RomanNumeralService {

    private static final Log logger = LogFactory.getLog(RomanNumeralService.class);
    // only defined values up to 1000 given the upper limit of 3999
    private static final int[] integers = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    private static final String[] romanNumerals = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};

    /**
     * Convert a number into a Roman numeral.
     *
     * @param integer the input number to be converted
     * @param min the minimum number accepted
     * @param max the maximum number accepted
     * @return String the Roman numeral value
     */
    public String convert(String integer, int min, int max) {
        long startTimer = System.currentTimeMillis();
        StringBuilder result = new StringBuilder();
        int number = Integer.parseInt(integer);
        // return if out of range
        if (number < min || number > max) {
            return null;
        }
        int position = integers.length - 1;
        // append the corresponding Roman numeral place while it is still greater than that place, starting from high to low
        while (number > 0) {
            while (number >= integers[position]) {
                result.append(romanNumerals[position]);
                number -= integers[position];
            }
            position--;
        }
        // log for processing metrics
        logger.info(String.format("[TIMER] Convert \'%s\' executed in %d ms", integer, System.currentTimeMillis() - startTimer));
        return result.toString();
    }

}
