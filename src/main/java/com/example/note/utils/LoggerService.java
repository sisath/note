package com.example.note.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerService {

    private LoggerService() {
        throw new AssertionError("The LoggingService class cannot be instantiated. " +
                "Please use its static methods for functionality.");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerService.class);

    public static void logInfo(String message) {
        LOGGER.info(message);
    }

    public static void logError(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }
}

