package org.otus.handler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.otus.listener.Listener;
import org.otus.model.ExtraMessageFields;
import org.otus.model.Message;
import org.otus.model.ObjectForMessage;
import org.otus.processor.Processor;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ComplexProcessorTest {

    @Test
    @DisplayName("Тестируем вызовы процессоров")
    void handleProcessorsTest() {
        // given
        var message = new Message.Builder(1L).field7("field7").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenReturn(message);

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, ex -> {
        });

        // when
        var result = complexProcessor.handle(message);

        // then
        verify(processor1).process(message);
        verify(processor2).process(message);
        assertThat(result).isEqualTo(message);
    }

    @Test
    @DisplayName("Тестируем обработку исключения")
    void handleExceptionTest() {
        // given
        var message = new Message.Builder(1L).field8("field8").build();

        var processor1 = mock(Processor.class);
        when(processor1.process(message)).thenThrow(new RuntimeException("Test Exception"));

        var processor2 = mock(Processor.class);
        when(processor2.process(message)).thenReturn(message);

        var processors = List.of(processor1, processor2);

        var complexProcessor = new ComplexProcessor(processors, ex -> {
            throw new TestException(ex.getMessage());
        });

        // when
        assertThatExceptionOfType(TestException.class).isThrownBy(() -> complexProcessor.handle(message));

        // then
        verify(processor1, times(1)).process(message);
        verify(processor2, never()).process(message);
    }

    @Test
    @DisplayName("Тестируем уведомления")
    void notifyTest() {
        // given
        var message = new Message.Builder(1L).field9("field9").build();

        var listener = mock(Listener.class);

        var complexProcessor = new ComplexProcessor(new ArrayList<>(), ex -> {
        });

        complexProcessor.addListener(listener);

        // when
        complexProcessor.handle(message);
        complexProcessor.removeListener(listener);
        complexProcessor.handle(message);

        // then
        verify(listener, times(1)).onUpdated(message);
    }

    @Test
    void processChangeFields() {
        var field13 = new ObjectForMessage();
        field13.setData(List.of("data1", "data2"));

        var extraFields = new ExtraMessageFields("originalField11", "originalField12", field13);
        var message = new Message.Builder(1L)
                .field10("field10")
                .extraMessageFields(extraFields)
                .build();

        Processor processor = message1 -> message1;

        Message updatedMessage = processor.processChangeFields(message);

        ExtraMessageFields processedFields = updatedMessage.getExtraMessageFields();
        assertThat(processedFields.getField11()).isEqualTo("originalField12");
        assertThat(processedFields.getField12()).isEqualTo("originalField11");
        assertThat(processedFields.getField13().getData()).containsExactly("data1", "data2");

        ExtraMessageFields originalFields = message.getExtraMessageFields();
        assertThat(originalFields.getField11()).isEqualTo("originalField11");
        assertThat(originalFields.getField12()).isEqualTo("originalField12");
        assertThat(originalFields.getField13().getData()).containsExactly("data1", "data2");
    }

    private static class TestException extends RuntimeException {
        public TestException(String message) {
            super(message);
        }
    }
}
