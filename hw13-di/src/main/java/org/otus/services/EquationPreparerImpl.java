package org.otus.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.otus.model.DivisionEquation;
import org.otus.model.Equation;
import org.otus.model.MultiplicationEquation;

public class EquationPreparerImpl implements EquationPreparer {
    @Override
    public List<Equation> prepareEquationsFor(int base) {
        List<Equation> equations = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            var multiplicationEquation = new MultiplicationEquation(base, i);
            var divisionEquation = new DivisionEquation(multiplicationEquation.getResult(), base);
            equations.add(multiplicationEquation);
            equations.add(divisionEquation);
        }
        Collections.shuffle(equations);
        return equations;
    }
}
