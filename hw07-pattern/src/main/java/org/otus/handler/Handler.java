package org.otus.handler;

import org.otus.listener.Listener;
import org.otus.model.Message;

public interface Handler {
    Message handle(Message msg);

    void addListener(Listener listener);

    void removeListener(Listener listener);
}
