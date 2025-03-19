package org.otus.api;

import org.otus.api.model.SensorData;

public interface SensorsDataServer {
    void onReceive(SensorData sensorData);
}
