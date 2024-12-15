package org.otus.domain;

import org.otus.interfaces.Putable;
import org.otus.interfaces.Takeable;

public class BanknoteBox implements Takeable, Putable {
    private final Nominal nominal;
    private int count;

    public BanknoteBox(Nominal nominal, int count) {
        this.count = count;
        this.nominal = nominal;
    }

    public int getCount() {
        return count;
    }

    public Nominal getAmount() {
        return nominal;
    }

    @Override
    public void put(int count) {
        this.count += count;
    }

    @Override
    public void take(int count) throws RuntimeException {
        this.count -= count;
        if (this.count < 0) throw new IllegalArgumentException("Недостаточно банкнот в ячейке");
    }
}
