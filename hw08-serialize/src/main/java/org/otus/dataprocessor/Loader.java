package org.otus.dataprocessor;

import java.io.IOException;
import java.util.List;
import org.otus.model.Measurement;

public interface Loader {

    List<Measurement> load() throws IOException;
}
