package com.company;

import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Someone on 03.12.2016.
 */
public class Client {
    private String clientName;
    private String login;
    private String password;
    private LinkedList<Card> listOfCards;

    private void setClientName(String clientName) {
        this.clientName = clientName;
    }

    private void setLogin(String login) {
        this.login = login;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public String getClientName() {
        return clientName;
    }

    public String getLogin() {
        return login;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public Card ifClientHasCard(String cardNumber) {
        for (Card card: listOfCards) {
            if (card.getCardNumber().equals(cardNumber)) {
                return card;
            }
        }
        return null;
    }

    public void showAllCards() {
        System.out.println("Номер карты" + "Состояние счета");
        for (Card card : listOfCards) {
            System.out.println(card.getCardNumber() + " " + card.getAccountState());
        }
    }

    Client(String clientName, String login, String password) {
        setClientName(clientName);
        setLogin(login);
        setPassword(password);
        listOfCards = new LinkedList<>();
    }

    public Card addNewCard() {
        String cardNumber = null;
        int pin = 0;
        System.out.println("Введите номер карты:");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            cardNumber = scanner.nextLine();
        }
        System.out.println("Введите PIN-код, который Вы хотите использовать для карты");
        if (scanner.hasNextInt()) {
            pin = scanner.nextInt();
        }
        Card card = new Card(cardNumber, clientName, pin);
        listOfCards.add(card);
        return card;
    }
}
