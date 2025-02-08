package org.otus.interfaces;

import java.util.Map;
import org.otus.domain.Nominal;

public interface GiveMoney {
    Map<Nominal, Integer> giveMoney(int amount);
}
