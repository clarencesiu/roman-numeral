package com.adobe.aem.controller;

import com.adobe.aem.domain.Numeral;
import com.adobe.aem.service.RomanNumeralService;
import com.adobe.aem.util.RequestValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

/**
 * This is the controller class that handles the web calls for this application.
 * Only supported method is GET. URI is /romannumeral?query={integer}
 * Range is currently set to 1-3999.
 *
 * @author  Clarence Siu
 * @version 1.0
 * @since   2021-02-15
 * @see https://www.mathsisfun.com/roman-numerals.html
 */
@RestController
public class RomanNumeralController {

    /**
     * Configurable minimum value defined in application.properties
     */
    @Value("${romannumeral.number.min:1}")
    private int minRange;

    /**
     * Configurable maximum value defined in application.properties
     */
    @Value("${romannumeral.number.max:3999}")
    private int maxRange;

    @Autowired
    private RomanNumeralService service;

    private static final Log logger = LogFactory.getLog(RomanNumeralController.class);

    /**
     * Default home landing page.
     *
     * @return String welcome message
     */
    @RequestMapping("/")
    public String index() {
        return "Clarence Siu's Adobe & AEM Engineering Test.<br>GET http://localhost:8080/romannumeral?query={integer}";
    }

    /**
     * GET call to convert a number to a Roman numeral.
     *
     * @param integer the input number to be converted
     * @return String the JSON number input and Roman numeral output value
     * @throws ResponseStatusException if it is on invalid request value
     */
    @GetMapping("/romannumeral")
    @ResponseBody
    public Numeral convertToRomanNumeral(@RequestParam("query") String integer) {
        try {
            logger.debug(String.format("Received request to convert \'%s\' to a Roman numeral", integer));
            // normalizing input by removing leading and trailing spaces
            String normalized = integer.trim();
            RequestValidator.validate(normalized, minRange, maxRange);
            String romanNumeral = service.convert(normalized, minRange, maxRange);
            logger.debug(String.format("Finished converting \'%s\' to \'%s\'", integer, romanNumeral));
            return new Numeral(integer, romanNumeral);
        // catch all exceptions, do not leave any unhandled
        } catch (Exception e) {
            // track unsuccessful requests
            logger.error(String.format("Failed to convert \'%s\' to a Roman numeral", integer), e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
