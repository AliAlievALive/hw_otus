package org.otus.interfaces;

import java.util.List;
import org.otus.domain.Nominal;

public interface PutMoney {
    void putMoney(List<Nominal> nominals);
}
