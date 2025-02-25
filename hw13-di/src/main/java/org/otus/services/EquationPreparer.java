package org.otus.services;

import java.util.List;
import org.otus.model.Equation;

public interface EquationPreparer {
    List<Equation> prepareEquationsFor(int base);
}
