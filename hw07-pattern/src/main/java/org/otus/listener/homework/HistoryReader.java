package org.otus.listener.homework;

import java.util.Optional;
import org.otus.model.Message;

public interface HistoryReader {

    Optional<Message> findMessageById(long id);
}
