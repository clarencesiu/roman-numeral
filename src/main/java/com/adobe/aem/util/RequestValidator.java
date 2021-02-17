package com.adobe.aem.util;

import com.adobe.aem.exception.InvalidRequestException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is the validator for all incoming requests. If any checks fail, an InvalidRequestException is thrown.
 *
 * @author  Clarence Siu
 * @version 1.0
 * @since   2021-02-15
 */
public class RequestValidator {

    private static final Log logger = LogFactory.getLog(RequestValidator.class);

    /**
     * Validate request value.
     *
     * @param request the request being checked
     * @param min the minimum number accepted
     * @param max the maximum number accepted
     * @throws InvalidRequestException if request did not pass check
     * @return void
     */
    public static void validate(String request, int min, int max) {
        // check for null, empty, or only spaces
        if (StringUtils.isBlank(request)) {
            logger.warn(String.format("Query \'%s\' is blank", request));
            throw new InvalidRequestException("Query must contain a value");
        }
        // check for valid integer
        if (!isInteger(request)) {
            logger.warn(String.format("Query \'%s\' is not an integer", request));
            throw new InvalidRequestException("Query must be an integer");
        }
        // check that number is within range
        if (!isInRange(min, max, Integer.parseInt(request))) {
            logger.warn(String.format("Query \'%s\' is not within range of %d-%d", request, min, max));
            throw new InvalidRequestException(String.format("Query must be within range of %d-%d", min, max));
        }
        logger.debug(String.format("Query \'%s\' passed validation", request));
    }

    /**
     * Check if request is an integer.
     *
     * @param request the value being checked
     * @return boolean
     */
    private static boolean isInteger(String request) {
        return StringUtils.isNumeric(request);
    }

    /**
     * Check if request is within range.
     *
     * @param request the value being checked
     * @return boolean
     */
    private static boolean isInRange(int min, int max, int request) {
        return min <= request && request <= max;
    }

}
