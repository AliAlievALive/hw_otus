package org.otus.processor;

import org.otus.model.Message;

@SuppressWarnings("java:S1135")
public interface Processor {

    Message process(Message message);

    default Message processChangeFields(Message message){
        return message.processChangeFields(message);
    }
}
