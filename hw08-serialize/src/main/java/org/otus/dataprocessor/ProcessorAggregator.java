package org.otus.dataprocessor;

import org.otus.model.Measurement;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingDouble;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        return data.stream()
                .collect(Collectors.groupingBy(Measurement::name, summingDouble(Measurement::value)));
    }
}
