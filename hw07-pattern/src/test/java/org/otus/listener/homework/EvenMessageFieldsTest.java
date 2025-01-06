package org.otus.listener.homework;

import org.junit.jupiter.api.Test;
import org.otus.model.DateTimeProvider;
import org.otus.model.Message;
import org.otus.processor.EvenSecondExceptionProcessor;
import org.otus.processor.Processor;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EvenMessageFieldsTest {
    @Test
    void testThrowsExceptionOnEvenSecond() {
        DateTimeProvider mockDateTimeProvider = mock(DateTimeProvider.class);
        when(mockDateTimeProvider.getDate()).thenReturn(LocalDateTime.of(2025, 1, 9, 8, 0, 2)); // четная секунда

        Processor processor = new EvenSecondExceptionProcessor(mockDateTimeProvider);
        Message message = new Message.Builder(1L)
                .field1("Test")
                .build();

        assertThrows(RuntimeException.class, () -> processor.process(message));
    }

    @Test
    void testNoExceptionOnOddSecond() {
        DateTimeProvider mockDateTimeProvider = mock(DateTimeProvider.class);
        when(mockDateTimeProvider.getDate()).thenReturn(LocalDateTime.of(2025, 1, 9, 8, 0, 3)); // нечетная секунда

        Processor processor = new EvenSecondExceptionProcessor(mockDateTimeProvider);
        Message message = new Message.Builder(1L)
                .field1("Test")
                .build();

        assertDoesNotThrow(() -> processor.process(message));
    }
}
