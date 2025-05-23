package org.otus.services;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import org.otus.api.SensorsDataChannel;
import org.otus.api.model.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SensorsDataQueueChannel implements SensorsDataChannel {
    private static final Logger log = LoggerFactory.getLogger(SensorsDataQueueChannel.class);

    private final BlockingQueue<SensorData> sensorsDataQueue;

    public SensorsDataQueueChannel(int sensorsDataQueueCapacity) {
        sensorsDataQueue = new ArrayBlockingQueue<>(sensorsDataQueueCapacity);
    }

    @Override
    public boolean push(SensorData sensorData) {
        var pushResult = sensorsDataQueue.offer(sensorData);
        if (!pushResult) {
            log.warn("Очередь показаний переполнена");
        }
        return pushResult;
    }

    @Override
    public boolean isEmpty() {
        return sensorsDataQueue.isEmpty();
    }

    @Override
    public SensorData take(long timeout, TimeUnit unit) throws InterruptedException {
        return sensorsDataQueue.poll(timeout, unit);
    }
}
