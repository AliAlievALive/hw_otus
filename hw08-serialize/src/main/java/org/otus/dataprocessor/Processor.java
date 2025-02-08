package org.otus.dataprocessor;

import java.util.List;
import java.util.Map;
import org.otus.model.Measurement;

public interface Processor {

    Map<String, Double> process(List<Measurement> data);
}
