package org.otus.lib;

import java.util.List;
import org.otus.api.model.SensorData;

public interface SensorDataBufferedWriter {
    void writeBufferedData(List<SensorData> bufferedData);
}
