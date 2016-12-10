package com.company;

import CustomExceptions.IncorrectAmountOfMoney;
import CustomExceptions.NotEnoughMoneyException;

import java.util.Scanner;

/**
 * Created by Someone on 03.12.2016.
 */
public class Card {
    private String cardNumber;
    private String clientName;
    // float выбран для реалистичности, например, если был осуществлен платеж через карту, и появился остаток
    private float amountOfMoney;
    // PIN-код хранится в классе Card, так как доступ осуществляется именно к карте
    private int pin;


    public String getCardNumber() {
        return cardNumber;
    }

    public String getClientName() {
        return clientName;
    }

    public float getAccountState() {
        return amountOfMoney;
    }

    private void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    private void setClientName(String clientName) {
        this.clientName = clientName;
    }

    Card(String cardNumber, String clientName, int pin) {
        setCardNumber(cardNumber);
        setClientName(clientName);
        amountOfMoney = 0;
        this.pin = pin;
    }

    public static Card createNewCard() {
        String cardNumber = null;
        String clientName = null;
        int pin = 0;
        System.out.println("Введите номер карты:");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            cardNumber = scanner.nextLine();
        }
        System.out.println("Введите имя клиента: ");
        if (scanner.hasNextLine()) {
            clientName = scanner.nextLine();
        }
        System.out.println("Введите PIN-код, который Вы хотите использовать для карты");
        if (scanner.hasNextInt()) {
            pin = scanner.nextInt();
        }

        return new Card(cardNumber, clientName, pin);
    }

    public void addMoney(int money) {
        try {
            if (money < 0 || money % 100 != 0) {
                throw new IncorrectAmountOfMoney();
            }
            amountOfMoney += money;
        }
        catch (IncorrectAmountOfMoney incorrectAmountOfMoney) {
            System.out.println(incorrectAmountOfMoney);
        }
    }

    public void drawMoney(int money) {
        try {
            if (money > amountOfMoney) {
                throw new NotEnoughMoneyException();
            }
            if (money < 0 || money % 100 != 0) {
                throw new IncorrectAmountOfMoney();
            }
        }
        catch (NotEnoughMoneyException notEnoughMoneyException) {
            System.out.println(notEnoughMoneyException);
        }
        catch(IncorrectAmountOfMoney incorrectAmountOfMoney) {
            System.out.println(incorrectAmountOfMoney);
        }
    }


    public boolean authenticateCardOwner(int pin) {
        if (this.pin == pin) {
            return true;
        }
        else
            return false;
    }

}
