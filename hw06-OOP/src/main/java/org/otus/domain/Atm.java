package org.otus.domain;

import org.otus.interfaces.GiveMoney;
import org.otus.interfaces.PutMoney;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Atm implements GiveMoney, PutMoney {
    private static final Logger logger = LoggerFactory.getLogger(Atm.class);

    private final Set<BanknoteBox> banknotes = new HashSet<>();

    public void addBanknoteBox(BanknoteBox banknoteBox) {
        banknotes.add(banknoteBox);
    }

    @Override
    public Map<Nominal, Integer> giveMoney(int amount) {
        logger.info("Запрашиваемая сумма: {}", amount);
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма должна быть больше 0");
        }
        int totalBalance = getBalance();
        if (amount > totalBalance) {
            throw new IllegalStateException("Недостаточно средств в банкомате");
        }

        List<BanknoteBox> sortedBoxes = banknotes.stream()
                .sorted((b1, b2) -> Integer.compare(b2.getAmount().getValue(), b1.getAmount().getValue()))
                .toList();

        Map<Nominal, Integer> withdrawalMap = new LinkedHashMap<>();
        int remainingAmount = amount;

        for (BanknoteBox box : sortedBoxes) {
            int nominalValue = box.getAmount().getValue();
            int availableBanknotes = box.getCount();

            int neededBanknotes = remainingAmount / nominalValue;
            int banknotesToTake = Math.min(neededBanknotes, availableBanknotes);

            if (banknotesToTake > 0) {
                withdrawalMap.put(box.getAmount(), banknotesToTake);
                remainingAmount -= banknotesToTake * nominalValue;
                box.take(banknotesToTake);
            }

            if (remainingAmount == 0) break;
        }

        if (remainingAmount > 0) {
            throw new IllegalStateException("Невозможно выдать запрашиваемую сумму с текущими номиналами");
        }
        withdrawalMap.forEach((nominal, count) ->
                logger.info("Выдано {} банкнот номиналом {}", count, nominal.getValue()));
        return withdrawalMap;
    }

    @Override
    public void putMoney(List<Nominal> nominals) {
        logger.info("Пополняем банкомат");
        Map<Nominal, Long> nominalCountMap = nominals.stream()
                .collect(Collectors.groupingBy(nominal -> nominal, Collectors.counting()));

        nominalCountMap.forEach((nominal, count) ->
                banknotes.stream()
                        .filter(box -> box.getAmount() == nominal)
                        .findFirst()
                        .ifPresent(box -> box.put(count.intValue())));
    }

    public int getBalance() {
        logger.info("Подсчитываем баланс");
        return banknotes.stream()
                .mapToInt(box -> box.getAmount().getValue() * box.getCount())
                .sum();
    }
}