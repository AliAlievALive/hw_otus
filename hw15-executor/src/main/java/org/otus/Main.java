package org.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String FIRST = "FIRST";
    private static final String SECOND = "SECOND";
    private String threadName = SECOND;
    private boolean reverse = false;

    private synchronized void action() {
        int number = 1;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (threadName.equals(Thread.currentThread().getName())) {
                    this.wait();
                }
                logger.info("number:{}", number);
                if (reverse) number--;
                else number++;

                sleep();
                notifyAll();
                logger.info("after notify");
                threadName = Thread.currentThread().getName();
                if (number == 11) {
                    number = 9;
                    reverse = true;
                }
                if (number == 0) {
                    number = 1;
                    reverse = false;
                }
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Main numbers = new Main();

        new Thread(numbers::action, FIRST).start();
        new Thread(numbers::action, SECOND).start();
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
