package org.otus.dto;

import org.otus.annotations.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogging implements TestLoggingInterface {
    private static final Logger logger = LoggerFactory.getLogger(TestLogging.class);

    @Override
    @Log
    public void calculation(int param) {
        int sum = 0;
        for (int i = 0; i < param; i++) {
            sum += i;
        }
        logger.info("Sum: {}", sum);
    }
}
