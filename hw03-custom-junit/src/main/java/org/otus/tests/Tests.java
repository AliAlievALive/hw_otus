package org.otus.tests;

import org.otus.annotations.After;
import org.otus.annotations.Before;
import org.otus.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Tests {

    private static final Logger log = LoggerFactory.getLogger(Tests.class);

    @Before
    private void beforeEachMethods() {
        log.info("beforeEachMethod");
    }

    @After
    private void afterEachMethods() {
        log.info("afterEachMethod");
    }

    @Test
    private void correctMethod() {
        log.info("correctMethod");
    }

    @Test
    private void incorrectMethod() {
        log.info("incorrectMethod");
        throw new RuntimeException("error");
    }
}
