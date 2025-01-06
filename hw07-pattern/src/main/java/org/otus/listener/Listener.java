package org.otus.listener;

import org.otus.model.Message;

@SuppressWarnings("java:S1135")
public interface Listener {

    void onUpdated(Message msg);
}
