package com.adobe.aem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This is the application class that starts the web server.
 *
 * @author  Clarence Siu
 * @version 1.0
 * @since   2021-02-15
 * @see https://www.mathsisfun.com/roman-numerals.html
 */
@SpringBootApplication
public class RomanNumeralApplication {

    /**
     * This is the main method which runs the application.
     *
     * @param args unused
     * @return void
     */
    public static void main(String[] args) {
        SpringApplication.run(RomanNumeralApplication.class, args);
    }

}
