package org.otus.listener.homework;

import org.otus.listener.Listener;
import org.otus.model.ExtraMessageFields;
import org.otus.model.Message;
import org.otus.model.ObjectForMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Message> history = new HashMap<>();

    @Override
    public void onUpdated(Message msg) {
        history.put(msg.getId(), deepCopy(msg));
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id));
    }

    private Message deepCopy(Message original) {
        ExtraMessageFields originalExtra = original.getExtraMessageFields();
        ObjectForMessage copiedField13 = new ObjectForMessage();
        copiedField13.setData(new ArrayList<>(originalExtra.getField13().getData()));

        ExtraMessageFields copiedExtra = new ExtraMessageFields(
                originalExtra.getField11(),
                originalExtra.getField12(),
                copiedField13
        );

        return new Message.Builder(original.getId())
                .field1(original.getField1())
                .field2(original.getField2())
                .field3(original.getField3())
                .field4(original.getField4())
                .field5(original.getField5())
                .field6(original.getField6())
                .field7(original.getField7())
                .field8(original.getField8())
                .field9(original.getField9())
                .field10(original.getField10())
                .extraMessageFields(copiedExtra)
                .build();
    }
}
