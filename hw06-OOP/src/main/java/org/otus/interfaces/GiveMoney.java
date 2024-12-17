package org.otus.interfaces;

import org.otus.domain.Nominal;

import java.util.Map;

public interface GiveMoney {
    Map<Nominal, Integer> giveMoney(int amount);
}
