package org.otus.processor;

import org.otus.model.DateTimeProvider;
import org.otus.model.Message;

public class EvenSecondExceptionProcessor implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public EvenSecondExceptionProcessor(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        int currentSecond = dateTimeProvider.getDate().getSecond();
        if (currentSecond % 2 == 0) {
            throw new RuntimeException("Exception occurred in even second: " + currentSecond);
        }
        return message;
    }
}
