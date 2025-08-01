package com.example.bankcards.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    public static void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
