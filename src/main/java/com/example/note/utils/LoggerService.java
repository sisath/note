package com.example.note.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The LoggerService class provides static methods for logging information and errors using a Logger.
 * This class cannot be instantiated and should be used directly by calling its static methods.
 */
public class LoggerService {

    /**
     * Private constructor to prevent instantiation of the LoggerService class.
     * Throws an AssertionError if instantiation is attempted.
     */
    private LoggerService() {
        throw new AssertionError("The LoggingService class cannot be instantiated. " +
                "Please use its static methods for functionality.");
    }

    /** The Logger instance used for logging. */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerService.class);

    /**
     * Logs an information message.
     * @param message The message to be logged.
     */
    public static void logInfo(String message) {
        LOGGER.info(message);
    }

    /**
     * Logs an error message along with a Throwable.
     * @param message   The error message to be logged.
     * @param throwable The Throwable object representing the error.
     */
    public static void logError(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }
}