package org.otus;

import org.otus.handler.ComplexProcessor;
import org.otus.listener.ListenerPrinterConsole;
import org.otus.model.ExtraMessageFields;
import org.otus.model.Message;
import org.otus.model.ObjectForMessage;
import org.otus.processor.EvenSecondExceptionProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
        var complexProcessor = new ComplexProcessor(List.of(new EvenSecondExceptionProcessor(LocalDateTime::now)), ex -> {});
        var listenerPrinter = new ListenerPrinterConsole();
        complexProcessor.addListener(listenerPrinter);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .extraMessageFields(new ExtraMessageFields("field11", "field12", new ObjectForMessage()))
                .build();

        logger.info("oldMsg:{}", message);
        message = message.processChangeFields(message);
        logger.info("newMsg:{}", message);
    }
}
