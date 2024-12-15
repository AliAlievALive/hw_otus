package org.otus.interfaces;

import org.otus.domain.Nominal;

import java.util.List;

public interface PutMoney {
    void putMoney(List<Nominal> nominals);
}
