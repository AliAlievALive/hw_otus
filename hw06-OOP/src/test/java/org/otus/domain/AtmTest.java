package org.otus.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AtmTest {
    private static final Logger logger = LoggerFactory.getLogger(AtmTest.class);
    private Atm atm;

    @BeforeEach
    void setUp() {
        atm = new Atm();
        atm.addBanknoteBox(new BanknoteBox(Nominal.FIVE_THOUSAND, 5));
        atm.addBanknoteBox(new BanknoteBox(Nominal.THOUSAND, 10));
        atm.addBanknoteBox(new BanknoteBox(Nominal.FIVE_HUNDRED, 20));
        logger.info("balance: {}", atm.getBalance());
    }

    @Test
    void giveMoney_validAmount() {
        int initialBalance = atm.getBalance();

        atm.giveMoney(6500);

        int expectedBalance = initialBalance - 6500;
        assertEquals(expectedBalance, atm.getBalance(), "Баланс после выдачи неверный");
    }

    @Test
    void giveMoney_invalidAmount_insufficientFunds() {
        int totalBalance = atm.getBalance();
        Exception exception = assertThrows(IllegalStateException.class, () -> atm.giveMoney(totalBalance + 100));
        assertEquals("Недостаточно средств в банкомате", exception.getMessage());
    }

    @Test
    void giveMoney_invalidAmount_notDivisibleByNominals() {
        Exception exception = assertThrows(IllegalStateException.class, () -> atm.giveMoney(123));
        assertEquals("Невозможно выдать запрашиваемую сумму с текущими номиналами", exception.getMessage());
    }

    @Test
    void putMoney() {
        int initialBalance = atm.getBalance();

        atm.putMoney(List.of(Nominal.FIVE_HUNDRED, Nominal.FIVE_HUNDRED, Nominal.THOUSAND, Nominal.THOUSAND));

        int expectedBalance = initialBalance + 2 * 500 + 2 * 1000;
        assertEquals(expectedBalance, atm.getBalance(), "Баланс после пополнения неверный");
    }

    @Test
    void getBalance() {
        int expectedBalance = 5 * 5000 + 10 * 1000 + 20 * 500;
        assertEquals(expectedBalance, atm.getBalance(), "Начальный баланс неверный");
    }
}