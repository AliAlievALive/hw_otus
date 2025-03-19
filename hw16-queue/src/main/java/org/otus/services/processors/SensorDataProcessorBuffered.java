package org.otus.services.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.otus.api.SensorDataProcessor;
import org.otus.api.model.SensorData;
import org.otus.lib.SensorDataBufferedWriter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

// Этот класс нужно реализовать
@SuppressWarnings({"java:S1068", "java:S125"})
public class SensorDataProcessorBuffered implements SensorDataProcessor {
    private static final Logger log = LoggerFactory.getLogger(SensorDataProcessorBuffered.class);

    private final int bufferSize;
    private final SensorDataBufferedWriter writer;
    private final BlockingQueue<SensorData> bufferedData;

    public SensorDataProcessorBuffered(int bufferSize, SensorDataBufferedWriter writer) {
        this.bufferSize = bufferSize;
        this.writer = writer;
        this.bufferedData =
                new PriorityBlockingQueue<>(bufferSize, Comparator.comparing(SensorData::getMeasurementTime));
    }

    @Override
    public void process(SensorData data) {
        var added = bufferedData.offer(data);
        if (!added) {
            log.error("Буфер переполнен");
        }
        if (bufferedData.size() >= bufferSize) {
            flush();
        }
    }

    public void flush() {
        try {
            List<SensorData> sensorData = new ArrayList<>();
            if (bufferedData.drainTo(sensorData, bufferSize) > 0) {
                writer.writeBufferedData(sensorData);
            }
        } catch (Exception e) {
            log.error("Ошибка в процессе записи буфера", e);
        }
    }

    @Override
    public void onProcessingEnd() {
        flush();
    }
}
