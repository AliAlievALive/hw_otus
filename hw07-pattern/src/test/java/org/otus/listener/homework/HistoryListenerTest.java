package org.otus.listener.homework;

import org.junit.jupiter.api.Test;
import org.otus.model.ExtraMessageFields;
import org.otus.model.Message;
import org.otus.model.ObjectForMessage;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SuppressWarnings({"java:S1135", "java:S125"})
class HistoryListenerTest {

    @Test
    void listenerTest() {
        // given
        var historyListener = new HistoryListener();

        var id = 100L;
        var data = "33";
        var field13 = new ObjectForMessage();
        var field13Data = new ArrayList<String>();
        field13Data.add(data);
        field13.setData(field13Data);

        var message = new Message.Builder(id)
                .field10("field10")
                .extraMessageFields(new ExtraMessageFields("field11", "field12", field13))
                .build();

        // when
        historyListener.onUpdated(message);
        message.getExtraMessageFields().getField13().setData(new ArrayList<>()); //меняем исходное сообщение
        field13Data.clear(); //меняем исходный список

        // then
        var messageFromHistory = historyListener.findMessageById(id);
        assertThat(messageFromHistory).isPresent();
        assertThat(messageFromHistory.get().getExtraMessageFields().getField13().getData()).containsExactly(data);
    }
}
