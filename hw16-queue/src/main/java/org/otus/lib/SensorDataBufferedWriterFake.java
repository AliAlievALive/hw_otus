package org.otus.lib;

import java.util.List;
import java.util.stream.Collectors;
import org.otus.api.model.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SensorDataBufferedWriterFake implements SensorDataBufferedWriter {
    private static final Logger log = LoggerFactory.getLogger(SensorDataBufferedWriterFake.class);

    @Override
    public void writeBufferedData(List<SensorData> bufferedData) {
        var dataToWrite = bufferedData.stream().map(SensorData::toString).collect(Collectors.joining("\n"));
        log.info("Как будто куда-то записываем пачку данных: \n{}", dataToWrite);
    }
}
