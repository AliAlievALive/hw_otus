package org.otus.api;

import org.otus.api.model.SensorData;

public interface SensorDataProcessor {
    void process(SensorData data);

    default void onProcessingEnd() {}
}
