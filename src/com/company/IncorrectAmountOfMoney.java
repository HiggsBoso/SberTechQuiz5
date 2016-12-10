package com.company;

/**
 * Created by Someone on 04.12.2016.
 */
public class IncorrectAmountOfMoney extends Exception {
    @Override
    public String toString() {
        return "Ошибка возникла из-за того, что с карты пытались снять или положить либо отрицательную сумму, " +
                "либо сумму, не кратную 100";
    }
}
