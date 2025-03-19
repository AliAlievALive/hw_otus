package org.otus.lib;

import org.otus.api.model.SensorData;

import java.util.List;

public interface SensorDataBufferedWriter {
    void writeBufferedData(List<SensorData> bufferedData);
}
